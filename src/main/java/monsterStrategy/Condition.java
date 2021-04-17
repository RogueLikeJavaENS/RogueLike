package monsterStrategy;

import entity.living.player.Player;
import entity.living.npc.monster.Monster;

public interface Condition {
    boolean isVerified(Monster monster, Player player);

}
