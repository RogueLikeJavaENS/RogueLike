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
 * The Ranger shoots a powerful arrow with an unlimited Range. The arrow is so strong nothing can stop it.
 */
public class Sniper extends AbstractSpell {
    public Sniper() {
        super(colorize("Sniper", Attribute.BOLD(), Colors.MAGENTA.textApply()),
                true,
                20,
                5,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.RANGER);
    }

    @Override
    public boolean isZoning() {
        return true;
    }
}
