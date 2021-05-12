package game.entity.living.npc.merchants;

import game.entity.living.inventory.Inventory;
import game.stuff.Stuff;
import game.elements.GameState;
import utils.Position;

import java.util.HashMap;

public interface Merchant {
    HashMap<Stuff,Integer> getShops();
    void doInteraction(GameState gameState);
    Inventory getMerchantInventory();
    Position getPosition();
}
