package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The Ranger shoots an inflamed arrow with a long Range and greater damages. It hits the first ennemy it meets.
 */
public class FireArrow extends AbstractSpell {
    public FireArrow() {
        super(colorize("Fire Arrow", Attribute.BOLD(), Colors.RED.textApply()),
                true,
                5,
                2,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.RANGER);
    }
}
