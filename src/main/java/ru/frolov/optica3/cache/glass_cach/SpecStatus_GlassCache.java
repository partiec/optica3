package ru.frolov.optica3.cache.glass_cach;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecStatus_GlassCache {

   private boolean applied;

    public static boolean isApplied() {
        return applied;
    }

    public static void setApplied(boolean applied) {
        SpecStatus_GlassCache.applied = applied;
    }
}
