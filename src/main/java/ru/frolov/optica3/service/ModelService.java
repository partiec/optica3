package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.frolov.optica3.cache.*;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.enums.enums_for_frames.FrameInstallType;
import ru.frolov.optica3.enums.enums_for_frames.FrameMaterial;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.payload.FramePayload;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final PaginationService paginationService;
    private final FrameContainerService containerService;
    private final FrameService frameService;
    private final InfoService infoService;

    public void transferModel(Model model,
                              Page<FrameContainer> actualPage,
                              Integer pageNumberOnlyForFlip,
                              Long xId,
                              String whichFieldOnInputOnlyForSearch) {

        // page
        Page<FrameContainer> page = null;

        // если актуальная page не пришла, то создаем, используя pageNumberOnlyForFlip
        if (actualPage == null) {

            // Даже если pageNumberOnlyForFlip не пришла, pageNumber будет взят из кэша и останется прежним
            int pageNumber = Objects.requireNonNullElseGet(
                    pageNumberOnlyForFlip,
                    () -> PageCache.getPage().getNumber());

            page = this.paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);
        } else { // если пришла актуальная page, отправляем ее
            page = actualPage;
        }
        model.addAttribute("page", page);

        // filters вытаскиваем из кэша
        FiltersPayload filters = FiltersPayloadCache.getFiltersPayload();
        model.addAttribute("filters", filters);

        // framePayload вытаскиваем из кэша
        FramePayload framePayload = FramePayloadCache.getFramePayload();
        model.addAttribute("framePayload", framePayload);

        // всего позиций в бд
        List<FrameContainer> dbAllContainers = this.containerService.all();
        model.addAttribute("dbFramePositions", dbAllContainers.size());

        // найдено позиций (берем из page)
        model.addAttribute("foundFramePositions", page.getTotalElements());

        // всего оправ в бд
        long bdFrameUnits = this.frameService.dbFramesSize();
        model.addAttribute("dbFrameUnits", bdFrameUnits);

        // найдено оправ
        if (SpecStatusCache.isApplied()) { // если bySpec
            model.addAttribute("foundFrameUnits", infoService.foundBySpecFrameUnits(filters));
        } else { // если noSpec
            model.addAttribute("foundFrameUnits", bdFrameUnits);
        }

        if (xId != null) {
            model.addAttribute("xId", xId);
        }
        if (whichFieldOnInputOnlyForSearch != null) {
            model.addAttribute("whichFieldOnInput", whichFieldOnInputOnlyForSearch);
        }
        if (!SpecStatusCache.isApplied()) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }
        if (EditModeCache.isEditMode()) {
            model.addAttribute("editMode", "editMode");
        }
        //////////////////////////
        model.addAttribute("installTypes", FrameInstallType.values());
        model.addAttribute("materials", FrameMaterial.values());
        //////////////////////////
    }
}
