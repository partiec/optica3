package ru.frolov.optica3.controller.products.contact_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.entity.products.contact.ContactUnit;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.service.order.OrderService;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;

import java.util.Comparator;
import java.util.List;


@Controller
public class ToOrder_ContactController
        extends AbstractContactController {

    private final OrderService orderService;

    public ToOrder_ContactController(ContactContainerService containerService, ContactUnitService unitService, OrderService orderService) {
        super(containerService, unitService);
        this.orderService = orderService;
    }


    //-------------------------------------------------------------

    @PostMapping("toOrder")
    public String toOrder(
            @RequestParam(name = "xContainerId") Long xContainerId, // container.id
            Model model) {
        /*
         * Задача:
         *   Отправить "раннюю" unit в заказ. Статус продукта должен измениться на ORDERED.
         *   Переход на displayOrders.
         * */



        ContactContainer xContainer = null;
        ContactUnit earliest = null;
        Page<_Order> actualPage = null;

        // текущий заказ?
        _Order currentOrder = orderService.findCurrent();
        // Если нет текущего заказа, то предупреждение и page без изменений
        if (currentOrder == null) {
            model.addAttribute("noCurrent", "noCurrent");
            // отправляем в модель неизмененную Page<_Order> из кэша
            actualPage = orderService.getOrderCache().getPage();
        } else {
            // если currentOrder есть, то:
            //  - "раннюю" unit отправить в current


            // Найти xContainer по xContainerId
             xContainer = containerService.findById(xContainerId).get();

            // Найти "раннюю" unit в контейнере
           earliest = xContainer.getUnitList().stream()
                    .min(Comparator.comparing(ContactUnit::getCreatedAt))
                    .get();
            // установить ей статус ordered и order current
            earliest.setProductStatus(ProductStatus.ORDERED);
            earliest.setOrder(currentOrder);


            // добавить earliest в currentOrder
            List<ContactUnit> units = currentOrder.getContactUnits();
            units.add(earliest);


            // удалить earliest из xContainer
            xContainer.getUnitList().remove(earliest);

            // actualPage должна содержать current
            actualPage = orderService.getXPage(currentOrder);
        }


        // Отправляем данные в модель. Используем orderService !!!
        // --------------------------->
        orderService.transferToModel(
                model,
                actualPage,
                null,
                currentOrder == null ? null : currentOrder.getId(),
                null,
                null,
                null);

        // Контрольное кэширование. Используем orderService !!!
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
