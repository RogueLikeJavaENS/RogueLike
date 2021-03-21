package entity.object;

import display.GridMap;
import entity.living.Player;
import gameElement.GameState;
import gameElement.Room;
import utils.Position;

public class Door extends ObjectEntity {
    private Room nextRoom;
    private int direction;
    private Door next;

    public Door(Position position, Room nextRoom, int direction) {
        super(position, true);
        this.direction = direction;
        this.nextRoom = nextRoom;
    }

    public void setNext(Door next) {
        this.next = next;
    }

    @Override
    public void doAction(GameState gameState) {
        Player player = gameState.getPlayer();
        int abs = next.getPosition().getAbs();
        int ord = next.getPosition().getOrd();
        switch (direction) {
            case 0: // North
                player.setPosition(new Position(abs, ord-1));
                break;
            case 1: // East
                player.setPosition(new Position(abs+1, ord));
                break;
            case 2: // South
                player.setPosition(new Position(abs, ord+1));
                break;
            case 3: // West
                player.setPosition(new Position(abs-1, ord));
                break;
        }
        gameState.update(nextRoom);
    }

    @Override
    public String toString() {
        return "[]";
    }
}
