package game.entity.living.npc.monster.boss;

import display.GridMap;
import game.entity.living.npc.monster.Monster;
import game.entity.living.player.Player;

import java.util.List;

public interface Boss extends Monster {

    boolean hasSpecial();
    void setActedSpecial(boolean moved);
    void doSpecialAction(Player player, GridMap gridmap);
    void updatePosBoss(GridMap gridMap);
    List<BossPart> getBossPartList();
}
