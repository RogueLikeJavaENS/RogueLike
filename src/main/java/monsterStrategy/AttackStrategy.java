package monsterStrategy;

import display.GridMap;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;
import utils.Colors;
import static com.diogonunes.jcolor.Ansi.colorize;

public class AttackStrategy extends DecoratorStrategy{

    public AttackStrategy(Strategy strategy){
        super(strategy);
    }

    @Override
    public boolean act(Monster monster, Player player, GridMap gridMap) {
        int damage = monster.getMonsterStats().getDamageRaw();
        player.getPlayerStats().sufferDamage(damage);
        this.updateStrategyDescription(String.format("%s attacked and inflicted %s damages to %s",monster.getName(),colorize(Integer.toString(damage),Colors.RED.textApply()),player.getName()));
        return true;
    }
}
