package ru.frolov.optica3.cache.products;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;

@Component
@Getter
@Setter
public class AccessoryCache {

    private Page<AccessoryContainer> page;
    private Specification<AccessoryContainer> spec;
    private AccessoryDto dto;
    private AccessoryContainer container;
    private AccessoryUnit unit;
    private Boolean mode;

    // hint method for cache
    public void cacheAttributesNotNull(Page<AccessoryContainer> page,
                                       AccessoryDto dto,
                                       Specification<AccessoryContainer> specification,
                                       AccessoryContainer container,
                                       AccessoryUnit unit,
                                       Boolean mode) {

        if (page != null)
            this.page = page;
        if (dto != null)
            this.dto = dto;
        if (specification != null)
            this.spec = specification;
        if (container != null)
            this.container = container;
        if (unit != null)
            this.unit = unit;
        if (mode != null)
            this.mode = mode;
    }
}
