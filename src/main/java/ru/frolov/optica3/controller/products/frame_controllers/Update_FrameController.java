package ru.frolov.optica3.controller.products.frame_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;

import java.util.List;

@Controller
public class Update_FrameController
        extends AbstractFrameController {


    public Update_FrameController(FrameContainerService containerService, FrameUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("update")
    public String updateFrame(@RequestParam(name = "xContainerId") Long xContainerId,
                              FrameDto dto,
                              Model model) {

        /*
         * Метод обновляет контейнер по dto.
         * Задача:
         *   Обновить и отобразить страницу.
         * */

        // находим нужный контейнер по xOrderId
        FrameContainer xContainer = this.containerService.findById(xContainerId).get();


        // значения из dto в xContainer
        if (dto.getProductCategory() != null) {
            xContainer.setProductCategory(dto.getProductCategory());
        }
        if (dto.getFirm() != null) {
            xContainer.setFirm(dto.getFirm());
        }
        if (dto.getModel() != null) {
            xContainer.setModel(dto.getModel());
        }
        if (dto.getDetails() != null) {
            xContainer.setDetails(dto.getDetails());
        }
        if (dto.getPurchase() != null) {
            xContainer.setPurchase(dto.getPurchase());
        }
        if (dto.getSale() != null) {
            xContainer.setSale(dto.getSale());
        }
        /////////////////////////////////////////////////////////
        // frame
        if (dto.getFrameUseType() != null) {
            xContainer.setFrameUseType(dto.getFrameUseType());
        }
        if (dto.getFrameInstallType() != null) {
            xContainer.setFrameInstallType(dto.getFrameInstallType());
        }
        if (dto.getFrameMaterial() != null) {
            xContainer.setFrameMaterial(dto.getFrameMaterial());
        }
        /////////////////////////////////////////////////////////

        // пытаемся найти контейнеры-дубли
        List<FrameContainer> dubls = this.containerService.dublsByX(xContainer);

        // если есть дубли
        if (!dubls.isEmpty()) {

            xContainer = dubls.get(0);
            // применить метод, который
            //  - извлекает frames из контейнеров-дублей (дубли удаляет),
            //  - переименовывает их поля, чтоб были как у xContainer
            //  - вставляет обновленные frames в xContainer (xContainer становится единственным)
            this.containerService.killDubls(xContainer, dubls.subList(1, dubls.size()));
        }

//        // сохраняем контейнер
//        this.containerService.save(xContainer);   // ???

        // Подготовка данных для модели
        // ---------------------------->
        // новая page должна остаться с тем же номером и spec
        int pageNumber = containerService.getCache().getPage().getNumber();
        Specification<FrameContainer> specification = containerService.getCache().getSpec();
        Page<FrameContainer> actualPage;
        if (specification != null) {
            actualPage = containerService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = containerService.getPageNOSpec(pageNumber);
        }

        // Отправляем данные в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                actualPage,
                null,
                xContainerId,
                null,
                null);

        // Контрольное кэширование
        // ------------------------->
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
