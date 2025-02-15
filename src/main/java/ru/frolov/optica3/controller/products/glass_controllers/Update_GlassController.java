package ru.frolov.optica3.controller.products.glass_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;

import java.util.List;

@Controller
public class Update_GlassController
        extends AbstractGlassController {


    public Update_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("update")
    public String updateFrame(@RequestParam(name = "xContainerId") Long xContainerId,
                              GlassDto dto,
                              Model model) {

        /*
         * Метод обновляет контейнер по dto.
         * Задача:
         *   Обновить и отобразить страницу.
         * */

        // находим нужный контейнер по xOrderId
        GlassContainer xContainer = this.containerService.findById(xContainerId).get();


        // если в dto какое-то из полей не null, значит
        // измененное значение ИМЕННО ЭТОГО ПОЛЯ пришло из формы.
        // Устанавливаем это значение в контейнер, а старое значение затирается.
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
        ///////////////////////
        // glass
        if (dto.getGlassMaterial() != null) {
            xContainer.setGlassMaterial(dto.getGlassMaterial());
        }
        if (dto.getGlassDesign() != null) {
            xContainer.setGlassDesign(dto.getGlassDesign());
        }
        if (dto.getGlassCoat() != null) {
            xContainer.setGlassCoat(dto.getGlassCoat());
        }
        if (dto.getDioptre() != null) {
            xContainer.setDioptre(dto.getDioptre());
        }
        ////////////////////////
        // пытаемся найти контейнеры-дубли
        List<GlassContainer> dubls = this.containerService.dublsByX(xContainer);

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
//        this.containerService.save(xContainer);

        // Подготовка данных для модели
        // ---------------------------->
        // новая page должна остаться с тем же номером и spec
        int pageNumber = containerService.getCache().getPage().getNumber();
        Specification<GlassContainer> specification = containerService.getCache().getSpec();
        Page<GlassContainer> actualPage;
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


        return "displayGlasses";
    }
}
