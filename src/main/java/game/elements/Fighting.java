package game.elements;

import game.entity.living.LivingEntity;
import game.entity.living.player.Player;
import utils.Colors;

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
        sortTurnOrder();bufferEntity = new ArrayList<>(turnOrder);
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
     * This methods set the next game.entity in the buffers according to the turnOrderList.
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
     * Remove a monster from the fighting buffer.
     * Update the
     * @param monster The Monster to remove.
     */
    public void removeMonster(LivingEntity monster) {
        turnOrder.remove(monster);
        bufferEntity.remove(monster);
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
                sb.append("    ");
            }
            sb.append(entity.getName());
            sb.append(" ".repeat(15 - entity.getName().length()));
            sb.append("HP: ").append(entity.getStats().getLifePointActual());
            sb.append(" ".repeat(6 - String.valueOf(entity.getStats().getLifePointActual()).length()));
            sb.append("LVL: ").append(entity.getStats().getLevel()).append("\n");
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


    /**
     * This methods is used to sort the TurnOrder according to the speed of each game.entity.
     * If the player has his speed equals with a monster, he plays first.
     */
    private void sortTurnOrder() {
        turnOrder.sort((o1, o2) -> {
            if (o1.getStats().getAgilityTotal() == o2.getStats().getAgilityTotal()) {   // In case the player is o1 or o2, the player is placed in first.
                if (o1 instanceof Player) {
                    return -1;
                } else if (o2 instanceof Player) {
                    return 1;
                } else return 1;
            } else return o2.getStats().getAgilityTotal() - o1.getStats().getAgilityTotal();
        });
    }
}