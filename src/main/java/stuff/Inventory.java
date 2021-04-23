package stuff;

import gameElement.GameState;
import stuff.item.Item;
import stuff.item.ItemType;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Stuff> inventory;

    public Inventory() {
        inventory = new ArrayList<>();
    }

    public void addItem(Stuff stuff) {
        inventory.add(stuff);
    }

    public boolean useItem(ItemType type, GameState gameState) {
        boolean used = false;
        Item itemToDelete = null;
        for (Stuff stuff : inventory) {
            if (stuff.isUsable()) {
                Item item = (Item) stuff;
                if (item.getType().equals(type)) {
                    item.useItem(gameState);
                    itemToDelete = item;
                    used = true;
                    break;
                }
            }
        }
        inventory.remove((Stuff) itemToDelete);
        return used;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventory=" + inventory +
                '}';
    }

    public int getItemNumber(ItemType type) {
        int acc = 0;
        for (Stuff stuff : inventory) {
            if (stuff.isUsable()) {
                Item item = (Item) stuff;
                if (item.getType().equals(type)) {
                    acc++;
                }
            }
        }
        return acc;
    }
}
