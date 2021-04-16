package entity.object.potion;

import display.GridMap;
import entity.living.Player;
import entity.object.ObjectEntity;
import gameElement.GameState;
import items.AbstractItem;
import utils.Position;

public abstract class AbstractPotion extends ObjectEntity implements Potion {
    protected String sprite;
    final int potionType;

    public AbstractPotion(Position position, String sprite, int potionType){
        super(position, true);
        this.sprite=sprite;
        this.potionType = potionType;
    }

    @Override
    public String toString() {
        return sprite;
    }

    public void doAction(GameState gameState){
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        player.pickupPotion(this);
        gridMap.update(this, false);
    }

    public int getPotionType() {
        return potionType;
    }
}
