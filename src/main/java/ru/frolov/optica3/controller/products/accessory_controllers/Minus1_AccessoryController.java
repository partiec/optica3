package ru.frolov.optica3.controller.products.accessory_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

import java.util.List;

@Controller
public class Minus1_AccessoryController
        extends AbstractAccessoryController {


    public Minus1_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("minus1")
    public String minus1Frame(@RequestParam(name = "xContainerId") Long xContainerId,
                              Model model) {
        /*
         * Задача:
         *   Удалить 1 юнит. Отобразить страницу с учетом удаленного.
         * */

        // Находим нужный контейнер по xOrderId
        AccessoryContainer xContainer = this.containerService.findById(xContainerId).get();

        // units в xContainer
        List<AccessoryUnit> xList = xContainer.getUnitList();

        // Если в контейнере осталась последняя оправа или ни одной, то удалить весь контейнер из БД
        if (xList.size() <= 1) {
            this.containerService.delete(xContainer);
        } else {
            // Иначе удалить из контейнера только firstElement
            AccessoryUnit firstElement = xContainer.getUnitList().get(0);
            xContainer.getUnitList().remove(firstElement);
            unitService.remove(firstElement);
        }

        // Подготовка данных для модели
        // ---------------------------->
        // создать новую page с тем же pageNumber и specification
        int pageNumber = containerService.getAccessoryCache().getPage().getNumber();
        Specification<AccessoryContainer>specification = containerService.getAccessoryCache().getSpec();
        Page<AccessoryContainer> actualPage;
        if (specification != null) {
            actualPage = containerService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = containerService.getPageNOSpec(pageNumber);
        }
        // Отправка данных в модель
        // ------------------------>
        containerService.transferToModel(
                model,
                actualPage,
                null,
                xContainerId,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        containerService.getAccessoryCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null,
                null
        );

        return "displayAccessories";
    }
}
