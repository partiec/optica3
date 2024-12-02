package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.frame_caches.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_caches.SpecStatus_FrameCache;
import ru.frolov.optica3.cache.glass_caches.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_caches.SpecStatus_GlassCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.frame_services.FrameContainerService;
import ru.frolov.optica3.spec.frame_spec.SpecForFrames;
import ru.frolov.optica3.spec.glass_spec.SpecForGlasses;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Pagination_GlassService {

    private final GlassContainerService containerService;

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


    public Page<GlassContainer> createPageDependsOnSpecStatusAndCacheSpecStatus(int pageNumber) {

        Pageable pageable = PageRequest.of(
                pageNumber,
                Defaults.PAGE_SIZE,
                Sort.by(Sort.Direction.ASC, "firm"));

        Page<GlassContainer> resultPage = null;

        if (SpecStatus_GlassCache.isApplied()) {

            Filters_GlassPayload filters = FiltersPayload_GlassCache.getFiltersPayload();
            Specification<GlassContainer> spec = SpecForGlasses.byAllFieldsContains(filters);

            resultPage = this.containerService.getPage(
                    spec,
                    pageable);

        } else {

            resultPage = this.containerService.getPage(pageable);

        }


        return resultPage;
    }


}
