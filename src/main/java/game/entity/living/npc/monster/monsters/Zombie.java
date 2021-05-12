package game.entity.living.npc.monster.monsters;

import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 *This class describe the monster Skeleton
 *He has statistics and strategy.
 * @author luca
 */
public class Zombie extends AbstractMonster {
    private final static int basicHP = 40;
    private final static int basicMP = 5;
    private final static int hpModifier = 4;
    private final static int mpModifier = 1;

    /**
     * Create a new Zombie
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Zombie(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.GREEN, strategy,
                new MonsterStats(basicHP + (hpModifier * level),
                        basicMP + (mpModifier * level),
                        1,
                        1 + level,
                        2 + level,
                        level,
                        level,
                        level,
                        5 * level));
        setSprites("_#_", "§ §", Colors.GREEN);
        setBasicSprites("_#_", "§ §");
    }
}
