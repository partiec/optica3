package ru.frolov.optica3.enums.contact_enums;

public enum ContactPeriod {

    NOT_SELECTED("_"),
    _1_DAY("1 день"),
    _2_WEEK("2 нед."),
    _1_MONTH("1 мес."),
    _3_MONTH("3 мес."),
    _6_MONTH("6 мес.");


    private String str;

    ContactPeriod(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
