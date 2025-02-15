package ru.frolov.optica3.controller.products.contact_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;

import java.util.List;

@Controller
public class Update_ContactController
        extends AbstractContactController {


    public Update_ContactController(ContactContainerService containerService, ContactUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("update")
    public String updateFrame(@RequestParam(name = "xContainerId") Long xContainerId,
                              ContactDto dto,
                              Model model) {

        /*
         * Метод обновляет контейнер по dto.
         * Задача:
         *   Обновить и отобразить страницу.
         * */

        // находим нужный контейнер по xOrderId
        ContactContainer xContainer = this.containerService.findById(xContainerId).get();


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
        ///////////////////////
        // contact
        if (dto.getContactDesign() != null) {
            xContainer.setContactDesign(dto.getContactDesign());
        }
        if (dto.getContactPeriod() != null) {
            xContainer.setContactPeriod(dto.getContactPeriod());
        }
        if (dto.getContactOxygen() != null) {
            xContainer.setContactOxygen(dto.getContactOxygen());
        }
        if (dto.getContactWater() != null) {
            xContainer.setContactWater(dto.getContactWater());
        }
        if (dto.getContactDiameter() != null) {
            xContainer.setContactDiameter(dto.getContactDiameter());
        }
        if (dto.getContactRadius() != null) {
            xContainer.setContactRadius(dto.getContactRadius());
        }
        if (dto.getDioptre() != null) {
            xContainer.setDioptre(dto.getDioptre().toString());
        }
        ////////////////////////

        // пытаемся найти контейнеры-дубли
        List<ContactContainer> dubls = this.containerService.dublsByX(xContainer);

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
        int pageNumber = containerService.getCache().getPage().getNumber();
        Specification<ContactContainer>specification = containerService.getCache().getSpec();
        Page<ContactContainer> actualPage;
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
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                dto,
                null,
                null,
                null
        );


        return "displayContacts";
    }
}
