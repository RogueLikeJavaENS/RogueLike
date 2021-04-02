package items.weapons;

import items.AbstractItem;

public abstract class AbstractWeapon extends AbstractItem implements Weapon {


    public AbstractWeapon(int weight,String name, String description) {
        super(weight,name,description);
    }


}
