package game.entity.living.npc.monster.monsters;

import game.elements.GameState;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

public class Vampire extends AbstractMonster implements Monster {
    private final static int basicHP = 45;
    private final static int basicMP = 15;
    private final static int hpModifier = 2;
    private final static int mpModifier = 1;

    /**
     * Create a new Vampire
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Vampire(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.WHITE, strategy,
                new MonsterStats(basicHP+(hpModifier*level),
                        basicMP+(mpModifier*level),
                        1,
                        2+level,
                        2+level,
                        level,
                        level,
                        level,
                        5*level));
        setSprites("\\o/", "/_\\", Colors.WHITE, Colors.RED);
        setBasicSprites("\\o/", "/_\\");
    }

    @Override
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        if (getStrategy().getStrategyDescription() != null) {
            gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
        }
    }
}
