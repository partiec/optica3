package ru.frolov.optica3.controller.products.contact_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;


@Controller
public class Start_ContactController
        extends AbstractContactController {

    private final NoSpec_ContactController noSpec;

    public Start_ContactController(ContactContainerService containerService, ContactUnitService unitService, NoSpec_ContactController noSpec) {
        super(containerService, unitService);
        this.noSpec = noSpec;
    }
//--------------------------------------------------------------------------------



    @GetMapping("start")
    public String start(Model model) {


        return noSpec.noSpec(model);
    }

    @PostMapping("start")
    public String startPostContact(Model model) {

        start(model);

        return "displayContacts";
    }
}
