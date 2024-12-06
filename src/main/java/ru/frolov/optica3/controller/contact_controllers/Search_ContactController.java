package ru.frolov.optica3.controller.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.contact_cach.FiltersPayload_ContactCache;
import ru.frolov.optica3.cache.contact_cach.SpecStatus_ContactCache;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.service.contact_services.Cache_ContactService;
import ru.frolov.optica3.service.contact_services.ContactContainerService;
import ru.frolov.optica3.service.contact_services.Model_ContactService;
import ru.frolov.optica3.service.contact_services.Pagination_ContactService;


@Controller
@RequiredArgsConstructor
public class Search_ContactController {

    private final ContactContainerService containerService;
    private final Pagination_ContactService paginationService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;
//-------------------------------------------------------------------------------------------


    @PostMapping("api/searchContacts")
    public String searchContacts(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            Filters_ContactPayload filters,
            Model model) {


        System.out.println(".............................................................................................................................");
        System.out.println("___searchContact()...Filters(" +
                filters.firm() + "," +
                filters.design() + "," +
                filters.period() + "," +
                filters.oxygen() + "," +
                filters.water() + "," +
                filters.details() + "," +
                filters.purchase()  + "," +
                filters.sale()  + "," +
                filters.diameter()  + "," +
                filters.radius()  + "," +
                filters.dioptre() + ");");
        System.out.println("//////////// whichFieldOnInput = '" + whichFieldOnInput + "'  !!!");

        // Подготовка данных для модели
        // --------------------------->
        // кэшируем specStatus bySpec, поскольку page должна быть создана bySpec
        SpecStatus_ContactCache.setApplied(true);
        // кэшируем фильтры
        FiltersPayload_ContactCache.setFiltersPayload(filters);
        // создаем страницу № 0 в зависимости от specStatus (здесь bySpec)
        Page<ContactContainer> actualPage = this.paginationService.createPageDependsOnSpecStatus(0, whichFieldOnInput);


        // Отправка данных в модель
        // ------------------------->
        modelService.transferModel(
                model,
                actualPage,
                null,
                null,
                whichFieldOnInput,
                null);

        // Контрольное кэширование
        cacheService.cacheAttributes(
                actualPage,
                null,
                filters,
                null);


        return "displayContacts";
    }


}
