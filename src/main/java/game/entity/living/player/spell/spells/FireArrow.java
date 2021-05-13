package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class FireArrow extends AbstractSpell {
    public FireArrow() {
        super(colorize("Fire Arrow", Attribute.BOLD(), Colors.RED.textApply()),
                1.2,
                12,
                new Range(),
                10,
                true,
                5,
                null);
    }

    @Override
    public boolean isZoning() {
        return false;
    }
}
