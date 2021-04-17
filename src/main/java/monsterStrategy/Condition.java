package monsterStrategy;

import entity.living.Player;
import entity.living.monster.Monster;

public interface Condition {
    public boolean isVerified(Monster monster, Player player);

}
