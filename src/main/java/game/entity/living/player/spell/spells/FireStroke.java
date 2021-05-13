package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class FireStroke extends AbstractSpell {
    public FireStroke() {
        super(colorize("Fire Stroke", Attribute.BOLD(), Colors.RED.textApply()),
                1.5,
                60,
                new Range(),
                40,
                true,
                5,
                null);
    }
}
