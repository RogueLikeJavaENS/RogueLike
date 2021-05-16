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
 * The Ranger shoots several powerful arrows in a large zone if front of him.
 */
public class Multishot extends AbstractSpell {

    public Multishot() {
        super(colorize("Multi Shot", Attribute.BOLD(), Colors.GREEN.textApply()),
                true,
                1,
                6,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.RANGER);
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
