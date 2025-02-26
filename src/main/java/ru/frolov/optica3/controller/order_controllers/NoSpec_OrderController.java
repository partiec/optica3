package ru.frolov.optica3.controller.order_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class NoSpec_OrderController
        extends AbstractOrderController {

    private final AccessoryContainerService accessoryContainerService;
    private final ContactContainerService contactContainerService;
    private final FrameContainerService frameContainerService;
    private final GlassContainerService glassContainerService;

    public NoSpec_OrderController(OrderService orderService, AccessoryContainerService accessoryContainerService, ContactContainerService contactContainerService, FrameContainerService frameContainerService, GlassContainerService glassContainerService) {
        super(orderService);
        this.accessoryContainerService = accessoryContainerService;
        this.contactContainerService = contactContainerService;
        this.frameContainerService = frameContainerService;
        this.glassContainerService = glassContainerService;
    }

    @GetMapping("noSpec")
    public String noSpec(Model model,
                         @RequestParam(name = "check", required = false) Long... check
    ) {

        /*
         * Задача:
         *   Создать и отобразить страницу no spec.
         * */

        System.out.println("\n========================================");
        System.out.println(getClass().getSimpleName() + ".noSpec()...");

        System.out.println("принят check.length: " + check.length);
        System.out.println("сохр checks...");
        Set<Long> set = Arrays.stream(check).collect(Collectors.toSet());
        accessoryContainerService.getAccessoryCache().getChecks().addAll(set);
        System.out.println("проверим: " + accessoryContainerService.getAccessoryCache().getChecks());
        //...
        //...
        //...

        // Подготовка данных для отправки в модель
        // ---------------------------------------
        // нужна page noSpec № 0
        orderService.getOrderCache().setSpec(null);
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
        orderService.getOrderCache().cacheAttributesIfNotNull(
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
