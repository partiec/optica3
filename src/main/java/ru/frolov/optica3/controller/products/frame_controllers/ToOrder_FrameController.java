package ru.frolov.optica3.controller.products.frame_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.service.order.OrderService;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;

import java.util.Comparator;


@Controller
public class ToOrder_FrameController
        extends AbstractFrameController {

    private final OrderService orderService;

    public ToOrder_FrameController(FrameContainerService containerService, FrameUnitService unitService, OrderService orderService) {
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


        // Объявляем page<_Order>
        Page<_Order> actualPage = null;

        // Имеется ли текущий заказ?
        _Order currentOrder = orderService.findCurrent();
        // Если нет, то предупреждение и page без изменений
        if (currentOrder == null) {
            model.addAttribute("noCurrent", "noCurrent");
            // отправляем в модель неизмененную Page<_Order> из кэша
            actualPage = orderService.getCache().getPage();
        } else {
            // если currentOrder есть, то:
            //  - "раннюю" unit отправить в current


            // Найти xContainer по xOrderId
            FrameContainer xContainer = containerService.findById(xContainerId).get();

            // Найти "раннюю" unit в контейнере
            FrameUnit earliestUnit = xContainer.getUnitList().stream()
                    .min(Comparator.comparing(FrameUnit::getCreatedAt))
                    .get();
            // установить ей статус ordered и order current
            earliestUnit.setProductStatus(ProductStatus.ORDERED);
            earliestUnit.setOrder(currentOrder);


            // добавить earliest в currentOrder
            currentOrder.getFrameUnits().add(earliestUnit);


            // удалить earliest из xContainer
            xContainer.getUnitList().remove(earliestUnit);

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
        orderService.getCache().cacheAttributesIfNotNull(
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
