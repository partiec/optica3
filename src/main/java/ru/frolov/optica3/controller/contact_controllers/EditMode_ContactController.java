package ru.frolov.optica3.controller.contact_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.cache.contact_cach.EditMode_ContactCache;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.service.contact_services.Cache_ContactService;
import ru.frolov.optica3.service.contact_services.Model_ContactService;


@Controller
@RequiredArgsConstructor
public class EditMode_ContactController {

    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;

    @GetMapping("api/editContact_on")
    public String editContact_on(Model model) {

        System.out.println("___editContact_on()...");

        // включить режим редактирования
        EditMode_ContactCache.setEditMode(true);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<ContactContainer> samePage = Page_ContactCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayContacts";
    }

    @GetMapping("api/editContact_off")
    public String editGlass_off(Model model) {

        System.out.println("___editContact_off()...");

        EditMode_ContactCache.setEditMode(false);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<ContactContainer> samePage = Page_ContactCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayContacts";
    }
}
