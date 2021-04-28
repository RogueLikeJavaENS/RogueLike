package stuff.equipment.equipments;

import gameElement.GameRule;
import stuff.equipment.AbstractEquipment;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;

public class Armor extends AbstractEquipment {

    public Armor(int level, EquipmentRarity rarity, EquipmentType type) {
        super(level, rarity, type);
        GameRule gameRule = new GameRule();
        super.setBonusArmor(gameRule.getBonusArmor(level+rarity.ordinal()));
        super.setName("Armor");
    }
}
