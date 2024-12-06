package ru.frolov.optica3.enums.glass_enums;

public enum GlassCoat {
    NOT_SELECTED("_"),
    WITHOUT("нет покр."),
    HARD("Hard"),
    ANTI_REFLECTION("антирефлекс."),
    HMC("HMC"),
    ANTI_STATIC("антистатич."),
    ANTI_WATER("водоотталк."),
    OLEOPHOBIC("грязеотталк."),
    FILTER("фильтр"),
    POLARIZED("поляризац."),
    PHOTOCHROMIC("фотохром"),
    TINTED("тонир."),
    DRIVER("вожден.");

    private String str;

    GlassCoat(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
