package entity.object.potion;

import display.GridMap;
import entity.living.Player;
import entity.object.ObjectEntity;
import gameElement.GameState;
import items.AbstractItem;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPotion extends ObjectEntity implements Potion {
    final int potionType;

    public AbstractPotion(Position position, String sprite, int potionType){
        super(position, true);
        this.potionType = potionType;
        List<String> sprites = new ArrayList<>();
        sprites.add(sprite);
        sprites.add(sprite);
        setSprites(sprites);
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
