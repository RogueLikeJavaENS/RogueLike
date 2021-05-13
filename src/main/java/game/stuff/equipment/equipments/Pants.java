package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

/**
 * This class describe the equipment Pants
 */
public class Pants extends AbstractEquipment {

    /**
     * Create pants
     * @param level the level of the pants
     * @param rarity the rarity of the pants
     */
    public Pants(int level, EquipmentRarity rarity) {
        super(level, rarity, EquipmentType.PANT);
        GameRule gameRule = new GameRule();
        super.setBonusArmor(gameRule.getBonusArmor(level+rarity.ordinal()));            // Set the bonus of the pants according to gameRule
        super.setName("Pants");
    }
}
