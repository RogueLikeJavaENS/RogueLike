package entity.living.monster;

import entity.living.*;
import gameElement.GameState;
import monsterStrategy.Strategy;
import monsterStrategy.StrategyUtils;
import utils.Direction;
import utils.Position;

import java.util.List;

public abstract class AbstractMonster extends NPC implements Monster {
    private Strategy strategy;


    public AbstractMonster(Position position, String name, int level, Strategy strategy, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, stats);
        this.strategy = strategy;
    }

    @Override
    public MonsterStats getMonsterStats() {
        return (MonsterStats) stats;
    }
    public Strategy getStrategy() { return strategy; }

}
