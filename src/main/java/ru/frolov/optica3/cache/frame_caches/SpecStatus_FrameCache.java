package ru.frolov.optica3.cache.frame_caches;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecStatus_FrameCache {

   private boolean applied;

    public static boolean isApplied() {
        return applied;
    }

    public static void setApplied(boolean applied) {
        SpecStatus_FrameCache.applied = applied;
    }
}
