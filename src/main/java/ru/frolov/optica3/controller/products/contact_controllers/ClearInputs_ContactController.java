package ru.frolov.optica3.controller.products.contact_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;


@Controller
public class ClearInputs_ContactController
        extends AbstractContactController {


    public ClearInputs_ContactController(ContactContainerService containerService, ContactUnitService unitService) {
        super(containerService, unitService);
    }

    @PostMapping("clearInputs")
    public String clearInputs(
            Model model) {
        /*
         * Задача:
         *   В html должны очистится поля поиска. Остальное должно остаться без изменений.
         * */

        // Подготовка данных
        //-------------------
        // Кэшируем пустую dto. Из него пустые значения "очистят" поля html
        containerService.getCache().setDto(new ContactDto());

        // page берем из кэша и отправляем без изменений
        Page<ContactContainer> samePage = containerService.getCache().getPage();


        // Отправляем данные в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                samePage,
                null,
                null,
                null,
                null
        );

        return "displayContacts";
    }

}
