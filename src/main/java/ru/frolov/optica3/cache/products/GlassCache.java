package ru.frolov.optica3.cache.products;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;

@Component
@Getter
@Setter
public class GlassCache {

    private Page<GlassContainer> page;
    private Specification<GlassContainer> spec;
    private GlassDto dto;
    private GlassContainer container;
    private Boolean mode;

    // hint method for cache
    public void cacheAttributesNotNull(Page<GlassContainer> page,
                                       GlassDto dto,
                                       Specification<GlassContainer> specification,
                                       GlassContainer container,
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
