package game.stuff.item;

import game.elements.GameState;
import game.elements.Room;

import java.util.List;

public class MapDungeon extends AbstractItem{

    public MapDungeon() {
        super("Map of the Floor", ItemType.DUNGEON_MAP);
    }

    @Override
    public boolean useItem(GameState gameState) {
        for (Room room : gameState.getDungeon().getRoomList()) {
            room.setWasVisited(true);
        }
        return true;
    }

    @Override
    public boolean isUsable() {
        return true;
    }
}
