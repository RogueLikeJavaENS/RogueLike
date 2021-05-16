package game.entity.living.npc.monster;

import game.entity.living.*;
import game.elements.GameState;
import game.entity.living.npc.NPC;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import game.entity.object.elements.Grave;
import utils.Position;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class describe a monster.
 * It contains the strategy and if the monster is aggro the player
 *
 */
public abstract class AbstractMonster extends NPC implements Monster {
    private final Strategy strategy;
    private boolean agroPlayer;
    private final MonsterType monsterType;

    /**
     * Create an abstract monster
     *
     * @param position of the monster
     * @param name name of the monster
     * @param upColor basic color of the top of monster
     * @param downColor basic color of the down of the monster
     * @param strategy strategy of the monster
     * @param stats stats of the monster
     * @throws IllegalArgumentException Position can't be negative
     */
    public AbstractMonster(Position position, String name, Colors upColor, Colors downColor, MonsterType monsterType, Strategy strategy, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, upColor, downColor, stats);
        this.strategy = strategy;
        this.agroPlayer = false;
        this.monsterType = monsterType;
    }

    /**
     * Return if the monster is aggro to the player
     * @return true if the monster is aggro, false if not
     */
    public boolean isAgroPlayer() { return agroPlayer; }


    /**
     * Set the color of the monster in order to show if he is aggro or not
     *
     * @param agroPlayer the new aggro of the monster
     */
    public void setAgroPlayer(boolean agroPlayer) {
        this.agroPlayer = agroPlayer;
    }

    /**
     * Make the monster do his action following his strategy and update the descriptor
     *
     * @param gameState of the game
     */
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        if (getStrategy().getStrategyDescription() != null) {
            gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
        }
    }

    /**
     * Defines what happens when the monster dies.
     * @param gameState overall state of the game
     *
     * @author Raphael
     */
    public void doActionOnDeath(GameState gameState) {
        Grave grave = new Grave(this, gameState);
        gameState.getGridMap().update(grave, true);
    }

    /**
     * Return the Stats of the monster
     * @return the stats
     */
    @Override
    public MonsterStats getMonsterStats() {
        return (MonsterStats) stats;
    }

    /**
     * Return the Strategy of the monster
     * @return the strategy
     */
    public Strategy getStrategy() { return strategy; }

    /**
     * Return the name of the monster
     * @return name
     */
    public String getName(){ return super.getName();}

    public MonsterType getMonsterType() {
        return monsterType;
    }

    @Override
    public boolean isMonster() {
        return true;
    }

    public boolean isBoss() {
        return false;
    }

    @Override
    public boolean isBossPart() {
        return false;
    }

    public boolean isWeak(){
        return false;
    }


}
