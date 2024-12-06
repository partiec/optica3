package ru.frolov.optica3.controller.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.payload.contact_payloads.ContactPayload;
import ru.frolov.optica3.service.contact_services.Cache_ContactService;
import ru.frolov.optica3.service.contact_services.ContactContainerService;
import ru.frolov.optica3.service.contact_services.Model_ContactService;
import ru.frolov.optica3.service.contact_services.Pagination_ContactService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Update_ContactController {


    private final ContactContainerService containerService;
    private final Pagination_ContactService paginationService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;


    //------------------------------------------------------------

    @PostMapping("api/updateContact")
    public String updateContact(@RequestParam(name = "xId") Long xId,
                              ContactPayload contactPayload,
                              Model model) {

        // находим нужный контейнер по xId
        ContactContainer xContainer = this.containerService.findById(xId).get();


        // если во framePayload какое-то из полей не null, значит
        // измененное значение именно этого поля пришло из формы.
        // Устанавливаем это значение в контейнер, а старое значение затирается.
        if (contactPayload.firm() != null) {
            xContainer.setFirm(contactPayload.firm());
        }
        /////////////////////////////////////////////////////////
        if (contactPayload.design() != null) {
            xContainer.setDesign(contactPayload.design());
        }
        if (contactPayload.period() != null) {
            xContainer.setPeriod(contactPayload.period());
        }
        if (contactPayload.oxygen() != null) {
            xContainer.setOxygen(contactPayload.oxygen());
        }
        if (contactPayload.water() != null) {
            xContainer.setWater(contactPayload.water());
        }
        /////////////////////////////////////////////////////////
        if (contactPayload.details() != null) {
            xContainer.setDetails(contactPayload.details());
        }
        if (contactPayload.purchase() != null) {
            xContainer.setPurchase(contactPayload.purchase());
        }
        if (contactPayload.sale() != null) {
            xContainer.setSale(contactPayload.sale());
        }
        /////////////
        if (contactPayload.diameter() != null){
            xContainer.setDiameter(contactPayload.diameter());
        }
        if (contactPayload.radius() != null){
            xContainer.setRadius(contactPayload.radius());
        }
        /////////////
        if (contactPayload.dioptre() != null) {
            xContainer.setDioptre(contactPayload.dioptre());
        }


        // пытаемся найти контейнеры-дубли
        List<ContactContainer> dubls = this.containerService.dubls(xContainer);
        // если есть дубли
        if (!dubls.isEmpty()) {
            // применить метод из containerService, который
            //  - извлекает frames из контейнеров-дублей (дубли удаляет),
            //  - переименовывает их поля, чтоб были как у xContainer
            //  - вставляет обновленные frames в xContainer (xContainer становится единственным)
            this.containerService.killDubls(xContainer, dubls);
        }

        // сохраняем измененный контейнер
        this.containerService.save(xContainer);

        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться с тем же номером и spec, но обновлена (для info html)
        int pageNumber = Page_ContactCache.getPage().getNumber();
        Page<ContactContainer> actualPage = this.paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                actualPage,
                pageNumber,
                xId,
                null,
                null);

        // Контрольное кэширование
        // ------------------------->
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                contactPayload);


        return "displayContacts";
    }


}
