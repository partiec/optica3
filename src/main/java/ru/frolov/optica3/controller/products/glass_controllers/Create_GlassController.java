package ru.frolov.optica3.controller.products.glass_controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.entity.products.glass.GlassUnit;
import ru.frolov.optica3.factory.container.ContainerFactory;
import ru.frolov.optica3.factory.unit.UnitFactory;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;

import java.util.List;

@Controller
public class Create_GlassController
        extends AbstractGlassController {


    public Create_GlassController(GlassContainerService containerService, GlassUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("create")
    public String create(@Valid GlassDto filters,
                         Model model) {


        GlassContainer xContainer = null;
        GlassUnit xUnit = null;
        Long xId = null;

        // существуют ли уже такой контейнер?
        List<GlassContainer> dubls = containerService.dublsByDto(filters);

        // если такого еще нет
        if (dubls.isEmpty()) {

            // создать новый контейнер фабрично
            xContainer = (GlassContainer) ContainerFactory.createContainerInstance(filters);

            containerService.save(xContainer);

        } else {


            // если такой уже есть
            xContainer = dubls.get(0);
            this.containerService.killDubls(xContainer, dubls.subList(1, dubls.size()));
            xId = xContainer.id;
            // оповестить пользователя, что такие контейнеры уже есть и предложить +1
            model.addAttribute("sameContainerAlreadyExists", "sameContainerAlreadyExists");
        }

        // создать new unit с полями как у контейнера
        GlassUnit newUnit = (GlassUnit) UnitFactory.createUnitInstance(filters);


        unitService.save(newUnit);


        // связать newFrame и xContainer
        newUnit.setContainer(xContainer);
        List<GlassUnit> xContainerUnits = xContainer.getUnitList();
        xContainerUnits.add(newUnit);



        // Подготавливаем данные для модели
        // -------------------------------->
        // page должна быть no spec
        containerService.getCache().setSpec(null);
        // page создаем с помощью локального метода getXPageNoSpec()!, поскольку она должна содержать xContainer
        Page<GlassContainer> actualPage = containerService.getXPage(xContainer);
        // xOrderId нужен будет в html для галочки (которая помечает созданный контейнер)
        xId = xContainer.getId();
        // filters уже не нужны, кэшируем костыль
        containerService.getCache().setDto(new GlassDto());
        //----------------------------/

        // В модель
        // -------->
        containerService.transferToModel(
                model,
                actualPage,
                null,
                xId,
                null,
                null);

        // Контрольное кеширование
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null
        );

        return "displayGlasses";

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleErrors(MethodArgumentNotValidException notValidException,
                               Model model,
                               BindingResult br) {

        // в модель:
        // errors для отображения messages
        model.addAttribute("errors", br.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        // page должна остаться как была
        model.addAttribute("page", containerService.getCache().getPage());
        // filters должны остаться те же
        model.addAttribute("filters", containerService.getCache().getDto());

        // cache не требуется

        return "displayGlasses";
    }
}
