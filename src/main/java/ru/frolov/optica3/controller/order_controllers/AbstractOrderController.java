package ru.frolov.optica3.controller.order_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.frolov.optica3.service.order.OrderService;

@RequestMapping("api/order")
@RequiredArgsConstructor
public abstract class AbstractOrderController {

    protected final OrderService orderService;
}
