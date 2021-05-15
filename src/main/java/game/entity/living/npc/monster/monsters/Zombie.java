package game.entity.living.npc.monster.monsters;

import game.elements.GameRule;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 *This class describe the monster Skeleton
 *He has statistics and strategy.
 * @author luca
 */
public class Zombie extends AbstractMonster {

    /**
     * Create a new Zombie
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Zombie(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.GREEN, Colors.GREEN, MonsterType.ZOMBIE, strategy,
                new MonsterStats(1,1,1,1,1,1,1,level, 1));
        GameRule.setMonstersStats(this, MonsterType.ZOMBIE);
        setSprites("_#_", "§ §", getUpColor());
        setBasicSprites("_#_", "§ §");
    }
}
