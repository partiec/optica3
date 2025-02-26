package ru.frolov.optica3.controller.order_controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;

@Controller
public class Flip_OrderController
        extends AbstractOrderController {


    public Flip_OrderController(OrderService orderService) {
        super(orderService);
    }

    @GetMapping("flipPage/{pageNumber:\\d+}")
    public String flipFramePage(
            @PathVariable(name = "pageNumber") Integer pageNumber,
            Model model) {
        /*
         *
         * */

        // Подготовка данных для модели
        // ------------------------------>
        // page должна быть с другим pageNumber, но specification та же
        Page<_Order> actualPage;
        Specification<_Order> specification = orderService.getOrderCache().getSpec();
        if (specification != null) {
            actualPage = orderService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = orderService.getPageNOSpec(pageNumber);
        }

        // Отправка данных в модель
        // --------------------------->
        orderService.transferToModel(
                model,
                actualPage,
                pageNumber,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
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
}
