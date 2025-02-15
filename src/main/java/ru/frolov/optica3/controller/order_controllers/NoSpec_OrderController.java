package ru.frolov.optica3.controller.order_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;


@Controller
public class NoSpec_OrderController
        extends AbstractOrderController {


    public NoSpec_OrderController(OrderService orderService) {
        super(orderService);
    }

    @GetMapping("noSpec")
    public String noSpec(Model model) {

        /*
         * Задача:
         *   Создать и отобразить страницу no spec.
         * */

        System.out.println("========================================");
        System.out.println("\t" + getClass().getSimpleName() + ".noSpec()...");


        // Подготовка данных для отправки в модель
        // ---------------------------------------
        // нужна page noSpec № 0
        orderService.getCache().setSpec(null);
        Page<_Order> actualPage = orderService.getPageNOSpec(0);


        // Отправка данных в модель
        this.orderService.transferToModel(
                model,
                actualPage,
                null,
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        orderService.getCache().cacheAttributesIfNotNull(
                actualPage,
                null,
                null,
                null,
                null,
                null
        );


        return "displayOrders";
    }

    @PostMapping("noSpec")
    public String startPostRequest(Model model) {

        noSpec(model);

        return "displayOrders";
    }
}
