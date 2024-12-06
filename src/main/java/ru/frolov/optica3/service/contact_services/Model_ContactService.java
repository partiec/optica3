package ru.frolov.optica3.service.contact_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.frolov.optica3.cache.contact_cach.*;
import ru.frolov.optica3.defaults.Dioptre;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.payload.contact_payloads.ContactPayload;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Model_ContactService {

    private final Pagination_ContactService paginationService;
    private final ContactContainerService containerService;
    private final ContactService contactService;


    public void transferModel(Model model,
                              Page<ContactContainer> actualPage,
                              Integer pageNumberOnlyForFlip,
                              Long xId,
                              String whichFieldOnInputOnlyForSearch,
                              String copyToSearch) {

        // page
        Page<ContactContainer> page = null;

        // если actualPage не пришла, то создаем, используя pageNumberOnlyForFlip
        if (actualPage == null) {

            // Даже если pageNumberOnlyForFlip не пришла, pageNumber будет взят из кэша и останется прежним
            int pageNumber = Objects.requireNonNullElseGet(
                    pageNumberOnlyForFlip,
                    () -> Page_ContactCache.getPage().getNumber());

            page = this.paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        } else { // если пришла актуальная page, отправляем ее
            page = actualPage;
        }
        model.addAttribute("page", page);

        // filters вытаскиваем из кэша
        Filters_ContactPayload filters = FiltersPayload_ContactCache.getFiltersPayload();
        model.addAttribute("filters", filters);

        // glassPayload вытаскиваем из кэша
        ContactPayload contactPayload = ContactPayloadCache.getContactPayload();
        model.addAttribute("contactPayload", contactPayload);

        // всего позиций в бд
        List<ContactContainer> dbAllContainers = this.containerService.all();
        model.addAttribute("dbPositions", dbAllContainers.size());

        // найдено позиций (берем из page)
        model.addAttribute("foundPositions", page.getTotalElements());

        // всего оправ в бд
        long bdUnits = this.contactService.dbUnitsSize();
        model.addAttribute("dbUnits", bdUnits);

        // found units
        if (SpecStatus_ContactCache.isApplied()) { // если bySpec

                model.addAttribute("foundUnits", this.contactService.foundUnitsSize(this.paginationService.getFoundContainers()));

        } else { // если noSpec
            model.addAttribute("foundUnits", bdUnits);
        }

        if (xId != null) {
            model.addAttribute("xId", xId);
        }
        if (whichFieldOnInputOnlyForSearch != null) {
            model.addAttribute("whichFieldOnInput", whichFieldOnInputOnlyForSearch);
        }
        if (!SpecStatus_ContactCache.isApplied()) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }
        if (EditMode_ContactCache.isEditMode()) {
            model.addAttribute("editMode", EditMode_ContactCache.isEditMode());
        }
        //////////////////////////
        model.addAttribute("designs", ContactDesign.values());
        model.addAttribute("periods", ContactPeriod.values());
        model.addAttribute("oxygens", ContactOxygen.values());
        model.addAttribute("waters", ContactWater.values());
        //////////////////////////
        model.addAttribute("diameters", ContactDiameter.values());
        model.addAttribute("radiuses", ContactRadius.values());
        model.addAttribute("dioptres", Dioptre.getDioptresStringList());
        if (copyToSearch != null) {
            model.addAttribute("copyToSearch", "copyToSearch");
        }
    }
}
