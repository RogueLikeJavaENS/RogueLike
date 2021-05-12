package game.entity.object.potion;

import game.entity.object.potion.potions.ElixirEntity;
import game.entity.object.potion.potions.EmptyBottleEntity;
import game.entity.object.potion.potions.PotionHealthEntity;
import game.entity.object.potion.potions.XpBottleEntity;
import game.stuff.item.ItemType;
import utils.Position;

public class PotionEntityFactory {

    public PotionEntityFactory(){}

    public PotionEntity getPotionEntity(ItemType itemType, Position position){
        switch (itemType) {
            case HEALTH_POTION:
                return new PotionHealthEntity(position);
            case ELIXIR:
                return new ElixirEntity(position);

            case XP_BOTTLE:
                return new XpBottleEntity(position);

            case EMPTY_BOTTLE:
                return new EmptyBottleEntity(position);
        }
        return null;
    }
}
