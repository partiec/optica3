package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.spec.SpecForFrames;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final PaginationService paginationService;
    private final FrameContainerService containerService;
    private final FrameService frameService;

    public long foundBySpecFrameUnits(FiltersPayload filters) {

        Specification<FrameContainer> spec = SpecForFrames.byAllFieldsContains(filters);
        List<FrameContainer> bySpecContainers = this.containerService.allBySpec(spec);
        return  this.frameService.foundFramesUnits(bySpecContainers);
    }
}
