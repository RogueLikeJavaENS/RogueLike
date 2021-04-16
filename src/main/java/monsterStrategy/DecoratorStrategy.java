package monsterStrategy;

import display.GridMap;
import entity.living.Player;
import entity.living.monster.Monster;

public abstract class DecoratorStrategy implements Strategy{
    private final Strategy strategy;

    public DecoratorStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public  void doAct(Monster monster, Player player, GridMap gridMap){
        if (!act(monster,player,gridMap)){
            strategy.act(monster, player ,gridMap);
        }
    }

    public abstract boolean act(Monster monster, Player player, GridMap gridMap);
}
