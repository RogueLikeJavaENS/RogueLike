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
 * this class implement the basicAttackMage by extending AbstractSpell
 * its a basic attack with a medium range (2) and bit more damage.
 */
public class  BasicAttackMage extends AbstractSpell {
    public BasicAttackMage() {
        super(colorize("Attack", Attribute.BOLD()),
                true,
                2,
                0,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.MAGE);
    }
}
