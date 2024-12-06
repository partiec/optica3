package ru.frolov.optica3.cache.contact_cach;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.frolov.optica3.entity.contact.ContactContainer;

@UtilityClass
public class Page_ContactCache {

    private  Page<ContactContainer>page;

    public  Page<ContactContainer> getPage() {
        return page;
    }

    public  void setPage(Page<ContactContainer> page) {
        Page_ContactCache.page = page;
    }
}
