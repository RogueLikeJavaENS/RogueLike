package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

/**
 *
 */
public abstract class DecoratorStrategy implements Strategy{
    private final Strategy nextStrategy;
    private String strategyDescription; // description to give to the gameState

    /**
     * Create a strategy with the first strategy given
     * @param nextStrategy the first strategy
     */
    public DecoratorStrategy(Strategy nextStrategy) {
        this.nextStrategy = nextStrategy;
    }

    /**
     * Chose of the strategy to apply
     * @param monster monster which use the strategy
     * @param player the player
     * @param gridMap the gridMap where is the monster
     */
    public void doAct(Monster monster, Player player, GridMap gridMap){
        if (!act(monster,player,gridMap)){                          // if the monster not used the first strategy because the condition wasn't good
            nextStrategy.doAct(monster, player ,gridMap);           // choose the next strategy and verify if the condition are good with another call to doAct()
            strategyDescription = nextStrategy.getStrategyDescription();  // update in consequence the description
        }
    }

    /**
     *
     * @param monster the monster which act
     * @param player the player
     * @param gridMap the gridMap where is the monster
     * @return true if the monster used the strategy, false instead
     */
    public abstract boolean act(Monster monster, Player player, GridMap gridMap);

    /**
     * Getter of the description of the strategy
     * @return the description
     */
    public String getStrategyDescription() { return strategyDescription; }

    /**
     * Update the description of the strategy
     * @param strategyDescription the new description
     */
    protected void updateStrategyDescription(String strategyDescription){ this.strategyDescription = strategyDescription;}
}
