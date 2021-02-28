package entity;

import utils.Position;

public class AbstractMonster extends NPC{

    public AbstractMonster(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
    }
}
