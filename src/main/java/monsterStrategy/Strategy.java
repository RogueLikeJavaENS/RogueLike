package monsterStrategy;

import entity.living.Player;
import entity.living.monster.Monster;

public interface Strategy {

    boolean act(Monster monster, Player player);
}
