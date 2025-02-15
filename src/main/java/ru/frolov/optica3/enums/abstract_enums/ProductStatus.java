package ru.frolov.optica3.enums.abstract_enums;

public enum ProductStatus {

    NOT_SELECTED("_"),
    STOCK("на складе"),
    ORDERED("в заказе"),
    DEFECTIVE("брак"),
    EXPERTISE("эксперт."),
    RETURNED("возврат");

    private String str;

    ProductStatus(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
