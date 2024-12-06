package ru.frolov.optica3.service.contact_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.contact_cach.FiltersPayload_ContactCache;
import ru.frolov.optica3.cache.contact_cach.SpecStatus_ContactCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.spec.contact_spec.SpecForContacts;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Pagination_ContactService {

    private final ContactContainerService containerService;
    private List<ContactContainer> foundContainers;

    public List<ContactContainer> getFoundContainers() {
        return foundContainers;
    }

    public List<ContactContainer> getPageContent(List<ContactContainer> list,
                                               int pageNumber,
                                               int pageSize) {

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());

        if (startIndex >= endIndex || startIndex < 0) {
            // Page number out of range or no data for the page
            return new LinkedList<>();
        }

        return list.subList(startIndex, endIndex);
    }

    public int totalPages(List<?> list,
                          int pageSize) {

        int number = list.size() / pageSize;

        if ((list.size() % pageSize) > 0) {
            number = number + 1;
        }
        return number;
    }


    public Page<ContactContainer> createPageDependsOnSpecStatus(int pageNumber, String whichFieldOnInput) {

        Pageable pageable = PageRequest.of(
                        pageNumber,
                        Defaults.PAGE_SIZE)
                .withSort(Sort.Direction.ASC, "firm",  "design", "period","oxygen","water", "details", "purchase", "sale", "diameter","radius","dioptre");

        Specification<ContactContainer> spec = null;
        Page<ContactContainer> resultPage = null;

        // если bySpec
        if (SpecStatus_ContactCache.isApplied()) {

            // дергаем фильтры из кэша
            Filters_ContactPayload filters = FiltersPayload_ContactCache.getFiltersPayload();
            // вытаскиваем нужную spec
            spec = SpecForContacts.byAllContains(filters);

            // создаем page bySpec
            resultPage = this.containerService.getPage(
                    spec,
                    pageable);

            // заодно добываем список всех найденных контейнеров
            foundContainers = this.containerService.allBySpec(spec);

        } else {

            // создаем page noSpec
            resultPage = this.containerService.getPage(pageable);

            // заодно добываем список всех найденных контейнеров
            foundContainers = this.containerService.all();
        }


        return resultPage;
    }


}
