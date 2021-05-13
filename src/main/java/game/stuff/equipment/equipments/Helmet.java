package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

/**
 * This class describe the equipment Helmet
 */
public class Helmet extends AbstractEquipment {

    /**
     * Create an helmet
     *
     * @param level the level of the helmet
     * @param rarity the rarity of the helmet
     */
    public Helmet(int level, EquipmentRarity rarity) {
        super(level, rarity, EquipmentType.HELMET);
        GameRule gameRule = new GameRule();
        super.setBonusArmor(gameRule.getBonusArmor(level+rarity.ordinal()));            // Set the bonus of the helmet according to gameRule
        super.setName("Helmet");
    }
}
