package monsterStrategy;

import display.GridMap;
import entity.living.Player;
import entity.living.monster.Monster;

public class MoveAwayStrategy extends DecoratorStrategy{
   public MoveAwayStrategy(Strategy strategy){
       super(strategy);
   }

    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean canMove = StrategyUtils.getDistance(monster,player) > 1;
        if (canMove) {
            if (monster.isAgroPlayer()){
                monster.setAgroPlayer(false);
            }
            StrategyUtils.updatePos(monster, StrategyUtils.moveAroundPlayer(false, monster, player, gridMap));
            this.updateStrategyDescription(String.format("%s was afraid and ran away from %s",monster.getName(), player.getName()));
        }
        return canMove;
    }

}