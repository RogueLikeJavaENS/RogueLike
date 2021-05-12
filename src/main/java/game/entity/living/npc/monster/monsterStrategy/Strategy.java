package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

public interface Strategy {

    void doAct(Monster monster, Player player, GridMap gridMap);
    boolean act(Monster monster, Player player, GridMap gridMap);
    String getStrategyDescription();
}
