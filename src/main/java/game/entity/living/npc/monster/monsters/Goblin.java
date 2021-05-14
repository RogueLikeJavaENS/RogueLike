package game.entity.living.npc.monster.monsters;

import game.elements.GameRule;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterStats;
import game.elements.GameState;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

/**
 * This class describe the monster Goblin
 * He has some basic statistic
 *
 */
public class Goblin extends AbstractMonster implements Monster {
    /**
     * Create a new Goblin
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Goblin(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.GREEN, MonsterType.GOBLIN, strategy,
                new MonsterStats(1,1,1,1,1,1,1,level, 1));
        GameRule.setMonstersStats(this, MonsterType.GOBLIN);
        setSprites("_o_", "| |", Colors.GREEN);
        setBasicSprites("_o_", "| |");
    }

    @Override
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        if (getStrategy().getStrategyDescription() != null) {
            gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
        }
        getMonsterStats().recoverHp(getMonsterStats().getLevel());
    }
}
