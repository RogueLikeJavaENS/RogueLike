package game.entity;

import game.elements.GameState;
import utils.Position;

/**
 * This class is the interface of all type of game.entity which can be on a room in the game
 *
 * @author Juliette
 * */

public interface Entity {

    /**
     * Determines what will happen if the Player steps on the Entity.
     *
     * @param gameState GameState needed to create the action.
     */
    void doAction(GameState gameState);

    /**
     * Determines what will happen if the Player interacts with the Entity.
     *
     * @param gameState GameState needed to create the interaction.
     */
    void doInteraction(GameState gameState);
    String toString();
    String getSprites(int i);

    /**
     * Returns the position of the entity.
     *
     * @return the position of the entity
     */
    Position getPosition();

    /**
     * Returns wether NPCs can be on the same position of the Entity or not.
     *
     * @return True is NPCs can access the Entity's position, false otherwise.
     */
    boolean getIsNPCAccessible();

    /***
     * Returns the boolean which describe the accessibility of the entity.
     *
     * @return true or false
     */
    boolean getIsPlayerAccessible();

    /**
     * Determines wether the entity is a Monster or not.
     *
     * @return True if the entity is a Monster, false otherwise. It returns false by default unless the method is overridden.
     */
    boolean isMonster();

    /**
     * Determines wether the entity is a trap or not.
     *
     * @return True if the entity is a trap, false otherwise. It returns false by default unless the method is overridden.
     */
    boolean isTrap();

    /**
     * Determines wether the entity can be destroyed when the Player uses a Spell on it or not.
     * When destroyed, the entity is removed from the game.
     *
     * @return True if the entity can be destroyed, false otherwise. It returns false by default unless the method is overridden.
     */
    boolean isDestroyable();

    /**
     * Determines wether the entity is a part of a Boss or not.
     *
     * @return True if the entity is a part of a Boss, false otherwise. It returns false by default unless the method is overridden.
     */
    boolean isBossPart();
}
