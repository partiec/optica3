package ru.frolov.optica3.cache.glass_caches;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EditMode_GlassCache {

   private boolean editMode;

    public static boolean isEditMode() {
        return editMode;
    }

    public static void setEditMode(boolean applied) {
        EditMode_GlassCache.editMode = applied;
    }
}
