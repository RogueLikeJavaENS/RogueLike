package monsterStrategy;

import entity.living.Player;
import entity.living.monster.Monster;

public class AttackStrategy implements Strategy{

    @Override
    public boolean act(Monster monster, Player player) {
        int damage = monster.getMonsterStats().getRawDamage();
        player.getStats().setLifePoint(player.getStats().getLifePoint()-damage);
        return true;
    }
}
