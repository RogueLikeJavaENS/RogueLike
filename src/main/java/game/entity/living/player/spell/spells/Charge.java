package game.entity.living.player.spell.spells;

import display.GridMap;
import game.elements.GameState;
import game.entity.Entity;
import game.entity.living.player.Player;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Position;

import java.util.Collections;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Charge extends AbstractSpell {
    public Charge() {
        super(colorize("Charge", Colors.RED.textApply()),
                0,
                15,
                new Range(),
                25,
                true,
                3,
                (gameState -> {
                    GridMap gridMap = gameState.getGridMap();
                    List<Position> rangeList = gridMap.getRangeList();
                    Collections.reverse(rangeList);
                    for (Position position : rangeList) {
                        List<Entity> entities = gridMap.getEntitiesAt(position.getAbs(), position.getOrd());
                        if (entities.isEmpty()) {
                            chargePlayer(gameState, position);
                            return;
                        }
                        for (Entity entity : entities) {
                            if (entity.getIsPlayerAccessible()) {
                                chargePlayer(gameState, position);
                                return;
                            }
                        }
                    }
                }));
    }

    private static void chargePlayer(GameState gameState, Position position) {
        GridMap gridMap = gameState.getGridMap();
        Player player = gameState.getPlayer();
        gridMap.update(player, false);
        player.setPosition(position);
        gridMap.update(player, true);
    }

    @Override
    public boolean isZoning() {
        return true;
    }
}
