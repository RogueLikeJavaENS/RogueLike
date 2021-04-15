package entity.living.monster;

import entity.living.MonsterStats;
import entity.living.NPC;
import gameElement.GameState;
import utils.Position;

public class AbstractMonster extends NPC implements Monster {

    public AbstractMonster(Position position, String name, int level) throws IllegalArgumentException {
        super(position, name);
        this.stats=new MonsterStats(10,10,1,1,1,1, 0,level, 10);
    }

    @Override
    public void doAction(GameState gameState) {
        System.out.println("my turn ! " + getName());
    }
}
