package ru.frolov.optica3.controller.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.service.contact_services.*;

@Controller
@RequiredArgsConstructor
public class DeletePosition_ContactController {

    private final ContactContainerService containerService;
    private final ContactService contactService;
    private final Pagination_ContactService paginationService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/deleteContactPosition")
    public String deleteContactPosition(@RequestParam(name = "xId") Long xId,
                                 Model model) {

        // удаляем контейнер по xId
        this.containerService.deleteById(xId);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться с тем же номером и spec, но обновлена с учетом удаленного
        // pageNumber берем из кэша
        int pageNumber = Page_ContactCache.getPage().getNumber();

        // метод создаст page в зависимости от specStatus
        Page<ContactContainer> actualPage =
                paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        // Отправляем данные в модель
        // --------------------------->
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
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна


        return "displayContacts";
    }
}
