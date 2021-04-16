package entity.living.monster;

import entity.living.MonsterStats;
import gameElement.GameState;
import monsterStrategy.Strategy;
import utils.Position;


public class Goblin extends AbstractMonster implements Monster {
    private final static int basicHP = 4;
    private final static int basicMP = 2;

    public Goblin(Position position, String name, int level, Strategy strategy) {
        super(position, name, level,strategy,new MonsterStats(100,100,1,2,5,5,5,1,10));
        getStats().setLifePointActual(49);
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
