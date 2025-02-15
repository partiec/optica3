package ru.frolov.optica3.controller.products.glass_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.entity.products.glass.GlassUnit;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;

import java.util.List;

@Controller
public class Minus1_GlassController
        extends AbstractGlassController {


    public Minus1_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("minus1")
    public String minus1Frame(@RequestParam(name = "xContainerId") Long xContainerId,
                              Model model) {
        /*
         * Задача:
         *   Удалить 1 юнит. Отобразить страницу с учетом удаленного.
         * */

        System.out.println("===================================================");
        System.out.println("\t" + getClass().getSimpleName() + ".minus1Frame()...");

        // Находим нужный контейнер по xOrderId
        GlassContainer xContainer = this.containerService.findById(xContainerId).get();

        // units в xContainer
        List<GlassUnit> xList = xContainer.getUnitList();

        // Если в контейнере осталась последняя оправа или ни одной, то удалить весь контейнер из БД
        if (xList.size() <= 1) {
            this.containerService.delete(xContainer);
        } else {
            // Иначе удалить из контейнера только firstElement
            GlassUnit firstElement = xContainer.getUnitList().get(0);
            xContainer.getUnitList().remove(firstElement);
            unitService.delete(firstElement);
        }

        // Подготовка данных для модели
        // ---------------------------->
        // создать новую page с тем же pageNumber и specification
        int pageNumber = containerService.getCache().getPage().getNumber();
        Specification<GlassContainer> specification = containerService.getCache().getSpec();
        Page<GlassContainer> actualPage;
        if (specification != null) {
            actualPage = containerService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = containerService.getPageNOSpec(pageNumber);
        }
        // Отправка данных в модель
        // ------------------------>
        containerService.transferToModel(
                model,
                actualPage,
                null,
                xContainerId,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
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
