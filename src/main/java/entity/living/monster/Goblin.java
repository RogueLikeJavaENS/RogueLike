package entity.living.monster;

import utils.Colors;
import utils.Position;

import java.util.ArrayList;
import static com.diogonunes.jcolor.Ansi.colorize;

public class Goblin extends AbstractMonster implements Monster {
    private final static int basicHP = 4;
    private final static int basicMP = 2;


    public Goblin(Position position, int level){
        super(position, basicHP*level,basicMP*level, "Goblin", level);
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize("_o_", Colors.GREEN.textApply()));
        sprites.add(colorize("| |", Colors.GREEN.textApply()));
        setSprites(sprites);
    }

    @Override
    public String toString() {
        return "%%%";
    }
}
