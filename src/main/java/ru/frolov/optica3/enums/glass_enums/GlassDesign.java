package ru.frolov.optica3.enums.glass_enums;

public enum GlassDesign {
    NOT_SELECTED("_"),
    SPHERE("сфера"),
    ASPHERIC("асферика"),
    BIASPHERIC("биасферика"),
    BIFOCAL("бифокал"),
    MULTiFOCAL("мультифокал"),
    PROGRESSIVE("прогрессив"),
    OFFICE("офис");

    private String str;

    GlassDesign(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
