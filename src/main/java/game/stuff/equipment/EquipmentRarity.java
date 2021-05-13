package game.stuff.equipment;

import utils.Colors;

/**
 * Enumeration of the rarity if the equipment
 */
public enum EquipmentRarity {
    E("Common"), D("Uncommon"), C("Rare"), B("Epic"), A("Heroic"), S("Mythic"), L("Legendary");

    private final String rarity;

    EquipmentRarity(String rarity) {
        this.rarity = rarity;
    }

    public static Colors getColor(EquipmentRarity rarity) {
        switch (rarity) {
            case E:
                return Colors.GREY;
            case D:
                return Colors.GREEN;
            case C:
                return Colors.BLUE;
            case B:
                return Colors.MAGENTA;
            case A:
                return Colors.YELLOW;
            case S:
                return Colors.ORANGE;
            case L:
                return Colors.RED;
        }
        return Colors.WHITE;
    }

    public String getRarity() {
        return rarity;
    }
}
