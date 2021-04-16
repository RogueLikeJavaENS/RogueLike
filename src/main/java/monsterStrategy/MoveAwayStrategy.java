package monsterStrategy;

import display.GridMap;
import entity.living.Player;
import entity.living.monster.Monster;

public class MoveAwayStrategy extends DecoratorStrategy{

   public MoveAwayStrategy(Strategy strategy){
       super(strategy);
   }

    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean move = StrategyUtils.getDistance(monster,player) > 1;
        if (move) {
            StrategyUtils.updatePos(monster, StrategyUtils.escapePlayerDir(monster, player, gridMap));
        }
        return move;
    }

}
