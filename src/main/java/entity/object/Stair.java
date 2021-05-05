package entity.object;

import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import gameElement.MiniMap;
import generation.DungeonStructure;
import generation.RoomFactory;
import generation.Seed;
import stuff.item.ItemType;
import stuff.item.keys.FloorKey;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Stair extends ObjectEntity{

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
        Dungeon dungeon = DungeonStructure.createDungeon(seed, gameState.getDungeon().getFloor()+1);
        Position initialPos = dungeon.getRoom(0).getCenter();
        gameState.setDungeon(dungeon);
        gameState.getPlayer().setPosition(initialPos);
        RoomFactory.addMerchant(gameState, dungeon);
        gameState.updateChangingRoom(dungeon.getRoom(0));
        gameState.setMiniMap(new MiniMap(dungeon, gameState));
        gameState.getPlayer().getInventory().removeItem(ItemType.FLOORKEY);
        gameState.getDescriptor().updateDescriptor(String.format("%s found the stairs and is now on the floor %d", gameState.getPlayer().getName(), gameState.getDungeon().getFloor()));
    }
}
