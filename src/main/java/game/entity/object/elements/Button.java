package game.entity.object.elements;

import com.diogonunes.jcolor.Attribute;
import game.entity.object.ObjectEntity;
import game.elements.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class describe a Button.
 * The button is used to open the boss door. When all the button are activated the door open.
 */
public class Button extends ObjectEntity {

    private boolean pressed;

    /**
     * Create a button
     *
     * @param position the position of the button
     */
    public Button(Position position) {
        super(position, Colors.RED, false, false);
        // Create the sprite in red at first because it's not pressed
        setUpSprites(
                colorize("#", Colors.DARK_GREY.textApply()) +
                colorize("■", Attribute.BOLD(), Colors.RED.textApply()) +
                colorize("#", Colors.DARK_GREY.textApply())
        );
        setBottomSprites(
                colorize("#", Colors.DARK_GREY.textApply()) +
                colorize("■", Attribute.BOLD(), Colors.RED.textApply()) +
                colorize("#", Colors.DARK_GREY.textApply())
        );
        pressed =false;
    }

    /**
     * Make the action of the interaction with the button
     *
     * @param gameState the state of the game when the button is pressed
     */
    @Override
    public void doInteraction(GameState gameState) {
        if (!pressed) {
            gameState.getMusicStuff().playButtonFX();   // play the music of the button
            pressed = true;
            setUpSprites( // Set the color to green when the button is pressed
                    colorize("#", Colors.DARK_GREY.textApply()) +
                            colorize("■", Attribute.BOLD(), Colors.GREEN.textApply()) +
                            colorize("#", Colors.DARK_GREY.textApply())
            );
            setBottomSprites(
                    colorize("#", Colors.DARK_GREY.textApply()) +
                            colorize("■", Attribute.BOLD(), Colors.GREEN.textApply()) +
                            colorize("#", Colors.DARK_GREY.textApply())
            );
            gameState.getDescriptor().updateDescriptor(String.format("%s pressed a button...", gameState.getPlayer().getName()));
        }
        else { // Already pressed button
            gameState.getDescriptor().updateDescriptor(String.format("%s pressed a button but nothing happened.", gameState.getPlayer().getName()));
        }
    }

    /**
     * Return if the button is pressed
     *
     * @return true if the button is pressed, false instead
     */
    public boolean isPressed() {
        return pressed;
    }

}
