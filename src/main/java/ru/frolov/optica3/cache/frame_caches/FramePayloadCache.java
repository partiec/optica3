package ru.frolov.optica3.cache.frame_caches;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.frame_payloads.FramePayload;

@UtilityClass
public class FramePayloadCache {

    private FramePayload framePayload;

    public FramePayload getFramePayload() {
        return framePayload;
    }

    public void setFramePayload(FramePayload framePayload) {
        FramePayloadCache.framePayload = framePayload;
    }
}
