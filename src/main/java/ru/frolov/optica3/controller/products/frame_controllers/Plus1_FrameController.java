package ru.frolov.optica3.controller.products.frame_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;

import java.util.List;


@Controller
public class Plus1_FrameController
        extends AbstractFrameController {


    public Plus1_FrameController(FrameContainerService containerService, FrameUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("plus1")
    public String plus1(@RequestParam(name = "xContainerId") Long xContainerId,
                        Model model) {
        /*
         * Задача:
         *   Создать сущность и отобразить страницу с ужетом изменений.
         * */


        // Находим нужный контейнер по xOrderId
        FrameContainer xContainer = this.containerService.findById(xContainerId).orElse(null);

        // new unit
        FrameUnit newUnit = null;

        // если контейнер найден
        if (xContainer != null) {
            // создаем новую unit с такими же значениями полей, как у контейнера
            newUnit = new FrameUnit();

            newUnit.setProductCategory(xContainer.getProductCategory());
            newUnit.setFirm(xContainer.getFirm());
            newUnit.setModel(xContainer.getModel());
            newUnit.setDetails(xContainer.getDetails());
            newUnit.setPurchase(xContainer.getPurchase());
            newUnit.setSale(xContainer.getSale());
            ///////////////////////////////////////////////
            newUnit.setFrameUseType(xContainer.getFrameUseType());
            newUnit.setFrameInstallType(xContainer.getFrameInstallType());
            newUnit.setFrameMaterial(xContainer.getFrameMaterial());
            ///////////////////////////////////////////////

            // сохраняем new unit (для обретения id)
            unitService.save(newUnit);

            // Соединяем xContainer и newFrameUnit
            newUnit.setContainer(xContainer);
            List<FrameUnit> xContainerUnits =  xContainer.getUnitList();
            xContainerUnits.add(newUnit);


        } // если контейнер не найден - ничего не происходит


        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом добавления
        int pageNumber = containerService.getCache().getPage().getNumber();
        Specification<FrameContainer>specification = containerService.getCache().getSpec();
        Page<FrameContainer> actualPage;
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
                null);

        return "displayFrames";
    }
}
