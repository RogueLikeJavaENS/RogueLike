package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

/**
 * This class describe the equipment armor
 */
public class Armor extends AbstractEquipment {

    /**
     * Create an armor
     *
     * @param level level of the equipment
     * @param rarity rarity of the equipment
     */
    public Armor(int level, EquipmentRarity rarity) {
        super(level, rarity, EquipmentType.ARMOR);
        GameRule gameRule = new GameRule();
        super.setBonusArmor(gameRule.getBonusArmor(level+rarity.ordinal())); // Set the bonus of the armor according to gameRule
        super.setName("Armor");
    }
}
