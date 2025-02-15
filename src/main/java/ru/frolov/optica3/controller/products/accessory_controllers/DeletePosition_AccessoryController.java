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
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

@Controller
public class DeletePosition_AccessoryController
        extends AbstractAccessoryController {


    public DeletePosition_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("deletePosition")
    public String deletePosition(
            @RequestParam(name = "xContainerId") Long xContainerId,
                                 Model model) {

        /*
         * Задача:
         *   Удалить container по xOrderId и отобразить страницу с учетом удаленного
         *
         * */

        // удаляем контейнер по xOrderId
        containerService.deleteById(xContainerId);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться с тем же номером и spec, но обновлена с учетом удаленного
        int pageNumber = containerService.getAccessoryCache().getPage().getNumber();
        Specification<AccessoryContainer> specification = containerService.getAccessoryCache().getSpec();

        // создать page в зависимости от specStatus
        Page<AccessoryContainer> actualPage;
        if (specification != null) {
            actualPage = containerService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = containerService.getPageNOSpec(pageNumber);
        }

        // Отправляем данные в модель
        // --------------------------->
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
