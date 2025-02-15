package ru.frolov.optica3.controller.products.glass_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;

@Controller
public class FlipPages_GlassController
        extends AbstractGlassController {


    public FlipPages_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
        super(containerService, unitService);
    }

    @GetMapping("flipPage/{pageNumber:\\d+}")
    public String flipFramePage(
            @PathVariable(name = "pageNumber") Integer pageNumber,
            Model model) {
        /*
         *
         * */

        // Подготовка данных для модели
        // ------------------------------>
        // page должна быть с другим pageNumber, но specification та же
        Page<GlassContainer> actualPage;
        Specification<GlassContainer>specification = containerService.getCache().getSpec();
        if (specification != null) {
            actualPage = containerService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = containerService.getPageNOSpec(pageNumber);
        }

        // Отправка данных в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                actualPage,
                pageNumber,
                null,
                null,
                null);

        // Контрольное кэширование
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null
        );

        return "displayGlasses";
    }
}
