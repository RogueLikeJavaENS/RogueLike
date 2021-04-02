package utils;

import com.diogonunes.jcolor.Attribute;

public enum Colors {
    RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), GREY(8), BROWN(94), DARK_GREY(238);

    private final int value;

    Colors(int value) {
        this.value = value;
    }

    public Attribute textApply() {
        return Attribute.TEXT_COLOR(value);
    }

    public Attribute bgApply() {
        return Attribute.BACK_COLOR(value);
    }
}