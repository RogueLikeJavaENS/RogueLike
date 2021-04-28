package stuff.equipment;

import stuff.equipment.equipments.*;

public class EquipmentFactory {

    public EquipmentFactory() {}

    public Equipment getEquipment(int level, EquipmentType type, EquipmentRarity rarity) {

        Equipment equipment;

        switch (type) {
            case WEAPON:
                equipment =  new Weapon(level, rarity, type);
                break;
            case SHIELD:
                equipment =  new Shield(level, rarity, type);
                break;
            case ARMOR:
                equipment =  new Armor(level, rarity, type);
                break;
            case BOOT:
                equipment =  new Boot(level, rarity, type);
                break;
            case GLOVE:
                equipment =  new Glove(level, rarity, type);
                break;
            case HELMET:
                equipment =  new Helmet(level, rarity, type);
                break;
            case PANT:
                equipment =  new Pants(level, rarity, type);
                break;
            default:
                equipment = null;
        }
        return equipment;
    }
}
