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
public class Plus1_AccessoryController
        extends AbstractAccessoryController {


    public Plus1_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("plus1")
    public String plus1(@RequestParam(name = "xContainerId") Long xContainerId,
                        Model model) {
        /*
         * Задача:
         *   Создать сущность и отобразить страницу с ужетом изменений.
         * */


        // Находим нужный контейнер по xOrderId
        AccessoryContainer xContainer = this.containerService.findById(xContainerId).orElse(null);

        // new unit
        AccessoryUnit newUnit = null;

        // если контейнер найден
        if (xContainer != null) {
            // создаем новую unit с такими же значениями полей, как у контейнера
            newUnit = new AccessoryUnit();

            newUnit.setProductCategory(xContainer.getProductCategory());
            newUnit.setFirm(xContainer.getFirm());
            newUnit.setModel(xContainer.getModel());
            newUnit.setDetails(xContainer.getDetails());
            newUnit.setPurchase(xContainer.getPurchase());
            newUnit.setSale(xContainer.getSale());
            //////////////////////////////////////////
            newUnit.setAccessoryCategory(xContainer.getAccessoryCategory());
            //////////////////////////////////////////

            // сохраняем new unit (для обретения id)
            unitService.save(newUnit);

            // Соединяем xContainer и newFrameUnit
            newUnit.setContainer(xContainer);
            List<AccessoryUnit> xContainerUnits = xContainer.getUnitList();
            xContainerUnits.add(newUnit);


        } // если контейнер не найден - ничего не происходит


        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом добавления
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
                null);

        return "displayAccessories";
    }
}
