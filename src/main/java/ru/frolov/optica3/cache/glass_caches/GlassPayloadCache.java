package ru.frolov.optica3.cache.glass_caches;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.frame_payloads.FramePayload;
import ru.frolov.optica3.payload.glass_payloads.GlassPayload;

@UtilityClass
public class GlassPayloadCache {

    private GlassPayload glassPayload;

    public static GlassPayload getGlassPayload() {
        return glassPayload;
    }

    public static void setGlassPayload(GlassPayload glassPayload) {
        GlassPayloadCache.glassPayload = glassPayload;
    }
}
