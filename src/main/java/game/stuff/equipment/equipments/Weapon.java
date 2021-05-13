package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

/**
 * This class describe the equipment weapon
 */
public class Weapon extends AbstractEquipment {

    /**
     * Create a weapon
     * @param level the level of the weapon
     * @param rarity the rarity of the weapon
     */
    public Weapon(int level, EquipmentRarity rarity) {
        super(level, rarity, EquipmentType.WEAPON);
        GameRule gameRule = new GameRule();
        super.setBonusDamage(gameRule.getBonusDamage(level+rarity.ordinal()));
        super.setName("Weapon");
    }
}
