package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.FiltersPayloadCache;
import ru.frolov.optica3.cache.FramePayloadCache;
import ru.frolov.optica3.cache.PageCache;
import ru.frolov.optica3.cache.SpecStatusCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.payload.FramePayload;

@Service
@RequiredArgsConstructor
public class CacheService {

    public final void cacheAttributes(Page<FrameContainer> page,
                                      Boolean specStatus,
                                      FiltersPayload filtersPayload,
                                      FramePayload framePayload) {

        if (page != null) {
            PageCache.setPage(page);
        }
        if (specStatus != null) {
            SpecStatusCache.setApplied(specStatus);
        }
        if (filtersPayload != null) {
            FiltersPayloadCache.setFiltersPayload(filtersPayload);
        }
        if (framePayload != null) {
            FramePayloadCache.setFramePayload(framePayload);
        }

    }
}
