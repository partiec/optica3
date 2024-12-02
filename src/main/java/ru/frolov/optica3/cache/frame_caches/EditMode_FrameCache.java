package ru.frolov.optica3.cache.frame_caches;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EditMode_FrameCache {

   private boolean editMode;

    public static boolean isEditMode() {
        return editMode;
    }

    public static void setEditMode(boolean applied) {
        EditMode_FrameCache.editMode = applied;
    }
}
