package ru.frolov.optica3.controller.products.contact_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;

@Controller
public class CopyToSearch_ContactController
        extends AbstractContactController {


    public CopyToSearch_ContactController(ContactContainerService containerService, ContactUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("copyToSearch")
    public String copyToSearch(@RequestParam(name = "xContainerId") Long xContainerId,
                               Model model) {

        /*
         * Задача:
         *   Скопировать контейнер в блок поиска чтобы не вбивать вручную.
         * */


        // find xContainer
        ContactContainer xContainer = this.containerService.findById(xContainerId).get();

        // создаем и кэшируем dto на основе xContainer
        ContactDto dto = new ContactDto();
        dto.setProductCategory(xContainer.getProductCategory());
        dto.setFirm(xContainer.firm);
        dto.setModel(xContainer.model);
        dto.setDetails(xContainer.details);
        dto.setPurchase(xContainer.getPurchase());
        dto.setSale(xContainer.getSale());
        //////////////////////////////////
        dto.setContactDesign(xContainer.getContactDesign());
        dto.setContactPeriod(xContainer.getContactPeriod());
        dto.setContactOxygen(xContainer.getContactOxygen());
        dto.setContactWater(xContainer.getContactWater());
        dto.setContactDiameter(xContainer.getContactDiameter());
        dto.setContactRadius(xContainer.getContactRadius());
        dto.setDioptre(xContainer.getDioptre());
        //////////////////////////////////
        containerService.getCache().setDto(dto);

        // page без изменений
        Page<ContactContainer> samePage =
                containerService.getCache().getPage();


        // Отправляем данные в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                samePage,
                null,
                null,
                null,
                "copyToSearch");

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                samePage,
                null,
                null,
                xContainer,
                null);

        return "displayContacts";
    }
}
