package ru.frolov.optica3.cache;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.FiltersPayload;

@UtilityClass
public class FiltersPayloadCache {

   private FiltersPayload filtersPayload;

    public  FiltersPayload getFiltersPayload() {
        return filtersPayload;
    }

    public  void setFiltersPayload(FiltersPayload filtersPayload) {
        FiltersPayloadCache.filtersPayload = filtersPayload;
    }
}
