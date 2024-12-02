package ru.frolov.optica3.service.frame_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.spec.frame_spec.SpecForFrames;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Info_FrameService {

    private final Pagination_FrameService paginationFrameService;
    private final FrameContainerService containerService;
    private final FrameService frameService;

    public long foundBySpecFrameUnits(Filters_FramePayload filters) {

        Specification<FrameContainer> spec = SpecForFrames.byAllFieldsContains(filters);
        List<FrameContainer> bySpecContainers = this.containerService.allBySpec(spec);
        return  this.frameService.foundFramesUnits(bySpecContainers);
    }
}
