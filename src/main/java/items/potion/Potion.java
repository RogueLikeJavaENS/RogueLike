package items.potion;

import gameElement.GameState;

public interface Potion {

    int getPotionType();
    String getPotionName();
    boolean usePotion(GameState gs);
}
