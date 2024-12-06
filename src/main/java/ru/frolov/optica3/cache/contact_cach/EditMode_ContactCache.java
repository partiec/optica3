package ru.frolov.optica3.cache.contact_cach;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EditMode_ContactCache {

   private boolean editMode;

    public static boolean isEditMode() {
        return editMode;
    }

    public static void setEditMode(boolean applied) {
        EditMode_ContactCache.editMode = applied;
    }
}
