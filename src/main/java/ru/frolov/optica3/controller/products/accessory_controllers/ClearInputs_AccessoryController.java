package ru.frolov.optica3.controller.products.accessory_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;


@Controller
public class ClearInputs_AccessoryController
        extends AbstractAccessoryController {
    public ClearInputs_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }


    //-------------------------------------------------------------

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
        containerService.getAccessoryCache().setDto(new AccessoryDto());

        // page берем из кэша и отправляем без изменений
        Page<AccessoryContainer> samePage = containerService.getAccessoryCache().getPage();


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
        containerService.getAccessoryCache().cacheAttributesNotNull(
                samePage,
                null,
                null,
                null,
                null,
                null
        );

        return "displayAccessories";
    }
}
