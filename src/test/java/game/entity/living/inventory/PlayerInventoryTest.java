package game.entity.living.inventory;

import game.stuff.Stuff;
import game.stuff.item.ItemType;
import game.stuff.item.potions.PotionHealth;
import game.stuff.item.potions.XpBottle;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInventoryTest {

    void testRemoveStuff(){
        PlayerInventory playerInventory = new PlayerInventory();
        playerInventory.addItem(new XpBottle());
        boolean remove1 = playerInventory.removeStuff((Stuff) new XpBottle());
        assertTrue(remove1);
        boolean remove2 = playerInventory.removeStuff((Stuff) new PotionHealth());
        assertFalse(remove2);
    }

    void testContainsTest(){
        PlayerInventory playerInventory = new PlayerInventory();
        playerInventory.addItem(new XpBottle());
        assertTrue(playerInventory.containsItem(ItemType.XP_BOTTLE));
        assertFalse(playerInventory.containsItem(ItemType.GOLD_KEY));
    }

    void testGetItemNUmber(){
        PlayerInventory playerInventory = new PlayerInventory();
        assertEquals(0, playerInventory.getItemNumber(ItemType.XP_BOTTLE));
        playerInventory.addItem(new XpBottle());
        assertEquals(1, playerInventory.getItemNumber(ItemType.XP_BOTTLE));
        playerInventory.addItem(new XpBottle());
        playerInventory.addItem(new XpBottle());
        playerInventory.addItem(new XpBottle());
        playerInventory.addItem(new XpBottle());
        assertEquals(5, playerInventory.getItemNumber(ItemType.XP_BOTTLE));



    }

}