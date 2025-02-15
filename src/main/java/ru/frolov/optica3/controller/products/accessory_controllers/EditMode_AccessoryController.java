package ru.frolov.optica3.controller.products.accessory_controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;


@Controller
public class EditMode_AccessoryController
        extends AbstractAccessoryController {


    public EditMode_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    @GetMapping("editMode_on")
    public String editMode_on(Model model) {
        /*
         * Задача:
         *   Включить режим редактирования. Все должно остаться как было.
         * */

        // включить режим редактирования
        containerService.getAccessoryCache().setMode(true);

        // Подготовка данных для модели
        // ----------------------------->
        // Отправляем данные в модель
        // --------------------------->
        // page остается без изменений
        containerService.transferToModel(
                model,
                containerService.getAccessoryCache().getPage(),
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        containerService.getAccessoryCache().cacheAttributesNotNull(
                null,
                null,
                null,
                null,
                null,
                null
        );

        return "displayAccessories";
    }

    @GetMapping("editMode_off")
    public String editMode_off(Model model) {
        /*
         * Задача:
         *   Выключить режим редактирования. Все должно остаться как было.
         * */
        containerService.getAccessoryCache().setMode(false);


        // Отправляем данные в модель
        // --------------------------->
        // page без изменений
        containerService.transferToModel(
                model,
                containerService.getAccessoryCache().getPage(),
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        containerService.getAccessoryCache().cacheAttributesNotNull(
                null,
                null,
                null,
                null,
                null,
                false
        );

        return "displayAccessories";
    }
}
