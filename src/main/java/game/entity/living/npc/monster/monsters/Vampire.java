package game.entity.living.npc.monster.monsters;

import game.elements.GameRule;
import game.elements.GameState;
import game.entity.living.npc.monster.*;
import game.entity.living.npc.monster.monsterStrategy.ApproachStrategy;
import game.entity.living.npc.monster.monsterStrategy.AttackStrategy;
import game.entity.living.npc.monster.monsterStrategy.IdleStrategy;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;
import utils.State;

public class Vampire extends AbstractMonster implements Monster {

    /**
     * Create a new Vampire
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public Vampire(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.WHITE,Colors.RED, MonsterType.VAMPIRE, strategy,
                new MonsterStats(1,1,1,1,1,1,1,level, 1));
        GameRule.setMonstersStats(this, MonsterType.VAMPIRE);
        setSprites("\\o/", "/_\\", getUpColor(), getDownColor());
        setBasicSprites("\\o/", "/_\\");
    }

    @Override
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        if (getStrategy().getStrategyDescription() != null) {
            gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
        }
    }

    @Override
    public void doActionOnDeath(GameState gameState) {
        MonsterFactory monsterFactory = new MonsterFactory(gameState.getDungeon().getFloor());
        Monster bat = monsterFactory.getMonster(2, getPosition());
        gameState.getGridMap().update(bat, true);
        gameState.setState(State.NORMAL);
        gameState.changeRoomFight();
    }
}
