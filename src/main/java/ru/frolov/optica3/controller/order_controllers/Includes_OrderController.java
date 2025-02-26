package ru.frolov.optica3.controller.order_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;

@Controller
public class Includes_OrderController
        extends AbstractOrderController {


    public Includes_OrderController(OrderService orderService) {
        super(orderService);
    }
    //----------------------------------------------------------------------------------


    @PostMapping("includes")
    public String orderIncludes(
            @RequestParam(name = "xOrderId", required = false) Long xOrderId, // order.id
            Model model) {

        /*
         * Задача:
         *
         * */
        _Order xOrder;
        if (xOrderId == null) {
            xOrder = orderService.getOrderCache().getCurrentOrder();
        } else {
            xOrder = orderService.findById(xOrderId).get();
        }

        //orderService.transferOrder(model, xOrder);
        model.addAttribute("order", xOrder);

        return "display1Order";
    }

    @GetMapping("includes")
    public String orderIncludesGet(Model model){
        return orderIncludes(null, model);
    }


}
