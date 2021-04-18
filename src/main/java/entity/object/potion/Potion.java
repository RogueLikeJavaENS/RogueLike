package entity.object.potion;

import entity.Entity;
import gameElement.GameState;

public interface Potion extends Entity {
    void doAction(GameState gameState);
    boolean usePotion(GameState gameState);
    int getPotionType();
    String getPotionName();
}
