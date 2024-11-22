package ru.frolov.optica3.enums.enums_for_frames;

public enum FrameInstallType {

    RIM("ободок"),
    SEMI("леска"),
    BOLT("винты"),
    CLIP("втулки"),
    WIRE("проволока"),
    NO_STANDARD("нестандартная");

    private String str;

    FrameInstallType(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
