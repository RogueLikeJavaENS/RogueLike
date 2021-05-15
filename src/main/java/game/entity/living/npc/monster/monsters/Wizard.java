package game.entity.living.npc.monster.monsters;

import game.elements.GameRule;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

public class Wizard extends AbstractMonster{

    /**
     * Create a Wizard
     *
     * @param position    of the monster
     * @param name        name of the monster
     * @param strategy    strategy of the monster
     * @throws IllegalArgumentException Position can't be negative
     */
    public Wizard(Position position, String name, int level, Strategy strategy) throws IllegalArgumentException {
        super(position, name, Colors.BROWN, Colors.BLUE, MonsterType.WIZARD, strategy,
                new MonsterStats(1,1,3,1,1,1,1,level,1));
        GameRule.setMonstersStats(this,MonsterType.WIZARD);
        this.setSprites("0 Y","/\\|", getUpColor(),getDownColor());
        setBasicSprites("0 Y","/\\|");
    }

    @Override
    public boolean isWeak() {
        return true;
    }
}
