package monsterStrategy;

import entity.living.player.Player;
import entity.living.npc.monster.Monster;

public interface Condition {
    public boolean isVerified(Monster monster, Player player);

}
