package ru.frolov.optica3.controller.products.accessory_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;


@Controller
public class NoSpec_AccessoryController
        extends AbstractAccessoryController {


    public NoSpec_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    @GetMapping("noSpec")
    public String noSpec(Model model) {

        /*
         * Задача:
         *   Создать и отобразить страницу no spec.
         * */


        // Подготовка данных для отправки в модель
        // ---------------------------------------
        // нужна page noSpec № 0
        containerService.getAccessoryCache().setSpec(null);
        Page<AccessoryContainer> actualPage = this.containerService.getPageNOSpec(0);


        // Отправка данных в модель
        this.containerService.transferToModel(
                model,
                actualPage,
                null,
                null,
                null,
                null
        );

        // Контрольное кэширование
        // ----------------------->
        containerService.getAccessoryCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null,
                null
        );



        return "displayAccessories";
    }

    @PostMapping("noSpec")
    public String startPostRequest(Model model) {

        noSpec(model);

        return "displayAccessories";
    }
}
