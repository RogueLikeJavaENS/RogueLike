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
 * The Warrior is turing on himself, dealing damage around him.
 */
public class TourbiLOL extends AbstractSpell {
    public TourbiLOL() {
        super(colorize("TourbiLOL", Attribute.BOLD(), Colors.PINK.textApply()),
                true,
                1,
                2,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.WARRIOR);
    }

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
