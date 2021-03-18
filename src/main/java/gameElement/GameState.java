package gameElement;

import entity.Player;
import utils.Position;

public class GameState {
    /*
     states :
        0 -> Start
        1 -> Game
        2 -> Menu
        3 -> MiniMap
    */
    private int state;
    private Room currentRoom;
    private final Player player;
    private final Dungeon dungeon;
    private final MiniMap miniMap;

    public GameState(Player player, Dungeon dungeon, MiniMap miniMap) {
        this.dungeon = dungeon;
        this.player = player;
        this.miniMap = miniMap;
        this.currentRoom = dungeon.roomList.get(0);
        player.setPosition(currentRoom.getCenter());
        state = 0;
    }

    public int getState() {
        return state;
    }

}
