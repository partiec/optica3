package ru.frolov.optica3.enums.order_enums;

public enum OrderPayment {

    NOT_SELECTED("_"),
    PAID("ОПЛАЧЕН"),
    UNPAID("не оплачен"),
    PART_PAID("частич. опл."),
    RETURN("возврат"),
    PART_RETURN("частич. возвр.");

    private final String str;

    OrderPayment(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
