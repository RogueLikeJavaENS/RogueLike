package monsterStrategy;

import display.GridMap;
import entity.living.Player;
import entity.living.monster.Monster;

public class ApproachStrategy extends DecoratorStrategy {

    public ApproachStrategy(Strategy nextStrategy){
        super(nextStrategy);
    }

    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean canMove = StrategyUtils.getDistance(monster, player) > 1;
        System.out.println("Distance"+StrategyUtils.getDistance(monster,player));
        if (canMove){
            StrategyUtils.updatePos(monster, StrategyUtils.goToPlayerDir( monster, player,gridMap));
        }
        return canMove;
    }

}
