package game.entity.living.player.spell.spells;


import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
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
                true,
                1,
                0,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.WARRIOR);
    }
}
