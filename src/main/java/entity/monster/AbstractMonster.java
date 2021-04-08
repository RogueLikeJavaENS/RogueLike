package entity.monster;

import entity.living.NPC;
import entity.monster.Monster;
import utils.Position;

public class AbstractMonster extends NPC implements Monster {

    public AbstractMonster(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
    }
}
