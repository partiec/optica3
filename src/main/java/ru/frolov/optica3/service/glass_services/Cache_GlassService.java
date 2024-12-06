package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.glass_cach.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_cach.GlassPayloadCache;
import ru.frolov.optica3.cache.glass_cach.Page_GlassCache;
import ru.frolov.optica3.cache.glass_cach.SpecStatus_GlassCache;
import ru.frolov.optica3.entity.glass.GlassContainer;
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
