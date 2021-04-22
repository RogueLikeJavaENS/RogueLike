package entity.object;

import gameElement.GameState;
import utils.Colors;
import utils.Position;

public class EmptyBottle extends AbstractPotion {

    public EmptyBottle(Position position) {
        super(position,"( )", Colors.GREY, -1, "Empty Bottle");
    }
    public boolean usePotion(GameState gameState){
        return false;
    }
}
