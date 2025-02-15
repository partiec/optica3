package ru.frolov.optica3.controller.order_controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;

@Controller
public class CopyToSearch_OrderController
        extends AbstractOrderController {


    public CopyToSearch_OrderController(OrderService orderService) {
        super(orderService);
    }
    //----------------------------------------------------------------------------------

    @Transactional
    @PostMapping("copyToSearch")
    public String copyToSearch(@RequestParam(name = "xOrderId") Long xOrderId,
                               Model model) {

        /*
         * Задача:
         *   Скопировать контейнер в блок поиска чтобы не вбивать вручную.
         * */


        // find xOrder
        _Order xOrder = orderService.findById(xOrderId).get();

        // создаем dto на основе xOrder
        OrderAndClientDto dto = createDtoByOrder(xOrder);

        orderService.getCache().setDto(dto);

        // page без изменений
        Page<_Order> samePage =
                orderService.getCache().getPage();


        // Отправляем данные в модель
        // --------------------------->
        orderService.transferToModel(
                model,
                samePage,
                null,
                null,
                null,
                null,
                "copyToSearch");

        // Контрольное кэширование
        // ----------------------->
        orderService.getCache().cacheAttributesIfNotNull(
                samePage,
                null,
                null,
                xOrder,
                null,
                null);

        return "displayOrders";
    }

    private OrderAndClientDto createDtoByOrder(_Order xOrder) {
        OrderAndClientDto dto = new OrderAndClientDto();

        dto.setId(xOrder.getId());
        dto.setStage(xOrder.getStage());
        dto.setPayment(xOrder.getPayment());
        dto.setOrderDetails(xOrder.getOrderDetails());
        dto.setPrice(xOrder.getPrice());

        //////////////////////////////////////
        dto.setLastName(xOrder.getClient().getLastName());
        dto.setFirstName(xOrder.getClient().getFirstName());
        dto.setPatronymic(xOrder.getClient().getPatronymic());
        dto.setPassport(xOrder.getClient().getPassport());
        dto.setClientDetails(xOrder.getClient().getDetails());
        //////////////////////////////////////

        return dto;
    }
}
