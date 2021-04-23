package stuff.equipment;

import stuff.AbstractStuff;

public abstract class AbstractEquipment extends AbstractStuff implements Equipment {

    public AbstractEquipment(String name){
        super(name, false, true);
    }
}
