package ru.frolov.optica3.cache;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;

@Component
public class Cache {

    private static Page<FrameContainer> page;
    private static FramePayload framePayload;
    private static boolean useSpec;


    public static Page<FrameContainer> getPage() {
        return page;
    }

    public static FramePayload getPayload() {
        return framePayload;
    }

    public static void setPage(Page<FrameContainer> pageForCache) {
        page = pageForCache;
    }

    public static void setPayload(FramePayload payloadForCache) {
        framePayload = payloadForCache;
    }

    public static boolean specWasUsed() {
        return useSpec;
    }

    public static void setSpecWasUsed(boolean useSpec) {
        Cache.useSpec = useSpec;
    }
}
