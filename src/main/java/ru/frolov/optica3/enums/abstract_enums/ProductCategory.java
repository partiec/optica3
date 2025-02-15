package ru.frolov.optica3.enums.abstract_enums;

public enum ProductCategory {

    NOT_SELECTED("_"),

    ACCESSORY("аксессуар"),
    CONTACT("конт. линза"),
    FRAME("оправа"),
    GLASS("очк. линза");

    private String str;

    ProductCategory(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
