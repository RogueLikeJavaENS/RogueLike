package entity.object;

import entity.living.Player;
import gameElement.GameState;
import gameElement.Room;
import utils.Direction;
import utils.Position;

public class Door extends ObjectEntity {
    private final Room nextRoom;
    private final Direction direction;
    private Door next;

    public Door(Position position, Room nextRoom, Direction direction) {
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
            case NORTH:
                player.setPosition(new Position(abs, ord-1));
                break;
            case EAST:
                player.setPosition(new Position(abs+1, ord));
                break;
            case SOUTH:
                player.setPosition(new Position(abs, ord+1));
                break;
            case WEST:
                player.setPosition(new Position(abs-1, ord));
                break;
        }
        gameState.updateChangingRoom(nextRoom);
    }

    @Override
    public String toString() {
        return "[]";
    }
}
