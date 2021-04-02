package items.potion;

import items.AbstractItem;

public abstract class AbstractPotion extends AbstractItem implements Potion {

    public AbstractPotion(int weight,String name, String description){
        super(weight, name, description);
    }
}
