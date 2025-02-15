package ru.frolov.optica3.controller.order_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            @RequestParam(name = "xOrderId") Long xOrderId, // order.id
            Model model) {

        /*
         * Задача:
         *
         * */


        // find xOrder
        _Order xOrder = orderService.findById(xOrderId).get();

        System.out.println("======== ========== ============ ===========");
        System.out.println("orderIncludes()...");
        System.out.println("------------------");
        System.out.println("xOrderId=" + xOrderId);
        System.out.println("-no db- xOrder.списокАксессуаров.размер=" + xOrder.getAccessoryUnits().size());
        if (orderService.findCurrent() != null) {
            System.out.println("-from db- orderService.findCurrent().getAccessoryUnits().size()=" + orderService.findCurrent().getAccessoryUnits().size());
        }
        System.out.println("======== ========== ============ ===========");

        //orderService.transferOrder(model, xOrder);
        model.addAttribute("order", xOrder);

        return "display1Order";
    }


}
