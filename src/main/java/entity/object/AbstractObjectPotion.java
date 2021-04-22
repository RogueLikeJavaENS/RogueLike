package entity.object;

import display.GridMap;
import entity.living.player.Player;
import gameElement.GameState;
import items.potion.PotionFactory;
import items.potion.PotionType;
import utils.Colors;
import utils.Position;

/**
 * This abstract class handle mostly the sprite and the doAction of each potion.
 *
 * @author luca
 */
public abstract class AbstractObjectPotion extends ObjectEntity implements ObjectPotion {
    private final String potionName;
    private final PotionType potionType;

    public AbstractObjectPotion(Position position,String sprite, Colors color, String potionName, PotionType potionType) {
        super(position, color, true);
        this.potionName = potionName;
        this.potionType = potionType;
        setSprites(sprite, sprite, color);
    }

    public void doAction(GameState gameState){
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        PotionFactory potionFactory = new PotionFactory();
        player.pickupPotion(potionFactory.getItemPotion(potionType.ordinal()));
        gameState.getDescriptor().updateDescriptor(String.format("%s picked up a %s",player.getName(),this.potionName));
        gridMap.update(this, false);
    }

    public String getPotionName() { return potionName; }
}
