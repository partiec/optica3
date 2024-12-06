package ru.frolov.optica3.service.frame_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.frame_cach.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_cach.FramePayloadCache;
import ru.frolov.optica3.cache.frame_cach.Page_FrameCache;
import ru.frolov.optica3.cache.frame_cach.SpecStatus_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.frame_payloads.FramePayload;

@Service
@RequiredArgsConstructor
public class Cache_FrameService {

    public final void cacheAttributes(Page<FrameContainer> page,
                                      Boolean specStatus,
                                      Filters_FramePayload filtersFramePayload,
                                      FramePayload framePayload) {

        if (page != null) {
            Page_FrameCache.setPage(page);
        }
        if (specStatus != null) {
            SpecStatus_FrameCache.setApplied(specStatus);
        }
        if (filtersFramePayload != null) {
            FiltersPayload_FrameCache.setFiltersFramePayload(filtersFramePayload);
        }
        if (framePayload != null) {
            FramePayloadCache.setFramePayload(framePayload);
        }

    }
}
