package game.entity.living.npc.monster.monsterStrategy;

import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

public interface Condition {
    boolean isVerified(Monster monster, Player player);

}
