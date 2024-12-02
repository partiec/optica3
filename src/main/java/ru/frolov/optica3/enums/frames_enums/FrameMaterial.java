package ru.frolov.optica3.enums.frames_enums;

public enum FrameMaterial {
    NOT_SELECTED("_"),
    PLASTIC("пластик"),
    METALL("металл"),
    COMBINED("комбинированная");

    private String str;

    FrameMaterial(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
