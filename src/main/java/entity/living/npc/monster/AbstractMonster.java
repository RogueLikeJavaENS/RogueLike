package entity.living.npc.monster;

import entity.living.*;
import gameElement.GameState;
import entity.living.npc.NPC;
import monsterStrategy.Strategy;
import utils.Position;
import utils.Colors;
import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class describe a monster.
 * It contains the strategy and if the monster is aggro the player
 *
 */
public abstract class AbstractMonster extends NPC implements Monster {
    private Strategy strategy;
    private boolean agroPlayer;

    /**
     * Create an abstract monster
     *
     * @param position of the monster
     * @param name name of the monster
     * @param color basic color of the monster
     * @param strategy strategy of the monster
     * @param stats stats of the monster
     * @throws IllegalArgumentException
     */
    public AbstractMonster(Position position, String name, Colors color, Strategy strategy, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
        this.strategy = strategy;
        this.agroPlayer = false;
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
     * Return if the monster is aggro to the player
     * @return true if the monster is aggro, false if not
     */
    public boolean isAgroPlayer() { return agroPlayer; }

    /**
     * Return the name of the monster
     * @return name
     */
    public String getName(){ return super.getName();}


    /**
     * Set the color of the monster in order to show if he is aggro or not
     *
     * @param agroPlayer the new aggro of the monster
     */
    public void setAgroPlayer(boolean agroPlayer) {
        this.agroPlayer = agroPlayer;
        String upSprite = this.getBasicSprites(0);  // Create the sprite with the basic without color
        String downSprite = this.getBasicSprites(1);
        List<String> newSprite = new ArrayList<>(2);
        if (agroPlayer){                               // If the monster is aggro, colorize the new sprite in ORANGE
            newSprite.add(colorize(upSprite,Colors.ORANGE.textApply()));
            newSprite.add(colorize(downSprite,Colors.ORANGE.textApply()));
        }
        else {                                          // If the monsters is not aggro, colorize the sprite in his basic color
            Colors color = this.getBasicColor();
            newSprite.add(colorize(upSprite,color.textApply()));
            newSprite.add(colorize(downSprite,color.textApply()));
        }
        this.setSprites(newSprite);
    }

    /**
     * Make the monster do his action following his strategy and update the descriptor
     *
     * @param gameState of the game
     */
    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
    }
}
