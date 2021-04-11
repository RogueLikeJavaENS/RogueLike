package gameElement;

import entity.living.LivingEntity;
import entity.living.Player;
import utils.Colors;
import utils.Position;
import java.util.ArrayList;
import java.util.List;
import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class represent the fight. It sort the fight according to the Entity speed.
 * The Fight is represented as a buffer list. Each turn, the top of the list is popped out
 * and set as the current Entity.
 * @author Antoine
 */
public class Fighting {
    private List<LivingEntity> turnOrder;
    private LivingEntity currentEntity;
    private List<LivingEntity> bufferEntity;
    private int round;

    public Fighting(List<LivingEntity> entities) {
        turnOrder = entities;
        sortTurnOrder();
        bufferEntity = new ArrayList<>(turnOrder);
        currentEntity = bufferEntity.remove(0);
        round = 1;
    }

    /**
     * This methods is used to refill the buffer list if empty.
     */
    public void refillBuffer() {
        bufferEntity.addAll(turnOrder);
    }

    /**
     * This methods set the next entity in the buffers according to the turnOrderList.
     * If the buffers is empty, it refills it.
     * It increments the round.
     */
    public void next() {
        if (bufferEntity.isEmpty()) {
            refillBuffer();
        }
        currentEntity = bufferEntity.remove(0);
        round++;
    }

    /**
     * This methods is used to sort the TurnOrder according to the speed of each entity.
     * If the player has his speed equals with a monster, he plays first.
     */
    private void sortTurnOrder() {
        turnOrder.sort((o1, o2) -> {
            if (o1.getSpeed() == o2.getSpeed()) {   // In case the player is o1 or o2, the player is placed in first.
                if (o1 instanceof Player) {
                    return -1;
                } else if (o2 instanceof Player) {
                    return 1;
                } else return 1;
            } else return o1.getSpeed() - o2.getSpeed();
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Round Fight : ").append(round).append("\n");
        sb.append("#----------------------------------------#\n");
        for(LivingEntity entity : turnOrder) {
            if (entity.equals(currentEntity)) {
                sb.append(colorize(" -> ", Colors.RED.textApply()));
            } else {
                sb.append("\t");
            }
            sb.append(entity.getName());
            sb.append(" ".repeat(15 - entity.getName().length()));
            sb.append("HP: ").append(entity.getCurrentHP()).append("\n");
        }
        sb.append("#----------------------------------------#\n");
        return sb.toString();
    }

    /* GETTERS */
    public LivingEntity getCurrentEntity() {
        return currentEntity;
    }
    public List<LivingEntity> getBufferEntity() {
        return bufferEntity;
    }
    public List<LivingEntity> getTurnOrder() {
        return turnOrder;
    }

    public static void main(String[] args) {
        List<LivingEntity> entities = new ArrayList<>();
        Player p1 = new Player(new Position(1,2),5,5,"p1",1);
        p1.setSpeed(2);
        entities.add(p1);
        Player p2 = new Player(new Position(1,2),5,5,"p2",1);
        p2.setSpeed(2);
        entities.add(p2);
        Player p3 = new Player(new Position(1,2),5,5,"p3",1);
        p3.setSpeed(4);
        entities.add(p3);
        Player p4 = new Player(new Position(1,2),5,5,"p4",1);
        p4.setSpeed(5);
        entities.add(p4);

        Fighting fighting = new Fighting(entities);
    }
}