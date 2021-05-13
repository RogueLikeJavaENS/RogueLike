package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class BasicAttackMage extends AbstractSpell {
    public BasicAttackMage() {
        super(colorize("Attack", Attribute.BOLD()),
                1.0,
                14,
                new Range(),
                0,
                true,
                2,
                null
        );
    }
}
