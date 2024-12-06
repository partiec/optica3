package ru.frolov.optica3.enums.contact_enums;

public enum ContactWater {

    NOT_SELECTED("_"),
    _0_10("0 - 10"),
    _10_30("10 - 30"),
    _30_50("30 - 50"),
    _50_80("50 - 80"),
    _80_("80 +");


    private String str;

    ContactWater(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
