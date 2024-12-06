package ru.frolov.optica3.controller.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.contact_cach.FiltersPayload_ContactCache;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.service.contact_services.*;

@Controller
@RequiredArgsConstructor
public class CopyToSearch_ContactController {

    private final ContactContainerService containerService;
    private final ContactService contactService;
    private final Pagination_ContactService paginationService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/copyToSearchContacts")
    public String copyToSearchContacts(@RequestParam(name = "xId") Long xId,
                                 Model model) {

        // find xContainer
        ContactContainer xContainer = this.containerService.findById(xId).get();

        // set fields values to FiltersPayload and cache
        FiltersPayload_ContactCache.setFiltersPayload(new Filters_ContactPayload(
                xContainer.getFirm(),
                ////////////////////
                xContainer.getDesign(),
                xContainer.getPeriod(),
                xContainer.getOxygen(),
                xContainer.getWater(),
                /////////////////////
                xContainer.getDetails(),
                xContainer.getPurchase(),
                xContainer.getSale(),
                ///////////////
                xContainer.getDiameter(),
                xContainer.getRadius(),
                ///////////////
                xContainer.getDioptre()
        ));

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<ContactContainer> samePage = Page_ContactCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                "copyToSearch");

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayContacts";
    }
}
