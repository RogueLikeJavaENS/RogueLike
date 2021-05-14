package game.entity.living.npc.monster.monsters;

import game.elements.GameRule;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 * This class describe the monster Orc
 * He has some basic statistic
 * @author luca
 */

public class Orc extends AbstractMonster {

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
                new MonsterStats(1,1,1,1,1,1,1,level, 1));
        GameRule.setMonstersStats(this, MonsterType.ORC);
        setSprites("\\@/", "[ ]", Colors.RED, Colors.WHITE);
        setBasicSprites("\\@/", "[ ]");
    }
}
