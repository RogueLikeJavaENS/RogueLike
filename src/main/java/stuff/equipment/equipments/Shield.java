package stuff.equipment.equipments;

import gameElement.GameRule;
import stuff.equipment.AbstractEquipment;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;

public class Shield extends AbstractEquipment {

    public Shield(int level, EquipmentRarity rarity, EquipmentType type) {
        super(level, rarity, type);
        GameRule gameRule = new GameRule();
        super.setBonusArmor(gameRule.getBonusArmor(level+rarity.ordinal()));
        super.setName("Shield");
    }
}
