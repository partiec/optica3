package ru.frolov.optica3.enums.order_enums;

public enum OrderStage {

    NOT_SELECTED("_"),
    WAIT_FRAME("ждем оправу"),
    WAIT_GLASS("ждем линзы"),
    WAIT_CONTACT("ждем контактн. л."),
    WAIT_ACCESSORY("ждем аксессуар"),
    WAIT_DELIVERY("ждем доставку"),
    EXPERTISE("на экспертизе"),
    PART_RETURNED("частич. возвр."),
    RETURNED("возврат"),
    PRODUCTION("в работе"),
    COMPLETED("ГОТОВ");

    private final String str;

    OrderStage(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
