package game.entity.object.elements;

import game.element.Dungeon;
import game.element.GameState;
import game.element.MiniMap;
import game.entity.object.ObjectEntity;
import generation.DungeonStructure;
import generation.Seed;
import game.stuff.item.ItemType;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Stair extends ObjectEntity {

    public Stair(Position position) {
        super(position,Colors.BROWN, true, false);
        setSprites("  _", "_=|", Colors.BROWN);
    }



    @Override
    public void doAction(GameState gameState){
        takeStair(gameState);
    }

    public void takeStair(GameState gameState) {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, gameState.getDungeon().getFloor()+1, gameState.getPlayer().getClasse());
        Position initialPos = dungeon.getRoom(0).getCenter();
        gameState.setDungeon(dungeon);
        gameState.getPlayer().setPosition(initialPos);
        gameState.updateChangingRoom(dungeon.getRoom(0));
        gameState.setMiniMap(new MiniMap(dungeon, gameState));
        gameState.getPlayer().getInventory().removeItem(ItemType.FLOORKEY);
        gameState.getDescriptor().updateDescriptor(String.format("%s found the stairs and is now on the floor %d", gameState.getPlayer().getName(), gameState.getDungeon().getFloor()));
    }
}
