package ru.frolov.optica3.cache.contact_cach;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;

@UtilityClass
public class FiltersPayload_ContactCache {

   private Filters_ContactPayload filtersPayload;

    public static Filters_ContactPayload getFiltersPayload() {
        return filtersPayload;
    }

    public static void setFiltersPayload(Filters_ContactPayload filtersPayload) {
        FiltersPayload_ContactCache.filtersPayload = filtersPayload;
    }
}
