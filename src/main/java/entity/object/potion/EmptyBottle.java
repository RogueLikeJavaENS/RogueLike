package entity.object.potion;

import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class EmptyBottle extends AbstractPotion{

    public EmptyBottle(Position position) {
        super(position,"( )", Colors.GREY, -1);
    }
    public void usePotion(GameState gameState){
        //do nothing
    }
}
