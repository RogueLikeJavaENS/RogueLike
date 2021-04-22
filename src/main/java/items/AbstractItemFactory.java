package items;

import items.potion.PotionFactory;
import items.equipment.EquipmentFactory;
import items.object.ObjectFactory;

public abstract class AbstractItemFactory {

    public AbstractItemFactory(){}

    public static AbstractItemFactory getItemFactory(ItemType itemType){
        if (itemType == ItemType.POTION){
            return new PotionFactory();
        }
        else if (itemType == ItemType.EQUIPMENT){
            return new EquipmentFactory();
        }
        else if (itemType == ItemType.OBJECT){
            return new ObjectFactory();
        }
        return null;
    }


}
