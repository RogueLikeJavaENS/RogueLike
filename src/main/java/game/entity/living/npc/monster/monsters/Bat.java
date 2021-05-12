package game.entity.living.npc.monster.monsters;

import game.elements.GameState;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 * This class describe the monster Bat
 * He has some basic statistic
 *
 */
public class Bat extends AbstractMonster implements Monster {
    private final static int basicHP = 20;
    private final static int basicMP = 10;
    private final static int hpModifier = 2;
    private final static int mpModifier = 1;

    /**
     * Create a new Bat
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Bat(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.BROWN, strategy,
                new MonsterStats(basicHP+(hpModifier*level),
                        basicMP+(mpModifier*level),
                        1,
                        3+level,
                        1+(level/2),
                        level,
                        level,
                        level,
                        5*level));
        setSprites("\\o/", "   ", Colors.BROWN);
        setBasicSprites("\\o/", "   ");
    }

    @Override
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        if (getStrategy().getStrategyDescription() != null) {
            gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
        }
    }
}
