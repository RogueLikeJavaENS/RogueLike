package monsterStrategy;

import display.GridMap;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;

public class ApproachStrategy extends DecoratorStrategy {
    private Condition condition;

    public ApproachStrategy(Condition condition, Strategy nextStrategy){
        super(nextStrategy);
        this.condition = condition;
    }

    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean canMove = StrategyUtils.getDistance(monster, player) > monster.getMonsterStats().getRangeTotal();
        if (canMove){
            if (!monster.isAgroPlayer()){
                monster.setAgroPlayer(true);
            }
            StrategyUtils.setMonsterPosition(monster, player,gridMap);
            this.updateStrategyDescription(String.format("%s approaches %s",monster.getName(),player.getName()));
        }
        return canMove;
    }

}
