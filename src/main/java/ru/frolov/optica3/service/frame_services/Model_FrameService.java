package ru.frolov.optica3.service.frame_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.frolov.optica3.cache.frame_cach.*;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.frame_payloads.FramePayload;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Model_FrameService {

    private final Pagination_FrameService paginationFrameService;
    private final FrameContainerService containerService;
    private final FrameService frameService;
    private final Info_FrameService infoFrameService;

    public void transferModel(Model model,
                              Page<FrameContainer> actualPage,
                              Integer pageNumberOnlyForFlip,
                              Long xId,
                              String whichFieldOnInputOnlyForSearch,
                              String copyToSearch) {

        // page
        Page<FrameContainer> page = null;

        // если actualPage не пришла, то создаем, используя pageNumberOnlyForFlip
        if (actualPage == null) {

            // Даже если pageNumberOnlyForFlip не пришла, pageNumber будет взят из кэша и останется прежним
            int pageNumber = Objects.requireNonNullElseGet(
                    pageNumberOnlyForFlip,
                    () -> Page_FrameCache.getPage().getNumber());

            page = this.paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);
        } else { // если пришла актуальная page, отправляем ее
            page = actualPage;
        }
        model.addAttribute("page", page);

        // filters вытаскиваем из кэша
        Filters_FramePayload filters = FiltersPayload_FrameCache.getFiltersPayload();
        model.addAttribute("filters", filters);

        // framePayload вытаскиваем из кэша
        FramePayload framePayload = FramePayloadCache.getFramePayload();
        model.addAttribute("framePayload", framePayload);

        // всего позиций в бд
        List<FrameContainer> dbAllContainers = this.containerService.all();
        model.addAttribute("dbPositions", dbAllContainers.size());

        // найдено позиций (берем из page)
        model.addAttribute("foundPositions", page.getTotalElements());

        // всего оправ в бд
        long bdFrameUnits = this.frameService.dbFramesSize();
        model.addAttribute("dbUnits", bdFrameUnits);

        // найдено оправ
        if (SpecStatus_FrameCache.isApplied()) { // если bySpec
            model.addAttribute("foundUnits", infoFrameService.foundBySpecFrameUnits(filters));
        } else { // если noSpec
            model.addAttribute("foundUnits", bdFrameUnits);
        }

        if (xId != null) {
            model.addAttribute("xId", xId);
        }
        if (whichFieldOnInputOnlyForSearch != null) {
            model.addAttribute("whichFieldOnInput", whichFieldOnInputOnlyForSearch);
        }
        if (!SpecStatus_FrameCache.isApplied()) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }
        if (EditMode_FrameCache.isEditMode()) {
            model.addAttribute("editMode", "editMode");
        }
        //////////////////////////
        model.addAttribute("useTypes", FrameUseType.values());
        model.addAttribute("installTypes", FrameInstallType.values());
        model.addAttribute("materials", FrameMaterial.values());
        //////////////////////////
        if (copyToSearch != null){
            model.addAttribute("copyToSearch", "copyToSearch");
        }
    }
}
