package game.stuff.equipment.equipments;

import game.elements.GameRule;
import game.stuff.equipment.AbstractEquipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;

public class Shield extends AbstractEquipment {

    public Shield(int level, EquipmentRarity rarity, EquipmentType type) {
        super(level, rarity, type);
        GameRule gameRule = new GameRule();
        super.setBonusArmor(gameRule.getBonusArmor(level+rarity.ordinal()));
        super.setName("Shield");
    }
}