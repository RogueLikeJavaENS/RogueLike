package entity.living.inventory;

import gameElement.GameState;
import stuff.Stuff;
import stuff.item.ItemType;

public interface Inventory {
    void addItem(Stuff stuff);
    boolean removeStuff(Stuff stuff);
    boolean useSelectedStuff(GameState gameState);
    boolean useItem(ItemType type, GameState gameState);

    void openSellingShop(GameState gameState);
    void openBuyingSHop(GameState gameState);
    void openInventory(int level);

    void closeInventory();
    void switchCategory();
    boolean nextSelectedStuff();
    boolean previousSelectedStuff();

    int getItemNumber(ItemType type);
    void removeItem(ItemType type);
    boolean containsItem(ItemType type);

    String toStringInventory(GameState gameState);
}
