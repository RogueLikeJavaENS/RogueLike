package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;
import utils.Colors;
import static com.diogonunes.jcolor.Ansi.colorize;

public class AttackStrategy extends DecoratorStrategy{

    public AttackStrategy(Strategy strategy){
        super(strategy);
    }

    public boolean act(Monster monster, Player player, GridMap gridMap) {
        if (player.getPlayerStats().hasAvoided()){
            this.updateStrategyDescription(String.format("%s dodged %s's attack!", player.getName(), monster.getName()));
        } else {
            int damage = 2 * monster.getMonsterStats().getDamageNatural() + 5 * monster.getMonsterStats().getLevel();
            damage = player.getPlayerStats().sufferDamage(damage);
            this.updateStrategyDescription(String.format("%s attacked and inflicted %s damages to %s", monster.getName(), colorize(Integer.toString(damage), Colors.RED.textApply()), player.getName()));
        }
        return true;
    }
}
