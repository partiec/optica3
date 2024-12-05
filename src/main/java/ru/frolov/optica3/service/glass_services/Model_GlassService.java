package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.frolov.optica3.cache.glass_caches.*;
import ru.frolov.optica3.defaults.Dioptre;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.payload.glass_payloads.GlassPayload;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Model_GlassService {

    private final Pagination_GlassService paginationService;
    private final GlassContainerService containerService;
    private final GlassService glassService;


    public void transferModel(Model model,
                              Page<GlassContainer> actualPage,
                              Integer pageNumberOnlyForFlip,
                              Long xId,
                              String whichFieldOnInputOnlyForSearch,
                              String copyToSearch) {

        // page
        Page<GlassContainer> page = null;

        // если actualPage не пришла, то создаем, используя pageNumberOnlyForFlip
        if (actualPage == null) {

            // Даже если pageNumberOnlyForFlip не пришла, pageNumber будет взят из кэша и останется прежним
            int pageNumber = Objects.requireNonNullElseGet(
                    pageNumberOnlyForFlip,
                    () -> Page_GlassCache.getPage().getNumber());

            page = this.paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        } else { // если пришла актуальная page, отправляем ее
            page = actualPage;
        }
        model.addAttribute("page", page);

        // filters вытаскиваем из кэша
        Filters_GlassPayload filters = FiltersPayload_GlassCache.getFiltersPayload();
        model.addAttribute("filters", filters);

        // glassPayload вытаскиваем из кэша
        GlassPayload glassPayload = GlassPayloadCache.getGlassPayload();
        model.addAttribute("glassPayload", glassPayload);

        // всего позиций в бд
        List<GlassContainer> dbAllContainers = this.containerService.all();
        model.addAttribute("dbPositions", dbAllContainers.size());

        // найдено позиций (берем из page)
        model.addAttribute("foundPositions", page.getTotalElements());

        // всего оправ в бд
        long bdUnits = this.glassService.dbUnitsSize();
        model.addAttribute("dbUnits", bdUnits);

        // found units
        if (SpecStatus_GlassCache.isApplied()) { // если bySpec

                model.addAttribute("foundUnits", this.glassService.foundUnitsSize(this.paginationService.getFoundContainers()));

        } else { // если noSpec
            model.addAttribute("foundUnits", bdUnits);
        }

        if (xId != null) {
            model.addAttribute("xId", xId);
        }
        if (whichFieldOnInputOnlyForSearch != null) {
            model.addAttribute("whichFieldOnInput", whichFieldOnInputOnlyForSearch);
        }
        if (!SpecStatus_GlassCache.isApplied()) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }
        if (EditMode_GlassCache.isEditMode()) {
            model.addAttribute("editMode", "editMode");
        }
        //////////////////////////
        model.addAttribute("materials", GlassMaterial.values());
        model.addAttribute("designs", GlassDesign.values());
        model.addAttribute("coats", GlassCoat.values());
        //////////////////////////
        model.addAttribute("dioptres", Dioptre.getDioptresStringList());
        if (copyToSearch != null) {
            model.addAttribute("copyToSearch", "copyToSearch");
        }
    }
}
