package ru.frolov.optica3.controller.contact_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.contact_cach.FiltersPayload_ContactCache;
import ru.frolov.optica3.cache.contact_cach.SpecStatus_ContactCache;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.service.contact_services.*;


@Controller
@RequiredArgsConstructor
public class NoSpec_ContactController {

    private final ContactContainerService containerService;
    private final Pagination_ContactService paginationService;
    private final ContactService contactService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;

    @GetMapping("api/noSpecContacts")
    public String noSpecContacts(Model model) {

        System.out.println("___noSpecContacts()...");

        // Подготовка данных для модели
        // ---------------------------->
        // кэшируем specStatus(false):
        SpecStatus_ContactCache.setApplied(false);
        // создаем page noSpec № 0
        Page<ContactContainer> actualPage = paginationService.createPageDependsOnSpecStatus(0, null);
        // кэшируем filters костыли
        FiltersPayload_ContactCache.setFiltersPayload(
                new Filters_ContactPayload(null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));


        // Отправка данных в модель
        modelService.transferModel(
                model,
                actualPage,
                null,
                null,
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

    @PostMapping("api/noSpecContacts")
    public String startPostRequest(Model model) {

        noSpecContacts(model);

        return "displayContacts";
    }
}
