package game.elements;

import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.LivingEntity;
import game.entity.living.player.Player;
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
    private List<Integer> initiativeList;
    private int initiativeGauge;

    public Fighting(List<LivingEntity> entities) {
        turnOrder = entities;
        sortTurnOrder();
        initiativeList = new ArrayList<>();
        for (int i = 0; i < turnOrder.size(); i++) {
            initiativeList.add(0);
        }
        initiativeGauge = turnOrder.get(0).getStats().getInitiativeTotal(); //on récupère la plus grande initiative, qui sera la "taille" de la jauge
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
     * This methods is used to sort the TurnOrder according to the speed of each game.entity.
     * If the player has his speed equals with a monster, he plays first.
     */
    private void sortTurnOrder() {
        turnOrder.sort((o1, o2) -> {
            if (o1.getStats().getInitiativeTotal() == o2.getStats().getInitiativeTotal()) {   // In case the player is o1 or o2, the player is placed in first.
                if (o1 instanceof Player) {
                    return -1;
                } else if (o2 instanceof Player) {
                    return 1;
                } else return 1;
            } else return o2.getStats().getInitiativeTotal() - o1.getStats().getInitiativeTotal();
        });
    }

    public void removeMonster(LivingEntity monster) {
        initiativeList.remove(turnOrder.indexOf(monster));
        turnOrder.remove(monster);
        bufferEntity.remove(monster);
        initiativeGauge = turnOrder.get(0).getStats().getInitiativeTotal();
    }

    public boolean canAct(LivingEntity entity) {
        int index = turnOrder.indexOf(entity);
        int newInitiative = initiativeList.get(index) + entity.getStats().getInitiativeTotal();
        boolean enoughInitiative = newInitiative >= initiativeGauge;
        initiativeList.set(index, (initiativeList.get(index) + entity.getStats().getInitiativeTotal())%initiativeGauge);
        return enoughInitiative;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Round Fight : ").append(round).append("\n");
        sb.append("#----------------------------------------#\n");
        for(LivingEntity entity : turnOrder) {
            String initiativeString = String.format("%d/%d", initiativeList.get(turnOrder.indexOf(entity)%initiativeGauge), initiativeGauge);
            if (entity.equals(currentEntity)) {
                sb.append(colorize(" -> ", Colors.RED.textApply()));
            } else {
                sb.append("    ");
            }
            sb.append(entity.getName());
            sb.append(" ".repeat(10 - entity.getName().length()));
            sb.append(initiativeString);
            sb.append(" ".repeat(5 - initiativeString.length()));
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

}