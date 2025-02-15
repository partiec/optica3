package ru.frolov.optica3.controller.products.accessory_controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

@Controller
public class CopyToSearch_AccessoryController
        extends AbstractAccessoryController {


    public CopyToSearch_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
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
        AccessoryContainer xContainer = containerService.findById(xContainerId).get();

        // создаем и кэшируем dto на основе xContainer
        AccessoryDto dto = new AccessoryDto();
        dto.setProductCategory(xContainer.getProductCategory());
        dto.setFirm(xContainer.firm);
        dto.setModel(xContainer.model);
        dto.setDetails(xContainer.details);
        dto.setPurchase(xContainer.getPurchase());
        dto.setSale(xContainer.getSale());
        //////////////////////////////////
        dto.setAccessoryCategory(xContainer.getAccessoryCategory());
        //////////////////////////////////
        containerService.getAccessoryCache().setDto(dto);

        // page без изменений
        Page<AccessoryContainer> samePage =
                containerService.getAccessoryCache().getPage();


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
        containerService.getAccessoryCache().cacheAttributesNotNull(
                samePage,
                null,
                null,
                xContainer,
                null,
                null);

        return "displayAccessories";
    }
}
