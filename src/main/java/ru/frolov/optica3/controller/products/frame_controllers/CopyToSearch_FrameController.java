package ru.frolov.optica3.controller.products.frame_controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;

@Controller
public class CopyToSearch_FrameController
        extends AbstractFrameController {


    public CopyToSearch_FrameController(FrameContainerService containerService, FrameUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("copyToSearch")
    public String copyToSearch(@RequestParam(name = "xContainerId") Long xContainerId,
                               Model model) {

        /*
         * Задача:
         *   Скопировать контейнер в блок поиска чтобы не вбивать вручную.
         * */


        // find xContainer
        FrameContainer xContainer = this.containerService.findById(xContainerId).get();

        // создаем и кэшируем dto на основе xContainer
        FrameDto dto = new FrameDto();
        dto.setProductCategory(xContainer.getProductCategory());
        dto.setFirm(xContainer.firm);
        dto.setModel(xContainer.model);
        dto.setDetails(xContainer.details);
        dto.setPurchase(xContainer.getPurchase());
        dto.setSale(xContainer.getSale());
        //////////////////////////////////
        dto.setFrameUseType(xContainer.getFrameUseType());
        dto.setFrameInstallType(xContainer.getFrameInstallType());
        dto.setFrameMaterial(xContainer.getFrameMaterial());
        //////////////////////////////////
        containerService.getCache().setDto(dto);

        // page без изменений
        Page<FrameContainer> samePage =
                containerService.getCache().getPage();


        // Отправляем данные в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                samePage,
                null,
                null,
                null,
                "copyToSearch");

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                samePage,
                null,
                null,
                xContainer,
                null);

        return "displayFrames";
    }
}
