package ru.frolov.optica3.controller.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.entity.contact.Contact;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.service.contact_services.Cache_ContactService;
import ru.frolov.optica3.service.contact_services.ContactContainerService;
import ru.frolov.optica3.service.contact_services.Model_ContactService;
import ru.frolov.optica3.service.contact_services.Pagination_ContactService;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class Minus1_ContactController {

    private final ContactContainerService containerService;
    private final Pagination_ContactService paginationService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/minus1Contact")
    public String minus1Contact(@RequestParam(name = "xId") Long xId,
                         Model model) {


        // Находим нужный контейнер по полученному id
        ContactContainer xContainer = this.containerService.findById(xId).get();

        // единицы товара из xContainer
        List<Contact> xList = xContainer.getContactList();

        // Если в контейнере осталась последняя оправа или ни одной, то удалить весь контейнер из БД
        if (xList.size() <= 1) {
            this.containerService.delete(xContainer);
        } else {
            // Иначе удалить из контейнера только firstElement
            Contact firstElement = xContainer.getContactList().get(0);
            xContainer.getContactList().remove(firstElement);
            // сохранить контейнер в БД
            this.containerService.save(xContainer);
        }

        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом удаления
        int pageNumber = Page_ContactCache.getPage().getNumber();
        Page<ContactContainer> actualPage = this.paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        // Отправка данных в модель
        // ------------------------>
        modelService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayContacts";
    }
}
