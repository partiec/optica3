package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.spec.frame_spec.SpecForFrames;
import ru.frolov.optica3.spec.glass_spec.SpecForGlasses;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Info_GlassService {

    private final Pagination_GlassService paginationService;
    private final GlassContainerService containerService;
    private final GlassService glassService;

    public long foundBySpecFrameUnits(Filters_GlassPayload filters) {

        Specification<GlassContainer> spec = SpecForGlasses.byAllFieldsContains(filters);
        List<GlassContainer> bySpecContainers = this.containerService.allBySpec(spec);
        return  this.glassService.foundUnits(bySpecContainers);
    }
}
