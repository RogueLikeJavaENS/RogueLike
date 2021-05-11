package monsterStrategy;

import display.GridMap;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;

public class IdleStrategy extends DecoratorStrategy {
    Condition condition;

    public IdleStrategy(Condition condition, Strategy nextStrategy){
        super(nextStrategy);
        this.condition = condition;
    }

    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean canMove = condition.isVerified(monster,player);
        if (canMove) {
            if (monster.isAgroPlayer()){
                monster.setAgroPlayer(false);
            }
            updateStrategyDescription(null);
            StrategyUtils.moveRandomly(monster, gridMap);
        }
        return canMove;
    }

}
