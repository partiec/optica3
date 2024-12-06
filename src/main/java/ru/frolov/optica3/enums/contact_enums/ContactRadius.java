package ru.frolov.optica3.enums.contact_enums;

public enum ContactRadius {

    NOT_SELECTED("_"),
    _8_3("8.3"),
    _8_4("8.4"),
    _8_5("8.5"),
    _8_6("8.6"),
    _8_7("8.7"),
    _8_8("8.8"),
    _8_9("8.9"),
    _9_0("9.0");


    private String str;

    ContactRadius(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
