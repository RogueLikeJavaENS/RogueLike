package entity.living.npc.merchants;

import entity.living.inventory.Inventory;
import entity.living.inventory.MerchantInventory;
import stuff.Stuff;
import gameElement.GameState;
import utils.Position;

import java.util.HashMap;

public interface Merchant {
    HashMap<Stuff,Integer> getShops();
    void doInteraction(GameState gameState);
    Inventory getMerchantInventory();
    Position getPosition();
}
