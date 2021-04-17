package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import gameElement.Room;
import utils.Colors;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Door extends ObjectEntity {
    private final Room nextRoom;
    private final Direction direction;
    private Door next;

    public Door(Position position, Room nextRoom, Direction direction) {
        super(position,Colors.BROWN, true);
        this.direction = direction;
        this.nextRoom = nextRoom;
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize("[ ]",Colors.BROWN.textApply()));
        sprites.add(colorize("[ ]",Colors.BROWN.textApply()));
        setSprites(sprites);
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
        return colorize("[ ]", Colors.BROWN.textApply());
    }
}