package ru.frolov.optica3.controller.products.accessory_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.service.order.OrderService;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


@Controller
public class ToOrder_AccessoryController
        extends AbstractAccessoryController {

    private final OrderService orderService;

    public ToOrder_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService, OrderService orderService) {
        super(containerService, unitService);
        this.orderService = orderService;
    }


    //-------------------------------------------------------------

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("toOrder")
    public String toOrder(
            @RequestParam(name = "xContainerId") Long xContainerId,
            Model model) {
        /*
         * Задача:
         *   Отправить "раннюю" unit в заказ. Статус продукта должен измениться на ORDERED.
         *   Переход на displayOrders.
         * */
        System.out.println("======================================================================================");
        System.out.println(getClass().getSimpleName() + ".toOrder()...");
        System.out.println("принят id контейнера: xContainerId=" + xContainerId);

        AccessoryContainer xContainer = null;
        AccessoryUnit earliest = null;
        Page<_Order> actualPage = null;

        // текущий заказ
        _Order currentOrder = orderService.findCurrent();
        // Если нет текущего заказа, то предупреждение и page без изменений
        if (currentOrder == null) {
            System.out.println("нет текущ ордера");
            model.addAttribute("noCurrent", "noCurrent");
            // отправляем в модель неизмененную Page<_Order> из кэша
            actualPage = orderService.getCache().getPage();


        } else {
            // если currentOrder есть, то:
            System.out.println("Найден currentOrder.id=" + currentOrder.getId());

            try {
                // ищем xContainer по xContainerId
                xContainer = containerService.findById(xContainerId).get();
            } catch (NoSuchElementException e) {
                // если не найден берем из кэша
                xContainer = this.containerService.getAccessoryCache().getContainer();
            }

            // Найти "раннюю" unit в контейнере
            earliest = xContainer.getUnitList().stream()
                    .min(Comparator.comparing(AccessoryUnit::getCreatedAt))
                    .get();
            System.out.println("Найдена 'ранняя' unit из xContainer/ ее id=" + earliest.getId());

            // установить ей статус ORDERED и order = currentOrder
            earliest.setProductStatus(ProductStatus.ORDERED);
            earliest.setOrder(currentOrder);
            // убрать внешний ключ на контейнер
            earliest.setContainer(null);

            // добавить earliest в currentOrder и оповестить html
            List<AccessoryUnit> orderUnits = currentOrder.getAccessoryUnits();
            System.out.println("на данный момент в currentOrder есть аксессуары: " + orderUnits.toString());
            orderUnits.add(earliest);
            containerService.getCache().setOrderRefreshed(true);

            // убрать earliest из xContainer
            List<AccessoryUnit> xContainerUnits = xContainer.getUnitList();
            xContainerUnits.remove(earliest);                                             // не должно быть CascadeType.ALL и orphanRemoval=true
            // если в контейнере не осталось юнитов, то удалить контейнер
            if (xContainer.getUnitList().size() <= 0) {
                containerService.delete(xContainer);
            }

            // actualPage должна содержать currentOrder
            actualPage = orderService.getXPage(currentOrder);

            System.out.println("___конец метода toOrder().");
        }


        // Отправляем данные в модель. Используем orderService !!!
        // --------------------------->
        orderService.transferToModel(
                model,
                actualPage,
                null,
                currentOrder == null ? null : currentOrder.getId(),
                earliest,
                null,
                null
        );

        // Контрольное кэширование. Используем orderService !!!
        // ----------------------->
        orderService.getCache().cacheAttributesIfNotNull(
                actualPage,
                null,
                null,
                null,
                false,
                null
        );

        return "displayOrders";
    }
}
