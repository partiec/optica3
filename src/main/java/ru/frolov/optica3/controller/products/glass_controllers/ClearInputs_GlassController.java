package ru.frolov.optica3.controller.products.glass_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;


@Controller
public class ClearInputs_GlassController
        extends AbstractGlassController {


    public ClearInputs_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
        super(containerService, unitService);
    }

    @PostMapping("clearInputs")
    public String clearInputs(
            Model model) {
        /*
         * Задача:
         *   В html должны очистится поля поиска. Остальное должно остаться без изменений.
         * */

        // Подготовка данных
        //-------------------
        // Кэшируем пустую dto. Из него пустые значения "очистят" поля html
        containerService.getCache().setDto(new GlassDto());

        // page берем из кэша и отправляем без изменений
        Page<GlassContainer> samePage = containerService.getCache().getPage();


        // Отправляем данные в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                samePage,
                null,
                null,
                null,
                null
        );

        return "displayGlasses";
    }
}
