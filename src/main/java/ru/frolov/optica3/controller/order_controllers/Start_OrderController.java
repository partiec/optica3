package ru.frolov.optica3.controller.order_controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.service.order.OrderService;


@Controller
public class Start_OrderController
        extends AbstractOrderController {

    private final NoSpec_OrderController noSpec;

    public Start_OrderController(OrderService orderService, NoSpec_OrderController noSpec) {
        super(orderService);
        this.noSpec = noSpec;
    }


//--------------------------------------------------------------------

    @GetMapping("start")
    public String start(Model model) {

        System.out.println("=====================================================");
        System.out.println("\t" + getClass().getSimpleName() + ".start()...");




        return noSpec.noSpec(model);
    }

    @PostMapping("start")
    public String startPost(Model model) {

        start(model);

        return "displayOrders";
    }
}
