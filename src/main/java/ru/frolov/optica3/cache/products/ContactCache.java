package ru.frolov.optica3.cache.products;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.entity.products.contact.ContactContainer;

@Component
@Getter
@Setter
public class ContactCache {

    private Page<ContactContainer> page;
    private Specification<ContactContainer> spec;
    private ContactDto dto;
    private ContactContainer container;
    private Boolean mode;

    // hint method for cache
    public void cacheAttributesNotNull(Page<ContactContainer> page,
                                       ContactDto dto,
                                       Specification<ContactContainer> specification,
                                       ContactContainer container,
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
