package game.stuff.item;

import game.stuff.item.keys.FloorKey;
import game.stuff.item.keys.GoldKey;
import game.stuff.item.potions.Elixir;
import game.stuff.item.potions.EmptyBottle;
import game.stuff.item.potions.PotionHealth;
import game.stuff.item.potions.XpBottle;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryTest {

    void testGetItem(){
        ItemFactory itemFactory = new ItemFactory();

        assertEquals(new PotionHealth(), itemFactory.getItem(ItemType.HEALTH_POTION,2));
        assertEquals(new Elixir(), itemFactory.getItem(ItemType.ELIXIR,2));
        assertEquals(new XpBottle(), itemFactory.getItem(ItemType.XP_BOTTLE,2));
        assertEquals(new FloorKey(), itemFactory.getItem(ItemType.FLOORKEY,2));
        assertEquals(new GoldKey(), itemFactory.getItem(ItemType.GOLD_KEY,2));
        assertEquals(new MapDungeon(), itemFactory.getItem(ItemType.DUNGEON_MAP,2));
        assertEquals(new EmptyBottle(), itemFactory.getItem(ItemType.EMPTY_BOTTLE,1));
    }

}