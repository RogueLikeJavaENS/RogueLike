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

public class HellWave extends AbstractSpell {

    public HellWave() {
        super(colorize("HellWave", Attribute.BOLD(), Colors.RED.textApply()),
                true,
                1,
                6,
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
        SpellUtils.setTopLeftCornerUlti(range, entityPos, direction);
    }

    @Override
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        SpellUtils.setBottomRightCornerUlti(range, entityPos, direction);
    }
}
