package ru.frolov.optica3.controller.order_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;


@Controller
public class Search_OrderController
        extends AbstractOrderController {


    public Search_OrderController(OrderService orderService) {
        super(orderService);
    }

    @PostMapping("search")
    public String search(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            OrderAndClientDto dto,
            Model model) {

        System.out.println("-----------------------------------------------------");
        System.out.println(getClass().getSimpleName() + ".search()");
        System.out.println("принято dto=" + dto.toString());
        /*
         * Метод ищет контейнеры по введенным данным.
         * Задача:
         *   Отобразить страницу, содержащую контейнеры, "похожие" на введенное
         * */

        // Подготовка данных для модели
        // --------------------------->
        // кэшируем принятое dto
        orderService.getOrderCache().setDto(dto);

        // page однозначно должна быть bySpec
        Specification<_Order> specification = orderService.getSpec().allContains(dto);
        orderService.getOrderCache().setSpec(specification);

        // создаем page №0 по spec (spec уже в кэше)
        Page<_Order> actualPage =
                orderService.getPageBYSpec(0, specification);


        // Отправка данных в модель
        // ------------------------->
        orderService.transferToModel(
                model,
                actualPage,
                null,
                null,
                null,
                whichFieldOnInput,
                null);

        // Контрольное кэширование
        orderService.getOrderCache().cacheAttributesIfNotNull(
                actualPage,
                dto,
                null,
                null,
                null,
                null
        );


        return "displayOrders";
    }
}
