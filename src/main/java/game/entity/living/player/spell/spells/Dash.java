package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import display.GridMap;
import game.elements.GameRule;
import game.entity.Entity;
import game.entity.living.player.Player;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The Ranger dashes 3 cases further (or less if there is something in the way).
 */
public class Dash extends AbstractSpell {
    public Dash() {
        super(colorize("Dash",Attribute.BOLD() ,Colors.GREEN.textApply()),
                false,
                3,
                3,
                (gameState -> {
                    GridMap gridMap = gameState.getGridMap();
                    List<Position> rangeList = gridMap.getRangeList();
                    List<Position> rangeBeforeEntity = new ArrayList<>();

                    for(Position position : rangeList) {
                        List<Entity> entities = gridMap.getEntitiesAt(position.getAbs(), position.getOrd());
                        if (entities.isEmpty()) {
                            rangeBeforeEntity.add(position);
                        } else {
                            boolean isAccessible = true;
                            for (Entity entity : entities) {
                                if (!entity.getIsPlayerAccessible()) {
                                    isAccessible = false;
                                    break;
                                }
                            }
                            if (isAccessible) {
                                rangeBeforeEntity.add(position);
                            }
                        }
                    }
                    Player player = gameState.getPlayer();
                    if (!rangeBeforeEntity.isEmpty()) {
                        Position position = rangeBeforeEntity.get(rangeBeforeEntity.size()-1);
                        gridMap.update(player, false);
                        player.setPosition(position);
                        gridMap.update(player, true);
                    }
                    gameState.getDescriptor().updateDescriptor(
                            String.format("%s used "+
                                    colorize("Dash", Attribute.BOLD(), Colors.GREEN.textApply())+
                                    " for "+
                                    colorize(String.format("%d MP.", 20), Attribute.BOLD(), Colors.BLUE.textApply()), player.getName()));
                    return true;
                })
        );
        GameRule.setSpellStats(this, InGameClasses.RANGER);
    }

    @Override
    public boolean isMovement() {
        return true;
    }
}
