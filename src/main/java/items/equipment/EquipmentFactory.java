package items.equipment;

import items.AbstractItemFactory;

public class EquipmentFactory extends AbstractItemFactory {

    public Equipment getEquipment(EquipmentType equipmentType,String name){
        if (equipmentType == EquipmentType.SWORD){
            return new Sword(name);
        }
        return null;
    }

}
