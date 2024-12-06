package ru.frolov.optica3.cache.glass_cach;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.frolov.optica3.entity.glass.GlassContainer;

@UtilityClass
public class Page_GlassCache {

    private  Page<GlassContainer>page;

    public  Page<GlassContainer> getPage() {
        return page;
    }

    public  void setPage(Page<GlassContainer> page) {
        Page_GlassCache.page = page;
    }
}
