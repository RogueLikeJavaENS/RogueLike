package game.stuff.equipment.equipments;

import game.element.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

public class Weapon extends AbstractEquipment {

    public Weapon(int level, EquipmentRarity rarity, EquipmentType type) {
        super(level, rarity, type);
        GameRule gameRule = new GameRule();
        super.setBonusDamage(gameRule.getBonusDamage(level+rarity.ordinal()));
        super.setName("Weapon");
    }
}
