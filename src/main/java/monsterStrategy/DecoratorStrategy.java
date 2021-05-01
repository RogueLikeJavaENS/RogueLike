package monsterStrategy;

import display.GridMap;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;

public abstract class DecoratorStrategy implements Strategy{
    private final Strategy nextStrategy;
    private String strategyDescription;

    public DecoratorStrategy(Strategy nextStrategy) {
        this.nextStrategy = nextStrategy;
    }

    public void doAct(Monster monster, Player player, GridMap gridMap){
        if (!act(monster,player,gridMap)){
            nextStrategy.doAct(monster, player ,gridMap);
            strategyDescription = nextStrategy.getStrategyDescription();
        }
    }

    public abstract boolean act(Monster monster, Player player, GridMap gridMap);
    protected void updateStrategyDescription(String strategyDescription){ this.strategyDescription = strategyDescription;}

    public String getStrategyDescription() { return strategyDescription; }
}
