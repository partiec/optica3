package ru.frolov.optica3.enums.contact_enums;

public enum ContactOxygen {

    NOT_SELECTED("_"),
   _0_10("0 - 10"),
    _10_30("10 -30"),
    _30_60("30 - 60"),
    _60_100("60 - 100"),
    _100_200("100 - 200");


    private String str;

    ContactOxygen(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
