package ru.frolov.optica3.controller.products.accessory_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

import java.util.List;

@Controller
public class Update_AccessoryController
        extends AbstractAccessoryController {


    public Update_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("update")
    public String updateFrame(@RequestParam(name = "xContainerId") Long xContainerId,
                              AccessoryDto dto,
                              Model model) {

        /*
         * Метод обновляет контейнер по dto.
         * Задача:
         *   Обновить и отобразить страницу.
         * */

        // находим нужный контейнер по xOrderId
        AccessoryContainer xContainer = this.containerService.findById(xContainerId).get();


        // если в dto какое-то из полей не null, значит
        // измененное значение ИМЕННО ЭТОГО ПОЛЯ пришло из формы.
        // Устанавливаем это значение в контейнер, а старое значение затирается.
        if (dto.getProductCategory() != null) {
            xContainer.setProductCategory(dto.getProductCategory());
        }
        if (dto.getFirm() != null) {
            xContainer.setFirm(dto.getFirm());
        }
        if (dto.getModel() != null) {
            xContainer.setModel(dto.getModel());
        }
        if (dto.getDetails() != null) {
            xContainer.setDetails(dto.getDetails());
        }
        if (dto.getPurchase() != null) {
            xContainer.setPurchase(dto.getPurchase());
        }
        if (dto.getSale() != null) {
            xContainer.setSale(dto.getSale());
        }
        ////////////////////////////////////////////
        // accessory
        if (dto.getAccessoryCategory() != null) {
            xContainer.setAccessoryCategory(dto.getAccessoryCategory());
        }
        ////////////////////////////////////////////

        // пытаемся найти контейнеры-дубли
        List<AccessoryContainer> dubls = this.containerService.dublsByX(xContainer);

        // если есть дубли
        if (!dubls.isEmpty()) {

            xContainer = dubls.get(0);
            // применить метод, который
            //  - извлекает frames из контейнеров-дублей (дубли удаляет),
            //  - переименовывает их поля, чтоб были как у xContainer
            //  - вставляет обновленные frames в xContainer (xContainer становится единственным)
            this.containerService.killDubls(xContainer, dubls.subList(1, dubls.size()));
        }

//        // сохраняем контейнер
//        this.containerService.save(xContainer);

        // Подготовка данных для модели
        // ---------------------------->
        // новая page должна остаться с тем же номером и spec
        int pageNumber = containerService.getAccessoryCache().getPage().getNumber();
        Specification<AccessoryContainer>specification = containerService.getAccessoryCache().getSpec();
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
        // ------------------------->
        containerService.getAccessoryCache().cacheAttributesNotNull(
                actualPage,
                dto,
                null,
                null,
                null,
                null
        );


        return "displayAccessories";
    }
}
