package game.entity.living.npc.monster.monsters;

import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 * This class describe the monster Orc
 * He has some basic statistic
 * @author luca
 */

public class Orc extends AbstractMonster {
    private final static int basicHP = 60;
    private final static int basicMP = 20;
    private final static int hpModifier = 6;
    private final static int mpModifier = 2;

    /**
     * Create a new Orc
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Orc(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.WHITE, strategy,
                new MonsterStats(basicHP+(hpModifier*level),
                        basicMP+(mpModifier*level),
                        1,
                        1+level,
                        2+level,
                        level,
                        level,
                        level,
                        5*level));
        setSprites("\\@/", "[ ]", Colors.RED, Colors.WHITE);
        setBasicSprites("\\@/", "[ ]");
    }
}
