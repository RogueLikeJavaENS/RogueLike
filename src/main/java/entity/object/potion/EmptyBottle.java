package entity.object.potion;

import gameElement.GameState;
import utils.Colors;
import utils.Position;

public class EmptyBottle extends AbstractPotion{

    public EmptyBottle(Position position) {
        super(position,"( )", Colors.GREY, -1, "Empty Bottle");
    }
    public void usePotion(GameState gameState){
        //do nothing
    }
}
