package ru.frolov.optica3.controller.products.glass_controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;


@Controller
public class EditMode_GlassController
        extends AbstractGlassController {


    public EditMode_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
        super(containerService, unitService);
    }

    @GetMapping("editMode_on")
    public String editMode_on(Model model) {
        /*
         * Задача:
         *   Включить режим редактирования. Все должно остаться как было.
         * */

        // включить режим редактирования
        containerService.getCache().setMode(true);

        // Подготовка данных для модели
        // ----------------------------->
        // Отправляем данные в модель
        // --------------------------->
        // page остается без изменений
        containerService.transferToModel(
                model,
                containerService.getCache().getPage(),
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                null,
                null,
                null,
                null,
                null
        );

        return "displayGlasses";
    }

    @GetMapping("editMode_off")
    public String editMode_off(Model model) {
        /*
         * Задача:
         *   Выключить режим редактирования. Все должно остаться как было.
         * */
        containerService.getCache().setMode(false);


        // Отправляем данные в модель
        // --------------------------->
        // page без изменений
        containerService.transferToModel(
                model,
                containerService.getCache().getPage(),
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                null,
                null,
                null,
                null,
                false
        );

        return "displayGlasses";
    }
}
