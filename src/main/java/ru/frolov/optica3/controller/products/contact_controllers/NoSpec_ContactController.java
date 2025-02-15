package ru.frolov.optica3.controller.products.contact_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;


@Controller
public class NoSpec_ContactController
        extends AbstractContactController {


    public NoSpec_ContactController(ContactContainerService containerService, ContactUnitService unitService) {
        super(containerService, unitService);
    }

    @GetMapping("noSpec")
    public String noSpec(Model model) {

        /*
         * Задача:
         *   Создать и отобразить страницу no spec.
         * */



        // Подготовка данных для отправки в модель
        // ---------------------------------------
        // нужна page noSpec № 0
        containerService.getCache().setSpec(null);
        Page<ContactContainer> actualPage = containerService.getPageNOSpec(0);


        // Отправка данных в модель
        this.containerService.transferToModel(
                model,
                actualPage,
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null
        );


        return "displayContacts";
    }

    @PostMapping("noSpec")
    public String startPostRequest(Model model) {

        noSpec(model);

        return "displayContacts";
    }
}
