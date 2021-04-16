package entity.living.monster;

import entity.living.MonsterStats;
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
        super(position, name, level,strategy,new MonsterStats(100,100,1,2,5,5,5,1,10));
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize("_o_", Colors.GREEN.textApply()));
        sprites.add(colorize("| |", Colors.GREEN.textApply()));
        setSprites(sprites);
    }


    @Override
    public String toString() {
        return "%%%";
    }

    @Override
    public void doAction(GameState gameState) {
        getStrategy().doAct(this,gameState.getPlayer(),gameState.getGridMap());
    }
}
