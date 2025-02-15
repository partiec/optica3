package ru.frolov.optica3.cache.products;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.entity.products.frame.FrameContainer;

@Component
@Getter
@Setter
public class FrameCache {

    private Page<FrameContainer> page;
    private Specification<FrameContainer> spec;
    private FrameDto dto;
    private FrameContainer container;
    private Boolean mode;

    // hint method for cache
    public void cacheAttributesNotNull(Page<FrameContainer> page,
                                       FrameDto dto,
                                       Specification<FrameContainer> specification,
                                       FrameContainer container,
                                       Boolean mode) {

        if (page != null)
            this.page = page;
        if (dto != null)
            this.dto = dto;
        if (specification != null)
            this.spec = specification;
        if (container != null)
            this.container = container;
        if (mode != null)
            this.mode = mode;
    }
}
