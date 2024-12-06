package ru.frolov.optica3.cache.contact_cach;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecStatus_ContactCache {

   private boolean applied;

    public static boolean isApplied() {
        return applied;
    }

    public static void setApplied(boolean applied) {
        SpecStatus_ContactCache.applied = applied;
    }
}
