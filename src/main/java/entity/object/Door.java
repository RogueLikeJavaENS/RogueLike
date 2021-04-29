package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import gameElement.Room;
import stuff.item.ItemType;
import stuff.item.keys.FloorKey;
import utils.Colors;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Door extends ObjectEntity {
    private final Room nextRoom;
    private final Direction direction;
    private Door next;
    private boolean isOpen;

    public Door(Position position, Room nextRoom, Direction direction,boolean isOpen) {
        super(position,Colors.BROWN, true);
        this.direction = direction;
        this.nextRoom = nextRoom;
        this.isOpen = isOpen;
        if (this.isOpen){
            setSprites("[ ]", "[ ]", Colors.BROWN);
        }
        else {
            setSprites("[X]", "[X]", Colors.BROWN);
            setIsAccessible(false);
        }
    }

    public void setNext(Door next) {
        this.next = next;
    }

    private void openDoor(){
        this.isOpen = true;
        this.setIsAccessible(true);
        setSprites("[ ]", "[ ]", Colors.BROWN);

    }
    public void openRelyDoor(){
        openDoor();
        this.next.openDoor();
    }
    private void closeDoor(){
        this.isOpen = false;
        this.setIsAccessible(false);
        setSprites("[X]", "[X]", Colors.BROWN);
    }
    public void closeRelyDoor(){
        closeDoor();
        this.next.closeDoor();
    }


    @Override
    public void doAction(GameState gameState) {
        if (isOpen) {
            Player player = gameState.getPlayer();
            int abs = next.getPosition().getAbs();
            int ord = next.getPosition().getOrd();
            switch (direction) {
                case NORTH:
                    player.setPosition(new Position(abs, ord - 1));
                    break;
                case EAST:
                    player.setPosition(new Position(abs + 1, ord));
                    break;
                case SOUTH:
                    player.setPosition(new Position(abs, ord + 1));
                    break;
                case WEST:
                    player.setPosition(new Position(abs - 1, ord));
                    break;
            }
            gameState.updateChangingRoom(nextRoom);
        }
    }

    @Override
    public void doInteraction(GameState gameState) {
        if (isOpen) {
            gameState.getDescriptor().updateDescriptor("The door is open.");
        } else {
            Player player = gameState.getPlayer();
            if (player.getInventory().containsItem(ItemType.FLOORKEY)) {
                player.getInventory().useItem(ItemType.FLOORKEY, gameState);
                player.getInventory().addItem(new FloorKey());
                openRelyDoor();
                gameState.getDescriptor().updateDescriptor("The door is open now.");
            } else {
                gameState.getDescriptor().updateDescriptor("You can't open the door without the key. ");
            }
        }
    }

    @Override
    public String toString() {
        return colorize("[ ]", Colors.BROWN.textApply());
    }
}