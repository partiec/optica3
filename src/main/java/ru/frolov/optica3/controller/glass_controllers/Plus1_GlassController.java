package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.glass_cach.Page_GlassCache;
import ru.frolov.optica3.entity.glass.Glass;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.service.glass_services.Cache_GlassService;
import ru.frolov.optica3.service.glass_services.GlassContainerService;
import ru.frolov.optica3.service.glass_services.Model_GlassService;
import ru.frolov.optica3.service.glass_services.Pagination_GlassService;


@Controller
@RequiredArgsConstructor
public class Plus1_GlassController {

    private final GlassContainerService containerService;
    private final Cache_GlassService cacheService;
    private final Pagination_GlassService paginationService;
    private final Model_GlassService modelService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/plus1Glass")
    public String plus1Glass(@RequestParam(name = "xId") Long xId,
                        Model model) {


        // Находим нужный контейнер по полученному id
        final GlassContainer xContainer = this.containerService.findById(xId).orElse(null);
        Glass newGlass = null;

        if (xContainer != null) {
            // создаем новую оправу с такими же значениями полей, как у контейнера
            newGlass = new Glass();
            newGlass.setFirm(xContainer.getFirm());
            ///////////////////////////////////////////////
            newGlass.setMaterial(xContainer.getMaterial());
            newGlass.setDesign(xContainer.getDesign());
            newGlass.setCoat(xContainer.getCoat());
            ///////////////////////////////////////////////
            newGlass.setDetails(xContainer.getDetails());
            newGlass.setPurchase(xContainer.getPurchase());
            newGlass.setSale(xContainer.getSale());
            newGlass.setDioptre(xContainer.getDioptre());

            // Соединяем xContainer и newFrame
            newGlass.setGlassContainer(xContainer);
            xContainer.addToGlassList(newGlass);
        }
        // сохраняем контейнер
        this.containerService.save(xContainer);


        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом добавления
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
