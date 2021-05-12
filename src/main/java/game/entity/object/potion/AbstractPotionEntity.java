package game.entity.object.potion;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.object.ObjectEntity;
import game.elements.GameState;
import game.stuff.item.ItemFactory;
import game.stuff.item.ItemType;
import utils.Colors;
import utils.Position;
import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This abstract class handle mostly the sprite and the doAction of each potion.
 *
 * @author luca
 */
public abstract class AbstractPotionEntity extends ObjectEntity implements PotionEntity {
    private final String potionName;
    private final ItemType itemType;

    public AbstractPotionEntity(Position position, String sprite, Colors color, String potionName, ItemType itemType) {
        super(position, color, true,true);
        this.potionName = potionName;
        this.itemType = itemType;
        setSprites(sprite, sprite, color);
    }

    public void doAction(GameState gameState){
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        ItemFactory itemFactory = new ItemFactory();
        player.getInventory().addItem(itemFactory.getItem(itemType, player.getPlayerStats().getLevel()));
        gameState.getDescriptor().updateDescriptor(String.format("%s picked up a %s !",player.getName(),(colorize(this.potionName, this.getBasicColor().textApply()))));
        gridMap.update(this, false);
    }

    public String getPotionName() { return potionName; }
}
