package entity.living.monster;

import entity.living.MonsterStats;
import gameElement.GameState;
import monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;


public class Skeleton extends AbstractMonster {
    private final static int basicHP = 5;
    private final static int basicMP = 1;


    public Skeleton(Position position, String name, int level, Strategy strategy) {
        super(position, name, strategy, new MonsterStats(100, 100, 1, 1, 5, 5, 5, level, 10));
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add("_#_");
        sprites.add("/ \\");
        setBasicSprites(sprites);
        setBasicColor(Colors.WHITE);
        setSprites(sprites);
    }

    @Override
    public String toString() {
        return "+++";
    }

    @Override
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
    }
}