package ru.frolov.optica3.controller.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.entity.contact.Contact;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.service.contact_services.Cache_ContactService;
import ru.frolov.optica3.service.contact_services.ContactContainerService;
import ru.frolov.optica3.service.contact_services.Model_ContactService;
import ru.frolov.optica3.service.contact_services.Pagination_ContactService;



@Controller
@RequiredArgsConstructor
public class Plus1_ContactController {

    private final ContactContainerService containerService;
    private final Cache_ContactService cacheService;
    private final Pagination_ContactService paginationService;
    private final Model_ContactService modelService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/plus1Contact")
    public String plus1Contact(@RequestParam(name = "xId") Long xId,
                        Model model) {


        // Находим нужный контейнер по полученному id
        final ContactContainer xContainer = this.containerService.findById(xId).orElse(null);
        Contact newContact = null;

        if (xContainer != null) {
            // создаем новую оправу с такими же значениями полей, как у контейнера
            newContact = new Contact();
            newContact.setFirm(xContainer.getFirm());
            ///////////////////////////////////////////////
            newContact.setDesign(xContainer.getDesign());
            newContact.setPeriod(xContainer.getPeriod());
            newContact.setOxygen(xContainer.getOxygen());
            newContact.setWater(xContainer.getWater());
            ///////////////////////////////////////////////
            newContact.setDetails(xContainer.getDetails());
            newContact.setPurchase(xContainer.getPurchase());
            newContact.setSale(xContainer.getSale());
            ////////////////////////
            newContact.setDiameter(xContainer.getDiameter());
            newContact.setRadius(xContainer.getRadius());
            ////////////////////////
            newContact.setDioptre(xContainer.getDioptre());

            // Соединяем xContainer и newFrame
            newContact.setContactContainer(xContainer);
            xContainer.addToContactList(newContact);
        }
        // сохраняем контейнер
        this.containerService.save(xContainer);


        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом добавления
        int pageNumber = Page_ContactCache.getPage().getNumber();
        Page<ContactContainer> actualPage = this.paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        // Отправка данных в модель
        // ------------------------>
        modelService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayContacts";
    }
}
