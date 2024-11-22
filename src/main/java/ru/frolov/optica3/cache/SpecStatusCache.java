package ru.frolov.optica3.cache;

import lombok.experimental.UtilityClass;
import ru.frolov.optica3.payload.FiltersPayload;

@UtilityClass
public class SpecStatusCache {

   private boolean applied;

    public static boolean isApplied() {
        return applied;
    }

    public static void setApplied(boolean applied) {
        SpecStatusCache.applied = applied;
    }
}
