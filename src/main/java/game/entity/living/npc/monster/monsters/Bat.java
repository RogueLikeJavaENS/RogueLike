package game.entity.living.npc.monster.monsters;

import game.elements.GameRule;
import game.elements.GameState;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 * This class describe the monster Bat
 * He has some basic statistic
 * @author luca
 */
public class Bat extends AbstractMonster implements Monster {
    /**
     * Create a new Bat
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Bat(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.BROWN, Colors.BROWN, MonsterType.BAT, strategy,
                new MonsterStats(1,1,1,1,1,1,1,level, 1));
        GameRule.setMonstersStats(this, MonsterType.BAT);
        setSprites("\\o/", "   ", getUpColor());
        setBasicSprites("\\o/", "   ");
    }

    @Override
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        if (getStrategy().getStrategyDescription() != null) {
            gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
        }
    }

    @Override
    public boolean isWeak() {
        return true;
    }
}
