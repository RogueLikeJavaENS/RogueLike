package utils;

import com.diogonunes.jcolor.Attribute;

/**
 * Colors with their corresponding int according to the JColor documentation
 */
public enum Colors {
    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    MAGENTA(5),
    CYAN(6),
    GREY(8),
    WHITE(15),
    BROWN(94),
    ORANGE(131), //for the monster agro
    YELLOW_GREEN(190), //for the RangerTrap
    PINK(200), // KillerRabbit
    SOFT_DARK(234), // holes
    DEEP_GREY(237), //for the spell range
    DARK_GREY(238), //for the walls
    LIGHT_GREY(244), //for the graves;
    DARK_BLUE(17), // for the aggro
    WALL_WHITE(247);
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

    public static Attribute bgApplyAggro() {return Attribute.BACK_COLOR(51,0,0);}

    public static Attribute bgApplyNormal() { return Attribute.BACK_COLOR(35,35,34);}
}