package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.cache.FiltersPayloadCache;
import ru.frolov.optica3.cache.SpecStatusCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.spec.SpecForFrames;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaginationService {

    private final FrameContainerService containerService;

    public List<FrameContainer> getPageContent(List<FrameContainer> list,
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


    public Page<FrameContainer> createPageDependsOnSpecStatusAndCacheSpecStatus(int pageNumber) {

        Pageable pageable = PageRequest.of(
                pageNumber,
                Defaults.PAGE_SIZE,
                Sort.by(Sort.Direction.ASC, "firm"));

        Page<FrameContainer> resultPage = null;

        if (SpecStatusCache.isApplied()) {

            FiltersPayload filters = FiltersPayloadCache.getFiltersPayload();
            Specification<FrameContainer> spec = SpecForFrames.byAllFieldsContains(filters);

            resultPage = this.containerService.getPage(
                    spec,
                    pageable);

        } else {

            resultPage = this.containerService.getPage(pageable);

        }


        return resultPage;
    }


}
