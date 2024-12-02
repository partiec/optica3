package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.frame_caches.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_caches.FramePayloadCache;
import ru.frolov.optica3.cache.frame_caches.Page_FrameCache;
import ru.frolov.optica3.cache.frame_caches.SpecStatus_FrameCache;
import ru.frolov.optica3.cache.glass_caches.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_caches.GlassPayloadCache;
import ru.frolov.optica3.cache.glass_caches.Page_GlassCache;
import ru.frolov.optica3.cache.glass_caches.SpecStatus_GlassCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.frame_payloads.FramePayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.payload.glass_payloads.GlassPayload;

@Service
@RequiredArgsConstructor
public class Cache_GlassService {

    public final void cacheAttributes(Page<GlassContainer> page,
                                      Boolean specStatus,
                                      Filters_GlassPayload filtersPayload,
                                      GlassPayload glassPayload) {

        if (page != null) {
            Page_GlassCache.setPage(page);
        }
        if (specStatus != null) {
            SpecStatus_GlassCache.setApplied(specStatus);
        }
        if (filtersPayload != null) {
            FiltersPayload_GlassCache.setFiltersPayload(filtersPayload);
        }
        if (glassPayload != null) {
            GlassPayloadCache.setGlassPayload(glassPayload);
        }

    }
}
