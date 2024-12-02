package ru.frolov.optica3.enums.glass_enums;

public enum GlassMaterial {
    NOT_SELECTED("_"),
    _1_49("1.49"),
    _1_5("1.5"),
    _1_56("1.56"),
    _1_61("1.61"),
    _1_67("1.67"),
    _1_7("1.7"),
    _1_74("1.74"),
    _1_9("1.9"),
    GLASS("стекло");

    private String str;

    GlassMaterial(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
