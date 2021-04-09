import entity.Entity;
import entity.living.LivingEntity;
import entity.living.Player;
import utils.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Fighting {
    private List<LivingEntity> turnOrder;
    private Entity currentEntity;
    private List<LivingEntity> bufferEntity;


    public Fighting(List<LivingEntity> entities) {
        turnOrder = entities;
        sortTurnOrder();
        bufferEntity = new ArrayList<>(turnOrder);
        currentEntity = bufferEntity.remove(0);
    }

    private void sortTurnOrder() {
        turnOrder.sort(Comparator.comparingInt(LivingEntity::getSpeed).reversed());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(LivingEntity entity : turnOrder) {
            sb.append(entity.getName());
            sb.append("HP: ");
            sb.append(entity.getCurrentHP());
            if (entity.equals(currentEntity)) {
                sb.append(" <-");
            }
            sb.append("\n");
        }
        return sb.toString();
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