package ru.frolov.optica3.controller.products.accessory_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;


@Controller
public class Search_AccessoryController
        extends AbstractAccessoryController {


    public Search_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    @PostMapping("search")
    public String search(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            AccessoryDto dto,
            Model model) {

        System.out.println("-----------------------------------------------------");
        System.out.println("\t! " + getClass().getSimpleName() + ".search()");
        /*
         * Метод ищет контейнеры по введенным данным.
         * Задача:
         *   Отобразить страницу, содержащую контейнеры, "похожие" на введенное
         * */

        // Подготовка данных для модели
        // --------------------------->
        // кэшируем принятое dto (заодно добавляем ProductCategory.FRAME)
        dto.setProductCategory(ProductCategory.ACCESSORY);
        containerService.getAccessoryCache().setDto(dto);

        // page однозначно должна быть bySpec
        Specification<AccessoryContainer> specification = containerService.getAccessorySpec().allContains(dto);
        containerService.getAccessoryCache().setSpec(specification);

        // создаем page №0 по spec (spec уже в кэше)
        Page<AccessoryContainer> actualPage =
                containerService.getPageBYSpec(0, specification);


        // Отправка данных в модель
        // ------------------------->
        containerService.transferToModel(
                model,
                actualPage,
                null,
                null,
                whichFieldOnInput,
                null);

        // Контрольное кэширование
        containerService.getAccessoryCache().cacheAttributesNotNull(
                actualPage,
                dto,
                null,
                null,
                null,
                null
        );


        return "displayAccessories";
    }
}
