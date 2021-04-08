package entity.living.monster;

import utils.Position;

public class Skeleton extends AbstractMonster {
    private final static int basicHP = 5;
    private final static int basicMP = 1;

    public Skeleton(Position position, String name, int level) {
        super(position, basicHP*level,basicMP*level, name, level);

    }

    @Override
    public String toString() {
        return "+++";
    }
}
