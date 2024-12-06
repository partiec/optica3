package ru.frolov.optica3.controller.glass_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.glass_cach.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_cach.Page_GlassCache;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.glass_services.Cache_GlassService;
import ru.frolov.optica3.service.glass_services.Model_GlassService;


@Controller
@RequiredArgsConstructor
public class ClearInputs_GlassController {

    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;

    @PostMapping("api/clearGlassInputs")
    public String clearGlassInputs(Model model) {

        System.out.println("___clearGlassInputs();");

        // Кэшируем костыль filters
        FiltersPayload_GlassCache.setFiltersPayload(new Filters_GlassPayload(null, null, null, null, null, null, null, null));

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<GlassContainer> samePage = Page_GlassCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayGlasses";
    }
}
