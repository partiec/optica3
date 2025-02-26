package ru.frolov.optica3.controller.order_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;

@Controller
public class DeleteOrderController
        extends AbstractOrderController {


    public DeleteOrderController(OrderService orderService) {
        super(orderService);
    }

    @Transactional
    @PostMapping("delete")
    public String deleteOrder(@RequestParam(name = "xOrderId") Long xOrderId,
                              Model model) {

        /*
         * Задача:
         *   Удалить container по xOrderId и отобразить страницу с учетом удаленного
         *
         * */

        System.out.println("================================================================");
        System.out.println(getClass().getSimpleName() + ".deleteOrder()...");
        System.out.println("получен xOrderId=" + xOrderId);
        System.out.println("Заказов в бд до удаления:" + orderService.getListNOSpec().size());


        // удаляем контейнер по xOrderId
        orderService.deleteById(xOrderId);
        System.out.println("AFTER DELETING");
        System.out.println("Заказов в бд после удаления:" + orderService.getListNOSpec().size());

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться с тем же номером и spec, но обновлена с учетом удаленного
        int pageNumber = orderService.getOrderCache().getPage().getNumber();
        Specification<_Order> specification = orderService.getOrderCache().getSpec();

        // создать page в зависимости от specStatus
        Page<_Order> actualPage;
        if (specification != null) {
            actualPage = orderService.getPageBYSpec(pageNumber, specification);
            System.out.println("Cоздаем actualPage by spec. getTotalElements=" + actualPage.getTotalElements());
        } else {
            actualPage = orderService.getPageNOSpec(pageNumber);
            System.out.println("Cоздаем actualPage no spec. getTotalElements=" + actualPage.getTotalElements());
        }

        // Отправляем данные в модель
        // --------------------------->
        orderService.transferToModel(
                model,
                actualPage,
                null,
                xOrderId,
                null,
                null,
                null);

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
}
