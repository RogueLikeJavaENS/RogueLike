package entity.object;

import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import gameElement.MiniMap;
import generation.DungeonStructure;
import generation.Seed;
import stuff.item.keys.FloorKey;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Stair extends ObjectEntity{
    private boolean open;

    public Stair(Position position, boolean isAccessible) {
        super(position,Colors.BROWN, isAccessible);
        open = false;
        setSprites("  _", "_=|", Colors.BROWN);
    }



    @Override
    public void doInteraction(GameState gameState){
        if (open){
            takeStair(gameState);
        }
        else {
            Player player = gameState.getPlayer();
            if (/*verifier la presence de la FloorKey*/){
                /*Enlever la FloorKey de l'inventaire du joueur*/
                gameState.getDescriptor().updateDescriptor("The stair are open, you can go to the next floor whenever you want\n");
            }
            else{
                gameState.getDescriptor().updateDescriptor("You don't have the key, you can't go upward.");
            }
        }
    }

    public void takeStair(GameState gameState) {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, gameState.getDungeon().getFloor()+1);
        Position initialPos = dungeon.getRoom(0).getCenter();
        gameState.setDungeon(dungeon);
        gameState.getPlayer().setPosition(initialPos);
        gameState.updateChangingRoom(dungeon.getRoom(0));
        gameState.setMiniMap(new MiniMap(dungeon, gameState));
        gameState.getDescriptor().updateDescriptor(String.format("%s found the stairs and is now on the floor %d", gameState.getPlayer().getName(), gameState.getDungeon().getFloor()));

    }
}
