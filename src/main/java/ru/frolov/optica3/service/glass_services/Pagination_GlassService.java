package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.glass_cach.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_cach.SpecStatus_GlassCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.spec.glass_spec.SpecForGlasses;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Pagination_GlassService {

    private final GlassContainerService containerService;
    private List<GlassContainer> foundContainers;

    public List<GlassContainer> getFoundContainers() {
        return foundContainers;
    }

    public List<GlassContainer> getPageContent(List<GlassContainer> list,
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


    public Page<GlassContainer> createPageDependsOnSpecStatus(int pageNumber, String whichFieldOnInput) {

        Pageable pageable = PageRequest.of(
                        pageNumber,
                        Defaults.PAGE_SIZE)
                .withSort(Sort.Direction.ASC, "firm", "material", "design", "coat", "details", "purchase", "sale", "dioptre");

        Specification<GlassContainer> spec = null;
        Page<GlassContainer> resultPage = null;

        // если bySpec
        if (SpecStatus_GlassCache.isApplied()) {

            // дергаем фильтры из кэша
            Filters_GlassPayload filters = FiltersPayload_GlassCache.getFiltersPayload();
            // вытаскиваем нужную spec
            spec = SpecForGlasses.byAllContains(filters);

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
