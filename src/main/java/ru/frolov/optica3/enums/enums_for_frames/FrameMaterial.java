package ru.frolov.optica3.enums.enums_for_frames;

public enum FrameMaterial {

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
