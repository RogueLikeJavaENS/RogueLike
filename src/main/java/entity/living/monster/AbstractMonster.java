package entity.living.monster;

import entity.living.NPC;
import gameElement.GameState;
import utils.Position;

public class AbstractMonster extends NPC implements Monster {

    public AbstractMonster(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
    }

    @Override
    public void doAction(GameState gameState) {
        System.out.println("my turn ! " + getName());
    }
}
