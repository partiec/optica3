package ru.frolov.optica3.cache;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EditModeCache {

   private boolean editMode;

    public static boolean isEditMode() {
        return editMode;
    }

    public static void setEditMode(boolean applied) {
        EditModeCache.editMode = applied;
    }
}
