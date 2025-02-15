package ru.frolov.optica3.controller.products.contact_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;

@Controller
public class FlipPages_ContactController
        extends AbstractContactController {


    public FlipPages_ContactController(ContactContainerService containerService, ContactUnitService unitService) {
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
        Page<ContactContainer> actualPage;
        Specification<ContactContainer> specification = containerService.getCache().getSpec();
        if (specification !=null){
            actualPage = containerService.getPageBYSpec(pageNumber, specification);
        } else{
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
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null
        );

        return "displayContacts";
    }
}
