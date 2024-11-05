package ru.frolov.optica3.cache;

import org.springframework.stereotype.Component;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.payload.FramePayload;

@Component
public class Cache {

    private static FramePayload framePayload;
    private static FiltersPayload filtersPayload;
    private static boolean specWasUsed;

    public static FramePayload getFramePayload() {
        return framePayload;
    }

    public static void setFramePayload(FramePayload payload) {
        framePayload = payload;
    }
    public static FiltersPayload getFiltersPayload() {
        return filtersPayload;
    }

    public static void setFiltersPayload(FiltersPayload payload) {
        filtersPayload = payload;
    }

    public static boolean isSpecWasUsed() {
        return specWasUsed;
    }

    public static void setSpecWasUsed(boolean wasSpecUsed) {
        specWasUsed = wasSpecUsed;
    }
}
