package monsterStrategy;

import entity.living.Player;
import entity.living.monster.Monster;

public class ApproachStrategy extends MoveStrategy {

    public ApproachStrategy(Strategy strategy){
      super(strategy);
    }
    @Override
    public boolean act(Monster monster, Player player) {
        boolean move = StrategyUtils.getDistance(monster, player) > 1;
        if (move){
            StrategyUtils.updatePos(monster, StrategyUtils.goToPlayerDir(monster, player));
        }
        return move;
    }
}
