package ru.frolov.optica3.enums.frames_enums;

public enum FrameInstallType {

    NOT_SELECTED("_"),
    RIM("ободок"),
    SEMI("леска"),
    BOLT("винты"),
    CLIP("втулки"),
    WIRE("проволока"),
    NON_STANDARD("нестандартная");

    private String str;

    FrameInstallType(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
