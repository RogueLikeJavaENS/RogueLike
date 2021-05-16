package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

/**
 * This class describe the equipment Shield
 */
public class Shield extends AbstractEquipment {

    /**
     * Create a shield
     * @param level the level of the shield
     * @param rarity the rarity of the shield
     */
    public Shield(int level, EquipmentRarity rarity) {
        super(level, rarity, EquipmentType.SHIELD);
    }
}
