package ru.frolov.optica3.cache.frame_cach;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;

@UtilityClass
public class FiltersPayload_FrameCache {

   private Filters_FramePayload filtersFramePayload;

    public Filters_FramePayload getFiltersPayload() {
        return filtersFramePayload;
    }

    public  void setFiltersFramePayload(Filters_FramePayload filtersFramePayload) {
        FiltersPayload_FrameCache.filtersFramePayload = filtersFramePayload;
    }
}
