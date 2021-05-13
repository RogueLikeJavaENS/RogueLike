package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import display.GridMap;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.object.traps.RangerTrap;
import utils.Colors;
import utils.Position;

import java.util.List;
import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The Ranger sets a Trap in front of him if he can, so the monsters stepping on it will suffer damages.
 */
public class Trap extends AbstractSpell {
    public Trap() {
        super(colorize("Trap", Attribute.BOLD(), Colors.GREEN.textApply()),
                0.0,
                0,
                new Range(),
                15,
                false,
                1,
                gameState -> {
                    GridMap gridmap = gameState.getGridMap();
                    List<Position> posToPlace = gridmap.getRangeList();
                    for (Position position : posToPlace) {
                        if (gridmap.getEntitiesAt(position.getAbs(), position.getOrd()).size() == 0) {
                            RangerTrap trapToPlace = new RangerTrap(position, Colors.GREY, true, true);
                            gridmap.update(trapToPlace, true);
                            gameState.getDescriptor().updateDescriptor(String.format("%s place a %s.", gameState.getPlayer().getName(), colorize("trap", Colors.ORANGE.textApply())));
                            return true;
                        }
                    }
                    return false;
                });
    }
}
