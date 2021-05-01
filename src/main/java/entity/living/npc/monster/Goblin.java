package entity.living.npc.monster;

import monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 * This class describe the monster Goblin
 * He has some basic statistic
 *
 */
public class Goblin extends AbstractMonster implements Monster {
    private final static int basicHP = 40;
    private final static int basicMP = 20;
    private final static int hpModifier = 4;
    private final static int mpModifier = 2;

    /**
     * Create a new Goblin
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Goblin(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.GREEN, strategy,
                new MonsterStats(basicHP+(hpModifier*level),
                        basicMP+(mpModifier*level),
                        1,
                        (3+level),
                        (3+level),
                        (4+level),
                        level,
                        level,
                        (5*level)));
        setSprites("_o_", "| |", Colors.GREEN);
        setBasicSprites("_o_", "| |");
    }
}
