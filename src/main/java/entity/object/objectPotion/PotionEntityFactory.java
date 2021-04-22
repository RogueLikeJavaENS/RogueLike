package entity.object.objectPotion;

import items.object.ObjectType;
import utils.Position;

public class PotionEntityFactory {

    public PotionEntityFactory(){}

    public ObjectPotion getObjectPotion(ObjectType potionType, Position position){
        switch (potionType){
            case HEALTH_POTION:
                return new PotionHealthEntity(position);
            case ELIXIR:
                return new ElixirEntity(position);

            case XP_BOTTLE:
                return new XpBottleEntity(position);

            case EMPTY_BOTTLE:
                return new EmptyBottleEnity(position);
        }
        return null;
    }
}
