package stuff.equipment.equipments;

import gameElement.GameRule;
import stuff.equipment.AbstractEquipment;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;

public class Weapon extends AbstractEquipment {

    public Weapon(int level, EquipmentRarity rarity, EquipmentType type) {
        super(level, rarity, type);
        GameRule gameRule = new GameRule();
        super.setBonusDamage(gameRule.getBonusDamage(level+rarity.ordinal()));
        super.setName("Weapon");
    }
}
