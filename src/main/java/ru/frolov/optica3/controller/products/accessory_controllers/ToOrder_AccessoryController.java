package ru.frolov.optica3.controller.products.accessory_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.service.order.OrderService;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

import java.util.*;
import java.util.stream.Collectors;


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
            @RequestParam(name = "xContainerId", required = false) Long xContainerId,
            Model model,
            @RequestParam(name = "otherOrder", required = false) String otherOrder,
            @RequestParam(name = "check", required = false) Long... check
    ) {
        /*
         * Задача:
         *   Отправить "раннюю" unit в заказ. Статус продукта должен измениться на ORDERED.
         *   Переход на displayOrders.
         * */


        System.out.println("======================================================================================");
        System.out.println(getClass().getSimpleName() + ".toOrder()...");
        System.out.println("принят xContainerId=" + xContainerId);
        System.out.println("ПРИНЯТЫ check...=" + Arrays.toString(check));

        List<AccessoryContainer> xContainers = new ArrayList<>();
        List<AccessoryUnit> earliests = new ArrayList<>();
        Page<_Order> actualPage = null;

        // не имеется ли УЖЕ в кэше чеков
        Set<Long> alreadyChecks = containerService.getAccessoryCache().getChecks();
        System.out.println("в кэше уже есть чеки = " + alreadyChecks.size());


        // кэшируем checks
        Set<Long> checksRecieved = new HashSet<>();
        if (check != null) {
            checksRecieved = Arrays.stream(check).collect(Collectors.toSet());
            containerService.getAccessoryCache().getChecks().addAll(checksRecieved);
        }

        _Order currentOrder;
        // текущий заказ
        if (otherOrder != null) {
            currentOrder = null;
        } else {
            currentOrder = orderService.findCurrent();
        }
        // Если нет текущего заказа
        if (currentOrder == null) {
            System.out.println("нет текущ ордера");
            model.addAttribute("noCurrent", "noCurrent");
            // отправляем в модель неизмененную Page<_Order> из кэша
            actualPage = orderService.getOrderCache().getPage();


        } else {
            // если currentOrder есть, то:
            System.out.println("Найден currentOrder.id=" + currentOrder.getId());


            // ищем xContainers по xContainerId или по check
            //-------------------------------------------------
            // если в кэше уже были checks
            if (alreadyChecks.size() > 0) {
                for (Long id : alreadyChecks) {
                    xContainers.add(containerService.findById(id).get());
                }
            }
            // если пришел check
            else if (checksRecieved.size() > 0) {

                for (Long id : checksRecieved) {
                    xContainers.add(containerService.findById(id).get());
                }

            } else {
                // если check не пришел, то ищем xContainer по xContainerId
                // а если xContainerId нету, значит мы пришли сюда из update
                Optional<AccessoryContainer> xContainerOpt = containerService.findById(xContainerId);
                if (xContainerOpt.isPresent()) {
                    xContainers.add(xContainerOpt.get());
                } else {

                }
            }

            // чистим checks
            containerService.getAccessoryCache().setChecks(new HashSet<>());

            // перебираем xContainers и ищем в них "ранние" unitы
            for (AccessoryContainer container : xContainers) {
                AccessoryUnit earliest = container.getUnitList().stream()
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
                containerService.getOrderCache().setOrderRefreshed(true);
                // убрать earliest из xContainer
                List<AccessoryUnit> xContainerUnits = container.getUnitList();
                xContainerUnits.remove(earliest);                                             // не должно быть CascadeType.ALL и orphanRemoval=true
                // если в контейнере не осталось юнитов, то удалить контейнер
                if (container.getUnitList().size() <= 0) {
                    containerService.delete(container);
                }


                earliests.add(earliest);  // нужен ли ?? раз мы все делаем в цикле для кажд
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
                null,
                null,
                null
        );

        // Контрольное кэширование. Используем orderService !!!
        // ----------------------->
        orderService.getOrderCache().cacheAttributesIfNotNull(
                actualPage,
                null,
                null,
                null,
                false,
                null
        );

        return "displayOrders";
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @GetMapping("toOrder")
    public String toOrderGet(
            @RequestParam(name = "xContainerId", required = false) Long xContainerId,
            Model model,
            @RequestParam(name = "otherOrder", required = false) String otherOrder,
            @RequestParam(name = "check", required = false) Long... check) {

        return toOrder(xContainerId, model, otherOrder, check);
    }
}
