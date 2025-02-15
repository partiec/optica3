package ru.frolov.optica3.controller.products.glass_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;


@Controller
public class Search_GlassController
        extends AbstractGlassController {


    public Search_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
        super(containerService, unitService);
    }

    @PostMapping("search")
    public String search(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            GlassDto dto,
            Model model) {


        /*
         * Метод ищет контейнеры по введенным данным.
         * Задача:
         *   Отобразить страницу, содержащую контейнеры, "похожие" на введенное
         * */

        // Подготовка данных для модели
        // --------------------------->
        // кэшируем принятое dto (заодно добавляем ProductCategory.FRAME)
        dto.setProductCategory(ProductCategory.GLASS);
        containerService.getCache().setDto(dto);

        // page однозначно должна быть bySpec
        Specification<GlassContainer> specification = containerService.getSpec().allContains(dto);
        containerService.getCache().setSpec(specification);

        // создаем page №0 по spec (spec уже в кэше)
        Page<GlassContainer> actualPage =
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
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                dto,
                null,
                null,
                null
        );


        return "displayGlasses";
    }
}
