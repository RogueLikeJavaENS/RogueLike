package game.entity.living.npc.merchants;

import game.entity.living.inventory.Inventory;
import game.stuff.Stuff;
import game.element.GameState;

import java.util.HashMap;

public interface Merchant {
    HashMap<Stuff,Integer> getShops();
    void doInteraction(GameState gameState);
    Inventory getMerchantInventory();
}
