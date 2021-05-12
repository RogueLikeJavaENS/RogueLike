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
    DEEP_GREY(237), //for the game.entity.living.player.spell range
    DARK_GREY(238), //for the walls
    LIGHT_GREY(244), //for the graves
    PINK(200), // KillerRabbit
    SOFT_DARK(234);

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