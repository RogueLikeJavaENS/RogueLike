package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Simple Fire Ball that hits the first enemies it encounters within a 3 cases range in front of the player.
 */

public class FireBall extends AbstractSpell {

    public FireBall() {
        super(colorize("Fire Ball", Attribute.BOLD(), Colors.RED.textApply()),
                true,
                3,
                2,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.MAGE);
    }

}
