package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.glass_caches.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_caches.SpecStatus_GlassCache;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.glass_services.Cache_GlassService;
import ru.frolov.optica3.service.glass_services.GlassContainerService;
import ru.frolov.optica3.service.glass_services.Model_GlassService;
import ru.frolov.optica3.service.glass_services.Pagination_GlassService;


@Controller
@RequiredArgsConstructor
public class Search_GlassController {

    private final GlassContainerService containerService;
    private final Pagination_GlassService paginationService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;
//-------------------------------------------------------------------------------------------


    @PostMapping("api/searchGlasses")
    public String searchGlasses(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            Filters_GlassPayload filters,
            Model model) {


        System.out.println(".............................................................................................................................");
        System.out.println("___searchGlasses()...Filters(" +
                filters.firm() + "," +
                filters.material() + "," +
                filters.design() + "," +
                filters.coat() + "," +
                filters.details() + "," +
                filters.purchase()  + "," +
                filters.sale()  + "," +
                filters.dioptre() + ");");
        System.out.println("//////////// whichFieldOnInput = '" + whichFieldOnInput + "'  !!!");

        // Подготовка данных для модели
        // --------------------------->
        // кэшируем specStatus bySpec, поскольку page должна быть создана bySpec
        SpecStatus_GlassCache.setApplied(true);
        // кэшируем фильтры
        FiltersPayload_GlassCache.setFiltersPayload(filters);
        // создаем страницу № 0 в зависимости от specStatus (здесь bySpec)
        Page<GlassContainer> actualPage = this.paginationService.createPageDependsOnSpecStatus(0, whichFieldOnInput);


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


        return "displayGlasses";
    }


}
