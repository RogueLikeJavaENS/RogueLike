package entity.living.monster;

import entity.Entity;
import entity.living.MonsterStats;
import gameElement.GameState;
import utils.Direction;
import utils.Position;

import java.util.List;

public interface Monster extends Entity {

    void doAction(GameState gameState);
    MonsterStats getMonsterStats();
    Position getPosition();
    void setPosition(Position position);
}
