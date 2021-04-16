package entity.living.monster;

import entity.living.*;
import monsterStrategy.Strategy;
import utils.Position;


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
