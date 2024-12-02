package ru.frolov.optica3.cache.glass_caches;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;

@UtilityClass
public class FiltersPayload_GlassCache {

   private Filters_GlassPayload filtersPayload;

    public static Filters_GlassPayload getFiltersPayload() {
        return filtersPayload;
    }

    public static void setFiltersPayload(Filters_GlassPayload filtersPayload) {
        FiltersPayload_GlassCache.filtersPayload = filtersPayload;
    }
}
