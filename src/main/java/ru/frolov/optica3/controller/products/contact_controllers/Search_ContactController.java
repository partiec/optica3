package ru.frolov.optica3.controller.products.contact_controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;


@Controller
public class Search_ContactController
        extends AbstractContactController {


    public Search_ContactController(ContactContainerService containerService, ContactUnitService unitService) {
        super(containerService, unitService);
    }

    @PostMapping("search")
    public String search(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            ContactDto dto,
            Model model) {

        /*
         * Метод ищет контейнеры по введенным данным.
         * Задача:
         *   Отобразить страницу, содержащую контейнеры, "похожие" на введенное
         * */

        // Подготовка данных для модели
        // --------------------------->
        // кэшируем принятое dto (заодно добавляем ProductCategory.FRAME)
        dto.setProductCategory(ProductCategory.CONTACT);
        containerService.getCache().setDto(dto);

        // page однозначно должна быть bySpec
        Specification<ContactContainer>specification = containerService.getSpec().allContains(dto);
        containerService.getCache().setSpec(specification);

        // создаем page №0 по spec (spec уже в кэше)
        Page<ContactContainer> actualPage =
                containerService.getPageBYSpec(0, specification);


        // Отправка данных в модель
        // ------------------------->
        containerService.transferToModel(
                model,
                actualPage,
                null,
                null,
                whichFieldOnInput,
                null);

        // Контрольное кэширование
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                dto,
                null,
                null,
                null
        );


        return "displayContacts";
    }
}
