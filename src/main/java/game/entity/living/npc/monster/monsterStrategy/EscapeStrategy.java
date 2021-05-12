package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

public class EscapeStrategy extends MoveAwayStrategy {
    private final Condition condition;

    public EscapeStrategy(Condition condition, Strategy nextStrategy){
        super(nextStrategy);
        this.condition = condition;
    }

    public boolean act(Monster monster, Player player, GridMap gridMap){
        boolean isVerified = condition.isVerified(monster,player);
        return isVerified && super.act(monster, player, gridMap);
    }


}
