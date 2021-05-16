package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpellUtils;
import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Ultimate of the Warrior. Hit the ground and deal damage to every mobs within the range.
 */
public class Earthquake extends AbstractSpell {

    public Earthquake() {
        super(colorize("Earthquake", Attribute.BOLD() , Colors.BROWN.textApply()),
                true,
                1,
                6,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.WARRIOR);
    }

    @Override
    public boolean isZoning() {
        return true;
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        SpellUtils.setTopLeftCornerUlti(range, entityPos, direction);
    }

    @Override
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        SpellUtils.setBottomRightCornerUlti(range, entityPos, direction);
    }
}
