package items.equipment;

import items.AbstractItem;
import items.Item;

public abstract class AbstractEquipment extends AbstractItem implements Equipment {
    public AbstractEquipment(int weight,String name, String desciption){
        super(weight, name, desciption);
    }
}
