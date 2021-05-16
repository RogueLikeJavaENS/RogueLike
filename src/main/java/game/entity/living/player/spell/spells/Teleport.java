package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import display.GridMap;
import game.elements.GameRule;
import game.elements.GameState;
import game.entity.Entity;
import game.entity.living.player.Player;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Direction;
import utils.Position;

import java.util.Collections;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * this class implement the Teleport by extending AbstractSpell
 * it's a movement spell with a good range (4) that avoid traps.
 */
public class Teleport extends AbstractSpell {

    public Teleport() {
        super(colorize("Teleport", Attribute.BOLD(), Colors.BLUE.textApply()),
                false,
                4,
                3,
                (gameState -> {
                    GridMap gridMap = gameState.getGridMap();
                    List<Position> rangeList = gridMap.getRangeList();
                    Collections.reverse(rangeList);
                    for (Position position : rangeList) {
                        List<Entity> entities = gridMap.getEntitiesAt(position.getAbs(), position.getOrd());
                        if (entities.isEmpty()) {
                            teleportPlayer(gameState, position);
                            return true;
                        }
                        for (Entity entity : entities) {
                            if (entity.getIsPlayerAccessible()) {
                                teleportPlayer(gameState, position);
                                return true;
                            }
                        }
                    }
                    return false;
                })
        );
        GameRule.setSpellStats(this, InGameClasses.MAGE);
    }

    /*change the position of the player without him moving on the map, take a Gamestate to know the state of the game,
    and the new position we want to put him at. */
    private static void teleportPlayer(GameState gameState, Position position) {
        GridMap gridMap = gameState.getGridMap();
        Player player = gameState.getPlayer();
        gridMap.update(player, false);
        player.setPosition(position);
        gridMap.update(player, true);
        gameState.getDescriptor().updateDescriptor(
                String.format("%s used "+
                        colorize("Teleport", Attribute.BOLD(), Colors.BLUE.textApply())+
                        " for "+
                        colorize(String.format("%d MP.", 20), Attribute.BOLD(), Colors.BLUE.textApply()), player.getName()));
    }

    @Override
    public boolean isZoning() {
        return true;
    }

    @Override
    public boolean isMovement() {
        return true;
    }
}
