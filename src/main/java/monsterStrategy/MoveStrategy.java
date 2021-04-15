package monsterStrategy;

import entity.living.Player;
import entity.living.monster.Monster;

public abstract class MoveStrategy implements Strategy{
    private Strategy strategy;

    public MoveStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public void strategicAct(Monster monster, Player player){
        if (!act(monster,player)){
            strategy.act(monster,player);
        }
    }

    @Override
    public abstract boolean act(Monster monster, Player player);
}
