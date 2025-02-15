package ru.frolov.optica3.controller.products.accessory_controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;

@Controller
public class Flip_AccessoryController
        extends AbstractAccessoryController {


    public Flip_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService) {
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
        Page<AccessoryContainer> actualPage;
        Specification<AccessoryContainer> specification = containerService.getAccessoryCache().getSpec();
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
