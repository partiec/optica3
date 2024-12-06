package ru.frolov.optica3.enums.contact_enums;

public enum ContactDiameter {

    NOT_SELECTED("_"),
    _13_8("13.8"),
    _14_0("14.0"),
    _14_1("14.1"),
    _14_2("14.2"),
    _14_3("14.3"),
    _14_4("14.4"),
    _14_5("14.5");


    private String str;

    ContactDiameter(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
