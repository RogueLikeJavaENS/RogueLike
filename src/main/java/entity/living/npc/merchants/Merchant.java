package entity.living.npc.merchants;

import stuff.Stuff;
import gameElement.GameState;

import java.util.HashMap;

public interface Merchant {
    HashMap<Stuff,Integer> getShops();
    void doInteraction(GameState gameState);
    MerchantInventory getMerchantInventory();
}
