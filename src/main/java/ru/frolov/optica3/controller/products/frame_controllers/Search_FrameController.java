package ru.frolov.optica3.controller.products.frame_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;


@Controller
public class Search_FrameController
        extends AbstractFrameController {


    public Search_FrameController(FrameContainerService containerService, FrameUnitService unitService) {
        super(containerService, unitService);
    }

    @PostMapping("search")
    public String search(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            FrameDto dto,
            Model model) {

        /*
         * Метод ищет контейнеры по введенным данным.
         * Задача:
         *   Отобразить страницу, содержащую контейнеры, "похожие" на введенное
         * */

        // Подготовка данных для модели
        // --------------------------->
        // кэшируем принятое dto (заодно добавляем ProductCategory.FRAME)
        dto.setProductCategory(ProductCategory.FRAME);
        containerService.getCache().setDto(dto);

        // page однозначно должна быть bySpec
        Specification<FrameContainer>specification = containerService.getSpec().allContains(dto);
        containerService.getCache().setSpec(specification);

        // создаем page №0 по spec (spec уже в кэше)
        Page<FrameContainer> actualPage =
                containerService.getPageBYSpec(0,specification);


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


        return "displayFrames";
    }
}
