package ru.frolov.optica3.controller.contact_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.contact_cach.FiltersPayload_ContactCache;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.cache.contact_cach.SpecStatus_ContactCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.contact.Contact;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.service.contact_services.Cache_ContactService;
import ru.frolov.optica3.service.contact_services.ContactContainerService;
import ru.frolov.optica3.service.contact_services.ContactService;
import ru.frolov.optica3.service.contact_services.Model_ContactService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Create_ContactController {

    private final ContactService contactService;
    private final ContactContainerService containerService;
    private final Model_ContactService modelService;
    private final Cache_ContactService cacheService;


    //------------------------------------------------------------------------------------------------------------

    @PostMapping("api/createContact")
    public String createContact(@Valid Filters_ContactPayload filters,
                                Model model) {

        ContactContainer xContainer;

        // существуют ли уже такие контейнеры?
        List<ContactContainer> dubls = this.containerService.dubls(filters);
        // если такого еще нет
        if (dubls.isEmpty()) {
            // создать новый контейнер
            xContainer = new ContactContainer();
            xContainer.setFirm(filters.firm());
            ///////////////////////////////////////////
            xContainer.setDesign(
                    filters.design() == null ? ContactDesign.NOT_SELECTED : filters.design());
            xContainer.setPeriod(
                    filters.period()==null ? ContactPeriod.NOT_SELECTED : filters.period());
            xContainer.setOxygen(
                    filters.oxygen() == null ? ContactOxygen.NOT_SELECTED : filters.oxygen());
            xContainer.setWater(
                    filters.water() == null ? ContactWater.NOT_SELECTED : filters.water());
            ///////////////////////////////////////////
            xContainer.setDetails(filters.details());
            xContainer.setPurchase(filters.purchase());
            xContainer.setSale(filters.sale());
            //////////////////////
            xContainer.setDiameter(
                    filters.diameter() == null ? ContactDiameter.NOT_SELECTED : filters.diameter());
            xContainer.setRadius(
                    filters.radius() == null ? ContactRadius.NOT_SELECTED : filters.radius());
            //////////////////////
            xContainer.setDioptre(
                    filters.dioptre() == null || !filters.dioptre().matches("^[+-p_]\\S*") ?
                            "_" : filters.dioptre());
        } else {
            // если такой уже есть, то самый ранний оставляем
            xContainer = dubls.get(0);
            // убиваем дубли
            this.containerService.killDubls(xContainer, dubls.subList(1, dubls.size()));
            // оповестить пользователя, что такие контейнеры уже есть и предложить +1
            model.addAttribute("sameContainerAlreadyExists", "sameContainerAlreadyExists");
        }

        // создать new unit
        Contact newContact = new Contact();
        newContact.setFirm(xContainer.getFirm());
        /////////////////////////////////////////
        newContact.setDesign(xContainer.getDesign());
        newContact.setPeriod(xContainer.getPeriod());
        newContact.setOxygen(xContainer.getOxygen());
        newContact.setWater(xContainer.getWater());
        //////////////////////////////////////////////////////
        newContact.setDetails(xContainer.getDetails());
        newContact.setPurchase(xContainer.getPurchase());
        newContact.setSale(xContainer.getSale());
        ////////////////////////
        newContact.setDiameter(xContainer.getDiameter());
        newContact.setRadius(xContainer.getRadius());
        ////////////////////////
        newContact.setDioptre(xContainer.getDioptre());

        // связать newUnit и xContainer
        newContact.setContactContainer(xContainer);
        xContainer.addToContactList(newContact);
        // сохранить контейнер (при этом newFrame сохранится каскадно)
        this.containerService.save(xContainer);


        // Подготавливаем данные для модели
        // -------------------------------->
        // page должна быть no spec
        SpecStatus_ContactCache.setApplied(false);
        // page создаем с помощью локального метода getXPageNoSpec()!, поскольку она должна содержать xContainer
        Page<ContactContainer> actualPage = getXPageNoSpec(xContainer);
        // xId нужен будет в html для галочки (которая помечает созданный контейнер)
        Long xId = xContainer.getId();
        // filters уже не нужны, кэшируем костыль
        FiltersPayload_ContactCache.setFiltersPayload(
                new Filters_ContactPayload(null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));
        //----------------------------/

        // В модель
        // -------->
        modelService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null,
                null);

        // Контрольное кеширование
        cacheService.cacheAttributes(
                actualPage, // (page) - with xContainer
                null, // (specStatus) - уже был кэширован при подготовке данных для модели
                null, // (filters) - уже был кэширован пустой костыль при подготовке данных для модели
                null   // (framePayload) - здесь не нужна
        );


        return "displayContacts";

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleErrors(MethodArgumentNotValidException notValidException,
                               Model model,
                               BindingResult br) {

        // в модель:
        // errors для отображения messages
        model.addAttribute("errors", br.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        // page должна остаться как была
        model.addAttribute("page", Page_ContactCache.getPage());
        // filters должны остаться те же
        model.addAttribute("filters", FiltersPayload_ContactCache.getFiltersPayload());

        // cache не требуется

        return "displayContacts";
    }


    private Page<ContactContainer> getXPageNoSpec(ContactContainer xContainer) {
        // нужна page с галочкой на xContainer.
        //-----------------------------------------------------------------------------
        Page<ContactContainer> xPage = null;

        if (xContainer != null) {


            // если catchXPage = true, то выходим из цикла
            boolean catchXPage = false;

            // перебираем страницы, пока не найдем нужную
            for (int i = 0; ; i++) {

                // если нужная страница найдена, выходим из цикла
                if (catchXPage) break;

                // На каждой итерации создать:
                //  pageable i
                Pageable pageable = PageRequest.of(
                        i,
                        Defaults.PAGE_SIZE,
                        Sort.by(Sort.Direction.ASC, "firm"));
                // страницу i
                xPage = this.containerService.getPage(pageable);

                // перебирать content каждой страницы пока не найдем страницу, содержащую xContainer
                for (ContactContainer container : xPage.getContent()) {
                    // если нашелся xContainer, то найдена нужная страница
                    if (container.equals(xContainer)) {
                        catchXPage = true;
                        break;
                    }
                }
            }
        }


        return xPage;
    }


}
