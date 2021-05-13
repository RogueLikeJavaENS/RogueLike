package game.entity.living.player.spell;

import game.elements.GameState;
import utils.Direction;
import utils.Position;

public interface Spell {
    String toString();

    int getDamage();

    double getDamageMult();

    int getManaCost();

    Range getRange();

    void setRange(Position entityPos, Direction direction);

    /**
     * Checks if the Player has enough PM to use their selected Spell, and applies its effect if so.
     *
     * @param gameState GameState required to apply the effect of the Spell correctly
     * @return True if the spell worked, false otherwise
     */
    boolean useSpell(GameState gameState);

    /**
     * @return True if the spell affects every entity within its range, false otherwise
     */
    boolean isZoning();

    /**
     * @return True of the spell is a moving spell, false otherwise
     */
    boolean isMovement();

    String getName();
}
