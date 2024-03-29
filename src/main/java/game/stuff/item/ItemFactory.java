package game.stuff.item;

import game.elements.GameRule;
import game.stuff.item.keys.GoldKey;
import game.stuff.item.keys.FloorKey;
import game.stuff.item.potions.*;

public class ItemFactory {

    public ItemFactory(){ }

    public static Item getItem(ItemType itemType, int level) {
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
            case DUNGEON_MAP:
                item = new MapDungeon();
                break;
            default:
                item = new EmptyBottle();
                break;
        }
        item.setPrice(GameRule.getItemPrice(level, itemType));
        return item;
    }
}
