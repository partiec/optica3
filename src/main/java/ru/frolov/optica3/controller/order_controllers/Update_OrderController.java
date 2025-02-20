package ru.frolov.optica3.controller.order_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;

@Controller
public class Update_OrderController
        extends AbstractOrderController {


    public Update_OrderController(OrderService orderService) {
        super(orderService);
    }

    @Transactional
    @PostMapping("update")
    public String update(@RequestParam(name = "xOrderId") Long xOrderId,
                         OrderAndClientDto dto,
                         Model model) {

        /*
         * Метод обновляет значения полей по dto.
         * Задача:
         *   Обновить и отобразить страницу.
         * */

        System.out.println("==================================================");
        System.out.println("==================================================");
        System.out.println(getClass().getSimpleName() + ".update()...");
        System.out.println("принят xOrderId=" + xOrderId);
        System.out.println("принято dto(" +
                dto.toString());

        // находим нужный заказ по xOrderId
        _Order xOrder = orderService.findById(xOrderId).get();


        // если пришло getCurrent, то устанавливаем, затирая старое значение
        //--------------------------------------------------------
        if (dto.getCurrent() != null) {

            xOrder.setCurrent(dto.getCurrent());

            if (dto.getCurrent() == true) {
                orderService.getCache().setCurrentOrder(xOrder);
            } else {
                orderService.getCache().setCurrentOrder(null);
            }
        }

        if (dto.getStage() != null) {
            xOrder.setStage(dto.getStage());
        }
        if (dto.getPayment() != null) {
            xOrder.setPayment(dto.getPayment());
        }
        if (dto.getOrderDetails() != null) {
            xOrder.setOrderDetails(dto.getOrderDetails());
        }
        if (dto.getPrice() != null) {
            xOrder.setPrice(dto.getPrice());
        }
        if (dto.getLastName() != null) {
            xOrder.getClient().setLastName(dto.getLastName());
        }
        if (dto.getFirstName() != null) {
            xOrder.getClient().setFirstName(dto.getFirstName());
        }
        if (dto.getBirthday() != null) {
            xOrder.getClient().setBirthday(dto.getBirthday());
        }
        if (dto.getPatronymic() != null) {
            xOrder.getClient().setPatronymic(dto.getPatronymic());
        }
        if (dto.getPassport() != null) {
            xOrder.getClient().setPassport(dto.getPassport());
        }
        if (dto.getClientDetails() != null) {
            xOrder.getClient().setDetails(dto.getClientDetails());
        }


//        // сохраняем контейнер
//        this.containerService.save(xContainer);

        // Подготовка данных для модели
        // ---------------------------->
        // новая page должна остаться с тем же номером и spec
        int pageNumber = orderService.getCache().getPage().getNumber();
        Specification<_Order> specification = orderService.getCache().getSpec();
        Page<_Order> actualPage;
        if (specification != null) {
            actualPage = orderService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = orderService.getPageNOSpec(pageNumber);
        }

        // Отправляем данные в модель
        // --------------------------->
        orderService.transferToModel(
                model,
                actualPage,
                null,
                xOrderId,
                null,
                null,
                null);

        // Контрольное кэширование
        // ------------------------->
        orderService.getCache().cacheAttributesIfNotNull(
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
