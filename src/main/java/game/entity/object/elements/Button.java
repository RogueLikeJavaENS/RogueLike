package game.entity.object.elements;

import com.diogonunes.jcolor.Attribute;
import game.entity.object.ObjectEntity;
import game.elements.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Button extends ObjectEntity {

    private boolean pressed;

    public Button(Position position) {
        super(position, Colors.RED, false, false);
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

    @Override
    public void doInteraction(GameState gameState) {
        if (!pressed) {
            pressed = true;
            setUpSprites(
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
        else {
            gameState.getDescriptor().updateDescriptor(String.format("%s pressed a button but nothing happened.", gameState.getPlayer().getName()));
        }
    }

    public boolean isPressed() {
        return pressed;
    }

}
