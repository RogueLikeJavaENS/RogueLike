package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Basic Ranger's attack. The Ranger shoots a simple arrow which hits the first ennemy it meets.
 * It has a long Range but lower damages.
 */
public class BasicAttackRanger extends AbstractSpell {
    public BasicAttackRanger() {
        super(colorize("Attack", Attribute.BOLD()),
                true,
                3,
                0,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.RANGER);
    }
}
