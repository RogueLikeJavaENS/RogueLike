package monsterStrategy;

import entity.living.Player;
import entity.living.monster.Monster;

public class EscapeStrategy implements Strategy{

    public EscapeStrategy(boolean condition, Strategy nextStrategy){

    }

    @Override
    public boolean act(Monster monster, Player player) {
        boolean move = StrategyUtils.getDistance(monster,player) > 1;
        if (move) {
            StrategyUtils.updatePos(monster, StrategyUtils.goToPlayerDir(monster,player).oppositeDirection());
        }
        return move;
    }
}
