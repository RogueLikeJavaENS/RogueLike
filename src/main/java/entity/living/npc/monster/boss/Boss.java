package entity.living.npc.monster.boss;

import display.GridMap;
import entity.living.npc.monster.Monster;
import entity.living.player.Player;

import java.util.List;

public interface Boss extends Monster {

    boolean hasMoved();
    void setMoved(boolean moved);
    void doSpecialAction(Player player, GridMap gridmap);
    void updatePosBoss(GridMap gridMap);
    List<BossPart> getBossPartList();
}
