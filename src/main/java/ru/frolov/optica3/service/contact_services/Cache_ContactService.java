package ru.frolov.optica3.service.contact_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.contact_cach.ContactPayloadCache;
import ru.frolov.optica3.cache.contact_cach.FiltersPayload_ContactCache;
import ru.frolov.optica3.cache.contact_cach.Page_ContactCache;
import ru.frolov.optica3.cache.contact_cach.SpecStatus_ContactCache;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.payload.contact_payloads.ContactPayload;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;

@Service
@RequiredArgsConstructor
public class Cache_ContactService {

    public final void cacheAttributes(Page<ContactContainer> page,
                                      Boolean specStatus,
                                      Filters_ContactPayload filtersPayload,
                                      ContactPayload contactPayload) {

        if (page != null) {
            Page_ContactCache.setPage(page);
        }
        if (specStatus != null) {
            SpecStatus_ContactCache.setApplied(specStatus);
        }
        if (filtersPayload != null) {
            FiltersPayload_ContactCache.setFiltersPayload(filtersPayload);
        }
        if (contactPayload != null) {
            ContactPayloadCache.setContactPayload(contactPayload);
        }

    }
}
