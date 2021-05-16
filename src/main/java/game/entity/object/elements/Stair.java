package game.entity.object.elements;

import game.entity.object.ObjectEntity;
import game.elements.Dungeon;
import game.elements.GameState;
import game.elements.MiniMap;
import generation.DungeonStructure;
import generation.Seed;
import game.stuff.item.ItemType;
import utils.Colors;
import utils.Position;

/**
 * Stairs are the only way to access a new floor in the Dungeon.
 */
public class Stair extends ObjectEntity {

    public Stair(Position position) {
        super(position,Colors.BROWN, Colors.BROWN,true, false);
        setSprites("  _", "_=|", Colors.BROWN);
    }

    @Override
    public void doAction(GameState gameState){
        takeStair(gameState);
    }

    /**
     * Creates a whole new floor and puts the Player inside.
     *
     * @param gameState GameState needed to create the new floor.
     */
    public void takeStair(GameState gameState) {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, gameState.getDungeon().getFloor()+1, gameState.getPlayer().getClasse());
        Position initialPos = dungeon.getRoom(0).getCenter();
        gameState.setDungeon(dungeon);
        gameState.getPlayer().setPosition(initialPos);
        gameState.updateChangingRoom(dungeon.getRoom(0));
        gameState.setMiniMap(new MiniMap(dungeon, gameState));
        gameState.getDescriptor().updateDescriptor(String.format("%s found the stairs and is now on the floor %d", gameState.getPlayer().getName(), gameState.getDungeon().getFloor()));
    }
}
