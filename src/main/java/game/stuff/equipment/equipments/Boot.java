package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

/**
 * This class describe a pair of boot
 */
public class Boot extends AbstractEquipment {

    /**
     * Create a pair of boot
     *
     * @param level the level of the boot
     * @param rarity the rarity of the boot
     */
    public Boot(int level, EquipmentRarity rarity) {
        super(level, rarity, EquipmentType.BOOT);
    }
}
