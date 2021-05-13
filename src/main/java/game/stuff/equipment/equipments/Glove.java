package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

/**
 * This class describe the equipment gloves
 */
public class Glove extends AbstractEquipment {

    /**
     * Create gloves
     *
     * @param level the level of the gloves
     * @param rarity the rarity of the gloves
     */
    public Glove(int level, EquipmentRarity rarity) {
        super(level, rarity, EquipmentType.GLOVE);
        GameRule gameRule = new GameRule();
        super.setBonusArmor(gameRule.getBonusArmor(level+rarity.ordinal()));             // Set the bonus of the gloves according to gameRule
        super.setName("Glove");
    }
}
