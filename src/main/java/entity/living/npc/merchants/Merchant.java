package entity.living.npc.merchants;

import entity.object.potion.Potion;
import gameElement.GameState;

import java.util.HashMap;

public interface Merchant {
    HashMap<Potion,Integer> getShops();
    void doInteraction(GameState gameState);
}
