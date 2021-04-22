package entity.living.npc.merchants;

import items.Item;
import gameElement.GameState;

import java.util.HashMap;

public interface Merchant {
    HashMap<Item,Integer> getShops();
    void doInteraction(GameState gameState);
}
