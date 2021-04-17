package monsterStrategy;

import display.GridMap;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;

public class AttackStrategy extends DecoratorStrategy{

    public AttackStrategy(Strategy strategy){
        super(strategy);
    }

    @Override
    public boolean act(Monster monster, Player player, GridMap gridMap) {
        int damage = monster.getMonsterStats().getDamageRaw();
        player.getPlayerStats().sufferDamage(damage);
        return true;
    }
}
