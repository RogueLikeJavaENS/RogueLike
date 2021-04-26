package stuff.item;

import stuff.item.keys.GoldKey;
import stuff.item.keys.FloorKey;
import stuff.item.potions.*;

public class ItemFactory {

    public ItemFactory(){ }

    public Item getItem(ItemType itemType) {
        switch (itemType) {
            case HEALTH_POTION:
                return new PotionHealth();
            case ELIXIR:
                return new Elixir();
            case EMPTY_BOTTLE:
                return new EmptyBottle();
            case XP_BOTTLE:
                return new XpBottle();
            case KEY:
                return new FloorKey();
            case GOLD_KEY:
                return new GoldKey();
            default:
                return null;
        }
    }
}
