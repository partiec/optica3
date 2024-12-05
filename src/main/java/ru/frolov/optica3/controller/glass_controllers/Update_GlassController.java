package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.glass_caches.Page_GlassCache;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.GlassPayload;
import ru.frolov.optica3.service.glass_services.Cache_GlassService;
import ru.frolov.optica3.service.glass_services.GlassContainerService;
import ru.frolov.optica3.service.glass_services.Model_GlassService;
import ru.frolov.optica3.service.glass_services.Pagination_GlassService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Update_GlassController {


    private final GlassContainerService containerService;
    private final Pagination_GlassService paginationService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;


    //------------------------------------------------------------

    @PostMapping("api/updateGlass")
    public String updateGlass(@RequestParam(name = "xId") Long xId,
                              GlassPayload glassPayload,
                              Model model) {

        // находим нужный контейнер по xId
        GlassContainer xContainer = this.containerService.findById(xId).get();


        // если во framePayload какое-то из полей не null, значит
        // измененное значение именно этого поля пришло из формы.
        // Устанавливаем это значение в контейнер, а старое значение затирается.
        if (glassPayload.firm() != null) {
            xContainer.setFirm(glassPayload.firm());
        }
        /////////////////////////////////////////////////////////
        if (glassPayload.material() != null) {
            xContainer.setMaterial(glassPayload.material());
        }
        if (glassPayload.design() != null) {
            xContainer.setDesign(glassPayload.design());
        }
        // раскрывающиеся списки
        if (glassPayload.coat() != null) {
            xContainer.setCoat(glassPayload.coat());
        }
        /////////////////////////////////////////////////////////
        if (glassPayload.details() != null) {
            xContainer.setDetails(glassPayload.details());
        }
        if (glassPayload.purchase() != null) {
            xContainer.setPurchase(glassPayload.purchase());
        }
        if (glassPayload.sale() != null) {
            xContainer.setSale(glassPayload.sale());
        }
        /////////////
        if (glassPayload.dioptre() != null) {
            xContainer.setDioptre(glassPayload.dioptre());
        }
        /////////////


        // пытаемся найти контейнеры-дубли
        List<GlassContainer> dubls = this.containerService.dubls(xContainer);
        // если есть дубли
        if (!dubls.isEmpty()) {
            // применить метод из containerService, который
            //  - извлекает frames из контейнеров-дублей (дубли удаляет),
            //  - переименовывает их поля, чтоб были как у xContainer
            //  - вставляет обновленные frames в xContainer (xContainer становится единственным)
            this.containerService.killDubls(xContainer, dubls);
        }

        // сохраняем измененный контейнер
        this.containerService.save(xContainer);

        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться с тем же номером и spec, но обновлена (для info html)
        int pageNumber = Page_GlassCache.getPage().getNumber();
        Page<GlassContainer> actualPage = this.paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                actualPage,
                pageNumber,
                xId,
                null,
                null);

        // Контрольное кэширование
        // ------------------------->
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                glassPayload);


        return "displayGlasses";
    }


}
