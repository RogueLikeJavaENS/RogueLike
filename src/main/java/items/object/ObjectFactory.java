package items.object;

import entity.object.ObjectEntity;
import items.AbstractItem;
import items.AbstractItemFactory;
import items.object.potionType.Elixir;
import items.object.potionType.EmptyBottle;
import items.object.potionType.PotionHealth;
import items.object.potionType.XpBottle;

public class ObjectFactory extends AbstractItemFactory {

    public ObjectFactory(){ }

    public Object getObject(ObjectType objectId){
        switch (objectId){
            case HEALTH_POTION:
                return new PotionHealth();
            case ELIXIR:
                return new Elixir();
            case EMPTY_BOTTLE:
                return new EmptyBottle();
            case XP_BOTTLE:
                return new XpBottle();
            case KEY:
                return new Key();

        }
    }
}
