package entity.object;

import gameElement.Dungeon;
import gameElement.GameState;
import gameElement.MiniMap;
import generation.DungeonStructure;
import generation.Seed;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Stair extends ObjectEntity{
    public Stair(Position position, boolean isAccessible) {
        super(position,Colors.BROWN, isAccessible);
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize("  _",Colors.BROWN.textApply()));
        sprites.add(colorize("_=|",Colors.BROWN.textApply()));
        setSprites(sprites);
    }

    @Override
    public void doAction(GameState gameState) {
        Seed seed = new Seed();
        gameState.setFloor(gameState.getFloor()+1);
        Dungeon dungeon = DungeonStructure.createDungeon(seed, gameState.getFloor());
        Position initialPos = dungeon.getRoom(0).getCenter();
        gameState.setDungeon(dungeon);
        gameState.getPlayer().setPosition(initialPos);
        gameState.updateChangingRoom(dungeon.getRoom(0));
        gameState.setMiniMap(new MiniMap(dungeon, gameState));
        gameState.getDescriptor().updateDescriptor(String.format("%s found a stair and is now on the floor %d", gameState.getPlayer().getName(), gameState.getFloor()));

    }
}
