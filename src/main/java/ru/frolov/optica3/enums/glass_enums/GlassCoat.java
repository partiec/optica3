package ru.frolov.optica3.enums.glass_enums;

public enum GlassCoat {
    NOT_SELECTED("_"),
    HMC("HMC"),
    ANTI_REFLECTION("антирефлекс."),
    ANTI_STATIC("антистатич."),
    ANTI_WATER("водоотталк."),
    OLEOPHOBIC("грязеотталк."),
    FILTER("фильтр"),
    POLARIZED("поляризац.");

    private String str;

    GlassCoat(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
