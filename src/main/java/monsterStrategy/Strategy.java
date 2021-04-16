package monsterStrategy;

import display.GridMap;
import entity.living.Player;
import entity.living.monster.Monster;

public interface Strategy {

    void doAct(Monster monster, Player player, GridMap gridMap);
    boolean act(Monster monster, Player player, GridMap gridMap);

}
