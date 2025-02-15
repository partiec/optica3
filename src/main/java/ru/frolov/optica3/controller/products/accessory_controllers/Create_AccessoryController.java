package ru.frolov.optica3.controller.products.accessory_controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.factory.container.ContainerFactory;
import ru.frolov.optica3.factory.unit.UnitFactory;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

import java.util.List;

@Controller
public class Create_AccessoryController
        extends AbstractAccessoryController {
    public Create_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
        super(containerService, unitService);
    }

    //-------------------------------------------------------------------------------------------------


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("create")
    public String create(@Valid AccessoryDto filters,
                         Model model) {


        AccessoryContainer newContainer = null;
        AccessoryUnit newUnit = null;


        // существуют ли уже такие контейнеры?
        List<AccessoryContainer> dubls = containerService.dublsByDto(filters);
        // если нет
        if (dubls.isEmpty()) {

            // создать новый контейнер
            newContainer = (AccessoryContainer) ContainerFactory.createContainerInstance(filters);
            containerService.save(newContainer);

        } else {

            // если "дубль" (или "дубли") есть, то оставить из них только firstElement
            newContainer = dubls.get(0);
            // остальные "дубли" удалить, предварительно вынув из них units
            this.containerService.killDubls(newContainer, dubls.subList(1, dubls.size()));

            // оповестить пользователя, что такие контейнеры уже есть и предложить +1
            model.addAttribute("sameContainerAlreadyExists", "sameContainerAlreadyExists");
        }

        // создать новую unit
        newUnit = (AccessoryUnit) UnitFactory.createUnitInstance(filters);
        unitService.save(newUnit);

        // связать newFrame и newContainer
        newUnit.setContainer(newContainer);
        List<AccessoryUnit> newContainerUnits = newContainer.getUnitList();
        newContainerUnits.add(newUnit);


        // Подготавливаем данные для модели
        // -------------------------------->
        // page должна быть no spec
        containerService.getAccessoryCache().setSpec(null);
        // page создаем с помощью локального метода getXPageNoSpec()!, поскольку она должна содержать xContainer
        Page<AccessoryContainer> actualPage = containerService.getXPage(newContainer);
        // filters уже не нужны, кэшируем костыль
        containerService.getAccessoryCache().setDto(new AccessoryDto());
        //----------------------------/

        // В модель
        // -------->
        containerService.transferToModel(
                model,
                actualPage,
                null,
                newContainer.id,
                null,
                null);

        // Контрольное кеширование
        containerService.getAccessoryCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null,
                null
        );

        return "displayAccessories";

    }


}
