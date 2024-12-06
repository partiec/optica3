package ru.frolov.optica3.controller.contact_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.service.contact_services.*;


@Controller
@RequiredArgsConstructor
public class Start_ContactController {

    private final ContactContainerService containerService;
    private final Pagination_ContactService paginationService;
    private final ContactService contactService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;

    private final NoSpec_ContactController noSpecController;
    //-----------------------------------------------------------------------------------------------

    @GetMapping("api/startContact")
    public String startContact(Model model) {

        System.out.println("___startContact();");

        // Подготовка кэша при старте
        // ---------------------------->
        // в качестве начальных данных кэшируем костыль filters:
        cacheService.cacheAttributes(null, null,
                new Filters_ContactPayload(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null), null);


        return noSpecController.noSpecContacts(model);
    }

    @PostMapping("api/startContact")
    public String startPostContact(Model model) {

        startContact(model);

        return "displayContacts";
    }
}
