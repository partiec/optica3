package ru.frolov.optica3.controller.order_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.service.order.OrderService;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;

import java.util.Set;

@Controller
public class Update_OrderController
        extends AbstractOrderController {

    private final AccessoryContainerService accessoryContainerService;
    private final ContactContainerService contactContainerService;
    private final FrameContainerService frameContainerService;
    private final GlassContainerService glassContainerService;

    public Update_OrderController(OrderService orderService, AccessoryContainerService accessoryContainerService, ContactContainerService contactContainerService, FrameContainerService frameContainerService, GlassContainerService glassContainerService) {
        super(orderService);
        this.accessoryContainerService = accessoryContainerService;
        this.contactContainerService = contactContainerService;
        this.frameContainerService = frameContainerService;
        this.glassContainerService = glassContainerService;
    }

    @Transactional
    @PostMapping("update")
    public String update(@RequestParam(name = "xOrderId", required = false) Long xOrderId,
                         OrderAndClientDto dto,
                         Model model) {

        /*
         * Метод обновляет значения полей по dto.
         * Задача:
         *   Обновить и отобразить страницу.
         * */


        System.out.println("==================================================");
        System.out.println(getClass().getSimpleName() + ".update()...");
        System.out.println("принят xOrderId=" + xOrderId);
        System.out.println("принято dto(" +
                dto.toString());


        // текущий ордер
        _Order current = orderService.findCurrent();
        System.out.println("сейчас current это: id=" + (current == null ? "current is null" : current.getId()));
        if (current != null) {
            current.setCurrent(null);
        }

        // xOrder
        _Order xOrder = orderService.findById(xOrderId).get();
        System.out.println("xOrder.id = " + xOrder.getId());

        // устанавливае поля, которые not null
        //-------------------------------------
        if (dto.getCurrent() != null) {

            // обновляем xOrder
            xOrder.setCurrent(dto.getCurrent());

            // заново ищем current
            current = orderService.findCurrent();

            // cache
            orderService.getOrderCache().setCurrentOrder(current);

        }

        // ЕСЛИ В ТОВАРНОМ КЭШЕ ЧТО-ТО ЕСТЬ, СРАЗУ В ЗАКАЗ
        System.out.println("[ accessoryContainerService.getAccessoryCache().getChecks().size()=" + accessoryContainerService.getAccessoryCache().getChecks().size());
        Set<Long> checks = accessoryContainerService.getAccessoryCache().getChecks();
        if (checks.size() > 0) {         // !
            System.out.println("[ACCESSORY CACHE IS NOT EMPTY] redirect:/api/accessory/toOrder");
            // ЗАКЭШИРОВАТЬ xOrder !!! Будет доступен в toOrder !!!
            orderService.getOrderCache().setCurrentOrder(xOrder);
            return "redirect:/api/accessory/toOrder";
        }//...остальные кэши...

        if (dto.getStage() != null) {
            xOrder.setStage(dto.getStage());
        }
        if (dto.getPayment() != null) {
            xOrder.setPayment(dto.getPayment());
        }
        if (dto.getOrderDetails() != null) {
            xOrder.setOrderDetails(dto.getOrderDetails());
        }
        if (dto.getPrice() != null) {
            xOrder.setPrice(dto.getPrice());
        }
        if (dto.getLastName() != null) {
            xOrder.setLastName(dto.getLastName());
        }
        if (dto.getFirstName() != null) {
            xOrder.setFirstName(dto.getFirstName());
        }
        if (dto.getBirthday() != null) {
            xOrder.setBirthday(dto.getBirthday());
        }
        if (dto.getPatronymic() != null) {
            xOrder.setPatronymic(dto.getPatronymic());
        }
        if (dto.getPassport() != null) {
            xOrder.setPassport(dto.getPassport());
        }
        if (dto.getClientDetails() != null) {
            xOrder.setClientDetails(dto.getClientDetails());
        }
        if (dto.getOrderDetails() != null) {
            xOrder.setOrderDetails(dto.getOrderDetails());
        }


//        // сохраняем контейнер
//        this.containerService.save(xContainer);

        // Подготовка данных для модели
        // ---------------------------->
        // новая page должна остаться с тем же номером и spec
        int pageNumber = orderService.getOrderCache().getPage().getNumber();
        Specification<_Order> specification = orderService.getOrderCache().getSpec();
        Page<_Order> actualPage;
        if (specification != null) {
            actualPage = orderService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = orderService.getPageNOSpec(pageNumber);
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
        // ------------------------->
        orderService.getOrderCache().cacheAttributesIfNotNull(
                actualPage,
                dto,
                null,
                null,
                null,
                null
        );


        return "displayOrders";
    }


}
