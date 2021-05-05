package stuff.item;

import gameElement.GameRule;
import stuff.item.keys.GoldKey;
import stuff.item.keys.FloorKey;
import stuff.item.potions.*;

public class ItemFactory {

    public ItemFactory(){ }

    public Item getItem(ItemType itemType, int level) {
        Item item;
        switch (itemType) {
            case HEALTH_POTION:
                item = new PotionHealth();
                break;
            case ELIXIR:
                item = new  Elixir();
                break;
            case XP_BOTTLE:
                item = new XpBottle();
                break;
            case FLOORKEY:
                item = new FloorKey();
                break;
            case GOLD_KEY:
                item = new GoldKey();
                break;
            default:
                item = new EmptyBottle();
                break;
        }
        GameRule gm = new GameRule();
        item.setPrice(gm.getPotionPrice(level, itemType));
        return item;
    }
}
