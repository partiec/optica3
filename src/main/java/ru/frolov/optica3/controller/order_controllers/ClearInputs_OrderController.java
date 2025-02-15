package ru.frolov.optica3.controller.order_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;


@Controller
public class ClearInputs_OrderController
        extends AbstractOrderController {
    public ClearInputs_OrderController(OrderService orderService) {
        super(orderService);
    }


    //-------------------------------------------------------------

    @PostMapping("clearInputs")
    public String clearInputs(
            Model model) {
        /*
         * Задача:
         *   В html должны очистится поля поиска. Остальное должно остаться без изменений.
         * */

        // Подготовка данных
        //-------------------
        // Кэшируем пустую dto. Из него пустые значения "очистят" поля html
        orderService.getCache().setDto(new OrderAndClientDto());

        // page берем из кэша и отправляем без изменений
        Page<_Order> samePage = orderService.getCache().getPage();


        // Отправляем данные в модель
        // --------------------------->
        orderService.transferToModel(
                model,
                samePage,
                null,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        orderService.getCache().cacheAttributesIfNotNull(
                samePage,
                null,
                null,
                null,
                null,
                null
        );

        return "displayOrders";
    }
}
