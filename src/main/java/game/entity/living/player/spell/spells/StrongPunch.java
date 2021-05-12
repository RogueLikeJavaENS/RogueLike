package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;

public class StrongPunch extends AbstractSpell {


    public StrongPunch() {
        super("Strong Punch",
                1.2,
                15,
                new Range(),
                5,
                true,
                1,
                null);
    }
}
