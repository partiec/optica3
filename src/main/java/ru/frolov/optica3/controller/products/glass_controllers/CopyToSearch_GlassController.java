package ru.frolov.optica3.controller.products.glass_controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;

@Controller
public class CopyToSearch_GlassController
        extends AbstractGlassController {


    public CopyToSearch_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
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
        GlassContainer xContainer = this.containerService.findById(xContainerId).get();

        // создаем и кэшируем dto на основе xContainer
        GlassDto dto = new GlassDto();
        dto.setProductCategory(xContainer.getProductCategory());
        dto.setFirm(xContainer.firm);
        dto.setModel(xContainer.model);
        dto.setDetails(xContainer.details);
        dto.setPurchase(xContainer.getPurchase());
        dto.setSale(xContainer.getSale());
        ////////////////////////////////////
        dto.setGlassMaterial(xContainer.getGlassMaterial());
        dto.setGlassDesign(xContainer.getGlassDesign());
        dto.setGlassCoat(xContainer.getGlassCoat());
        dto.setDioptre(xContainer.getDioptre());
        ////////////////////////////////////
        containerService.getCache().setDto(dto);

        // page без изменений
        Page<GlassContainer> samePage =
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

        return "displayGlasses";
    }
}
