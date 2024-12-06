package ru.frolov.optica3.cache.frame_cach;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.frolov.optica3.entity.frame.FrameContainer;

@UtilityClass
public class Page_FrameCache {

    private  Page<FrameContainer>page;

    public  Page<FrameContainer> getPage() {
        return page;
    }

    public  void setPage(Page<FrameContainer> page) {
        Page_FrameCache.page = page;
    }
}
