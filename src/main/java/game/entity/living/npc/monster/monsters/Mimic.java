package game.entity.living.npc.monster.monsters;

import game.elements.GameRule;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Mimic extends AbstractMonster {
    /**
     * Create a new Skeleton
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Mimic(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.RED,Colors.BROWN, MonsterType.MIMIC, strategy,
                new MonsterStats(1,1,1,1,1,1,1,level, 1));
        GameRule.setMonstersStats(this, MonsterType.MIMIC);
        setSprites("\\v/","[¤]",getUpColor(),getDownColor());
        setBasicSprites("\\v/", "[¤]");
    }
}
