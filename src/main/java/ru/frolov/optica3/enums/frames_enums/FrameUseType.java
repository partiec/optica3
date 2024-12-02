package ru.frolov.optica3.enums.frames_enums;

public enum FrameUseType {
    NOT_SELECTED("_"),
    CORRECT("корриг."),
    SUN("солнцезащитн."),
    TRAINER("тренаж."),
    CHILD("детск."),
    SPORT("спорт."),
    PROFESSIONAL("професс.");


    private String str;

    FrameUseType(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
