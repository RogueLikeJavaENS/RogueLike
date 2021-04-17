package entity.object.potion;

import display.GridMap;
import entity.living.player.Player;
import entity.object.ObjectEntity;
import gameElement.GameState;
import utils.Colors;
import utils.Position;
import static com.diogonunes.jcolor.Ansi.colorize;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPotion extends ObjectEntity implements Potion {
    final int potionType;

    public AbstractPotion(Position position, String sprite, Colors color, int potionType){
        super(position,color, true);
        this.potionType = potionType;
        List<String> sprites = new ArrayList<>();
        sprites.add(sprite);
        sprites.add(sprite);
        List<String> colorSprites = new ArrayList<>();
        colorSprites.add(colorize(sprites.get(0),color.textApply()));
        colorSprites.add(colorize(sprites.get(1),color.textApply()));
        setBasicSprites(sprites);
        setSprites(colorSprites);
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
