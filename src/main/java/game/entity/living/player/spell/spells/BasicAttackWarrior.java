package game.entity.living.player.spell.spells;


import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Basic Attack of the Warrior. Uses 0 Mana point. 1 of range and more stronger than the orthers.
 */
public class BasicAttackWarrior extends AbstractSpell {

    public BasicAttackWarrior() {
        super(colorize("Attack", Attribute.BOLD()),
                1.0,
                12,
                new Range(),
                0,
                true,
                1,
                null
        );
    }
}
