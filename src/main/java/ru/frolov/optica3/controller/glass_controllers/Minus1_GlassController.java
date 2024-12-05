package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.glass_caches.Page_GlassCache;
import ru.frolov.optica3.entity.glass.Glass;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.service.glass_services.Cache_GlassService;
import ru.frolov.optica3.service.glass_services.GlassContainerService;
import ru.frolov.optica3.service.glass_services.Model_GlassService;
import ru.frolov.optica3.service.glass_services.Pagination_GlassService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Minus1_GlassController {

    private final GlassContainerService containerService;
    private final Pagination_GlassService paginationService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/minus1Glass")
    public String minus1Glass(@RequestParam(name = "xId") Long xId,
                         Model model) {


        // Находим нужный контейнер по полученному id
        GlassContainer xContainer = this.containerService.findById(xId).get();

        // единицы товара из xContainer
        List<Glass> xList = xContainer.getGlassList();

        // Если в контейнере осталась последняя оправа или ни одной, то удалить весь контейнер из БД
        if (xList.size() <= 1) {
            this.containerService.delete(xContainer);
        } else {
            // Иначе удалить из контейнера только firstElement
            Glass firstElement = xContainer.getGlassList().get(0);
            xContainer.getGlassList().remove(firstElement);
            // сохранить контейнер в БД
            this.containerService.save(xContainer);
        }

        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом удаления
        int pageNumber = Page_GlassCache.getPage().getNumber();
        Page<GlassContainer> actualPage = this.paginationService.createPageDependsOnSpecStatus(pageNumber, null);

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

        return "displayGlasses";
    }
}
