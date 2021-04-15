package entity.living.monster;

import utils.Position;

public class Goblin extends AbstractMonster implements Monster {
    private final static int basicHP = 4;
    private final static int basicMP = 2;

    public Goblin(Position position, String name, int level){
        super(position, name, level);

    }

    @Override
    public String toString() {
        return "%%%";
    }
}
