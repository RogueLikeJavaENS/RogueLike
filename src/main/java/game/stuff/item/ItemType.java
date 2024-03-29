package game.stuff.item;

import utils.Colors;

public enum ItemType {
    HEALTH_POTION,
    ELIXIR,
    XP_BOTTLE,
    EMPTY_BOTTLE,
    FLOORKEY,
    GOLD_KEY,
    DUNGEON_MAP;

    public static Colors getColor(ItemType type) {
        switch (type) {
            case HEALTH_POTION:
                return Colors.RED;
            case ELIXIR:
                return Colors.BLUE;
            case XP_BOTTLE:
                return Colors.GREEN;
            case FLOORKEY:
                return Colors.MAGENTA;
            case GOLD_KEY:
                return Colors.YELLOW;
            case DUNGEON_MAP:
                return Colors.ORANGE;
        }
        return Colors.WHITE;
    }

    }
