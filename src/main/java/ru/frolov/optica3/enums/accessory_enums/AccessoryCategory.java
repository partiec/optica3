package ru.frolov.optica3.enums.accessory_enums;

public enum AccessoryCategory {

    NOT_SELECTED("_"),

    // ACCESSORIES ___
    CASE("футляр"),
    NAPKIN("салфетка"),
    SCREWDRIVER("отвертка"),
    SPRAY("спрей"),
    SOLUTION("раствор МКЛ");
    // ACCESSORIES /


    private String str;

    AccessoryCategory(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
