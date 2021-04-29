package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import gameElement.Room;
import stuff.item.ItemType;
import stuff.item.keys.FloorKey;
import utils.Colors;
import utils.Direction;
import utils.Position;
import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class describe a Door
 */
public class Door extends ObjectEntity {
    private final Room nextRoom;
    private final Direction direction;
    private Door next;
    private boolean isOpen;

    /**
     * Create a new Door
     * @param position on the room
     * @param nextRoom room to go when we take the door
     * @param direction which direction is the door
     * @param isOpen boolean
     */
    public Door(Position position, Room nextRoom, Direction direction, boolean isOpen) {
        super(position,Colors.BROWN, isOpen, false);
        this.direction = direction;
        this.nextRoom = nextRoom;
        this.isOpen = isOpen;
        if (this.isOpen){
            setSprites("[ ]", "[ ]", Colors.BROWN);
        }
        else {
            setSprites("[X]", "[X]", Colors.BROWN);
            setIsPlayerAccessible(false);
        }
    }

    /**
     * Set the associate door
     */
    public void setNext(Door next) {
        this.next = next;
    }

    /**
     * Open the door
     */
    private void openDoor(){
        this.isOpen = true;
        this.setIsPlayerAccessible(true);
        setSprites("[ ]", "[ ]", Colors.BROWN);

    }

    /**
     * Open the door and his associate door
     */
    public void openRelyDoor(){
        openDoor();
        this.next.openDoor();
    }

    /**
     * Close the door
     */
    private void closeDoor(){
        this.isOpen = false;
        this.setIsPlayerAccessible(false);
        setSprites("[X]", "[X]", Colors.BROWN);
    }

    /**
     * Close the door and his associate door
     */
    public void closeRelyDoor(){
        closeDoor();
        this.next.closeDoor();
    }


    /**
     * Do the action of a door
     * Take the door and go to the next room if it's open, else do nothing
     * @param gameState gameState of the game
     */
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

    /**
     * Make the interaction with the door
     * If the door is close and the player have a FloorKey it open the door else do nothing
     * @param gameState gameState of the game
     */
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