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
 * A basic AOE attack that deals damage to any monster around the player.
 */

public class FireAura extends AbstractSpell {

    public FireAura() {
        super(colorize("Fire Aura", Attribute.BOLD(),Colors.RED.textApply()),
                true,
                1,
                1,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.MAGE);
    }

    @Override
    public boolean isZoning() {
        return true;
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        SpellUtils.setTopLeftCornerArround(range, entityPos, direction);
    }

    @Override
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        SpellUtils.setBottomRightCornerArround(range, entityPos, direction);
    }
}
