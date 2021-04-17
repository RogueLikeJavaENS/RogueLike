package monsterStrategy;

import display.GridMap;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;

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
        }
        return canMove;
    }

}
