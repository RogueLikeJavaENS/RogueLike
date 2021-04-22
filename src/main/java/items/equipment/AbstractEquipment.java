package items.equipment;

import items.AbstractItem;
import items.Item;

public abstract class AbstractEquipment extends AbstractItem implements Equipment {

    public AbstractEquipment(String name){
        super(name);
    }
}
