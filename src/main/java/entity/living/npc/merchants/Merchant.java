package entity.living.npc.merchants;

import entity.object.potion.Potion;
import gameElement.GameState;
import items.Item;

import java.util.HashMap;
import java.util.List;

public interface Merchant {
    HashMap<Potion,Integer> getShops();
    void doInteraction(GameState gameState);
}
