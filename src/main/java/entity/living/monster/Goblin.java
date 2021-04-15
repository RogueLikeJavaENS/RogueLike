package entity.living.monster;

import gameElement.GameState;
import monsterStrategy.ApproachStrategy;
import monsterStrategy.AttackStrategy;
import monsterStrategy.EscapeStrategy;
import monsterStrategy.Strategy;
import utils.Position;

public class Goblin extends AbstractMonster implements Monster {
    private final static int basicHP = 4;
    private final static int basicMP = 2;

    public Goblin(Position position, String name, int level){
        super(position, name, level);




    private final Strategy goblinStrategy =
            new EscapeStrategy((this.getMonsterStats().getLifePoint() < this.getMonsterStats().getLifePoint()+5), new ApproachStrategy( new AttackStrategy()));


    @Override
    public String toString() {
        return "%%%";
    }

    @Override
    public void doAction(GameState gameState) {
        goblinStrategy.act(this,gameState.getPlayer());
    }
}
