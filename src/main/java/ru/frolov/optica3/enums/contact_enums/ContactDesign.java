package ru.frolov.optica3.enums.contact_enums;

public enum ContactDesign {

    NOT_SELECTED("_"),
    SPH("сфер."),
    ASPHER("асфер."),
    TOR("торич."),
    MULTI("мульти");


    private String str;

    ContactDesign(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
