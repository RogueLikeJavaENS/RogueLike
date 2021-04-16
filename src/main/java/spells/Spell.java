package spells;

import utils.Direction;
import utils.Position;

public interface Spell {
    String toString();

    double getDamageMult();

    int getManaCost();

    Range getRange();

    void setRange(Position entityPos, Direction direction);
}
