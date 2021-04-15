package entity.living.monster;

import gameElement.GameState;
import monsterStrategy.ApproachStrategy;
import monsterStrategy.AttackStrategy;
import monsterStrategy.Strategy;
import utils.Position;

public class Skeleton extends AbstractMonster {
    private final static int basicHP = 5;
    private final static int basicMP = 1;
    private static final Strategy skeletonStrategy =
            new ApproachStrategy(
                    new AttackStrategy()
            );

    public Skeleton(Position position, String name, int level) {
        super(position, level*basicHP,level*basicMP,1,1,1,name,level,2);
    }

    @Override
    public String toString() {
        return "+++";
    }

    @Override
    public void doAction(GameState gameState) {
        skeletonStrategy.act(this, gameState.getPlayer());
    }
}
