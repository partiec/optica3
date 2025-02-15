package ru.frolov.optica3.controller.order_controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.service.order.OrderService;


@Controller
public class EditMode_OrderController
        extends AbstractOrderController {


    public EditMode_OrderController(OrderService orderService) {
        super(orderService);
    }

    //---------------------------------------------------------------------
    @GetMapping("editMode_on")
    public String editMode_on(Model model) {
        /*
         * Задача:
         *   Включить режим редактирования. Все должно остаться как было.
         * */

        // включить режим редактирования
        orderService.getCache().setMode(true);

        // Подготовка данных для модели
        // ----------------------------->
        // Отправляем данные в модель
        // --------------------------->
        // page остается без изменений
        orderService.transferToModel(
                model,
                orderService.getCache().getPage(),
                null,
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        orderService.getCache().cacheAttributesIfNotNull(
                null,
                null,
                null,
                null,
                null,
                null
        );

        return "displayOrders";
    }

    @GetMapping("editMode_off")
    public String editMode_off(Model model) {
        /*
         * Задача:
         *   Выключить режим редактирования. Все должно остаться как было.
         * */
        orderService.getCache().setMode(false);


        // Отправляем данные в модель
        // --------------------------->
        // page без изменений
        orderService.transferToModel(
                model,
                orderService.getCache().getPage(),
                null,
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        orderService.getCache().cacheAttributesIfNotNull(
                null,
                null,
                null,
                null,
                null,
                false
        );

        return "displayOrders";
    }
}
