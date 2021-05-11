package entity.living.npc.monster;

import entity.Entity;
import gameElement.GameState;
import utils.Position;

/**
 * Interface of the monsters
 */
public interface Monster extends Entity {

    void doAction(GameState gameState);

    String getName();
    MonsterStats getMonsterStats();
    Position getPosition();
    boolean isAgroPlayer();

    void setPosition(Position position);
    void setAgroPlayer(boolean agroPlayer);
    boolean isBoss();
}
