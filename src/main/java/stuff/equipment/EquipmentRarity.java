package stuff.equipment;

import utils.Colors;

public enum EquipmentRarity {
    E, D, C, B, A, S, L;

    public static Colors getColor(EquipmentRarity rarity) {
        switch (rarity) {
            case E:
                return Colors.GREY;
            case D:
                return Colors.BROWN;
            case C:
                return Colors.ORANGE;
            case B:
                return Colors.GREEN;
            case A:
                return Colors.RED;
            case S:
                return Colors.MAGENTA;
            case L:
                return Colors.CYAN;
        }
        return Colors.WHITE;
    }
}
