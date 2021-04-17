package entity.living.npc.monster;

import entity.Entity;
import gameElement.GameState;
import utils.Position;


public interface Monster extends Entity {

    void doAction(GameState gameState);
    MonsterStats getMonsterStats();
    Position getPosition();
    void setPosition(Position position);
    void setAgroPlayer(boolean agroPlayer);
    boolean isAgroPlayer();
    String getName();
}
