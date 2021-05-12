package game.entity.living.npc.monster;

import game.entity.living.*;
import game.element.GameState;
import game.entity.living.npc.NPC;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
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

    /**
     * Create an abstract monster
     *
     * @param position of the monster
     * @param name name of the monster
     * @param color basic color of the monster
     * @param strategy strategy of the monster
     * @param stats stats of the monster
     * @throws IllegalArgumentException Position can't be negative
     */
    public AbstractMonster(Position position, String name, Colors color, Strategy strategy, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
        this.strategy = strategy;
        this.agroPlayer = false;
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
        String upSprite = this.getBasicSprites(0);  // Create the sprite with the basic without color
        String downSprite = this.getBasicSprites(1);
        if (agroPlayer){                               // If the monster is aggro, colorize the new sprite in ORANGE
            upSprite = (colorize(upSprite,Colors.ORANGE.textApply()));
            downSprite = (colorize(downSprite,Colors.ORANGE.textApply()));
        }
        else {                                          // If the monsters is not aggro, colorize the sprite in his basic color
            Colors color = this.getBasicColor();
            upSprite = (colorize(upSprite,color.textApply()));
            downSprite = (colorize(downSprite,color.textApply()));
        }
        this.setSprites(upSprite, downSprite, getBasicColor());
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
}
