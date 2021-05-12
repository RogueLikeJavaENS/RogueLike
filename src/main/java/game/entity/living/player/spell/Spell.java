package game.entity.living.player.spell;

import game.elements.GameState;
import utils.Direction;
import utils.Position;

public interface Spell {
    String toString();

    int getDamage();

    double getDamageMult();

    int getManaCost();

    Range getRange();

    void setRange(Position entityPos, Direction direction);

    boolean useSpell(GameState gameState);

    boolean isZoning();
}
