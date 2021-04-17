package monsterStrategy;

import display.GridMap;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;

public interface Strategy {

    void doAct(Monster monster, Player player, GridMap gridMap);
    boolean act(Monster monster, Player player, GridMap gridMap);
    String getStrategyDescription();
}
