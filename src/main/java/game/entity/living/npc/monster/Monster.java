package game.entity.living.npc.monster;

import game.entity.Entity;
import game.elements.GameState;
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

    /**
     * Sets the position of the Monster.
     *
     * @param position the Monster's new Position
     */
    void setPosition(Position position);
    void setAgroPlayer(boolean agroPlayer);
    boolean isBoss();
}
