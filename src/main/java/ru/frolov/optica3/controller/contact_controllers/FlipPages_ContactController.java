package ru.frolov.optica3.controller.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.service.contact_services.*;

@Controller
@RequiredArgsConstructor
public class FlipPages_ContactController {

    private final Pagination_ContactService paginationService;
    private final Cache_ContactService cacheService;
    private final Model_ContactService modelService;
    private final ContactContainerService containerService;
    private final ContactService contactService;

    @GetMapping("api/flipContactPage/{pageNumber:\\d+}")
    public String flipContactPage(
            @PathVariable(name = "pageNumber") Integer pageNumber,
            Model model) {


        // Подготовка данных для модели
        // ------------------------------>
        // page должна быть с другим pageNumber, но specStatus тот же
        // метод использует spеcStatus из кэша и создаст page с номером из @PathVariable
        Page<ContactContainer>actualPage = paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        // Отправка данных в модель
        // --------------------------->
        modelService.transferModel(
                model,
                actualPage,
                pageNumber,
                null,
                null,
                null);

        // Контрольное кэширование
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayContacts";
    }
}
