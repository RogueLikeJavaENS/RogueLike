package entity.living.npc.monster;

import gameElement.GameState;
import monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;
import static com.diogonunes.jcolor.Ansi.colorize;

public class Goblin extends AbstractMonster implements Monster {
    private final static int basicHP = 4;
    private final static int basicMP = 2;

    public Goblin(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.GREEN, strategy,new MonsterStats(100,100,1,2,5,5,5,level,10));
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add("_o_");
        sprites.add("| |");
        setBasicSprites(sprites);
        ArrayList<String> colorSprites = new ArrayList<>();
        colorSprites.add(colorize(sprites.get(0),getBasicColor().textApply()));
        colorSprites.add(colorize(sprites.get(1),getBasicColor().textApply()));
        setSprites(colorSprites);
    }


    @Override
    public String toString() {
        return "%%%";
    }

}
