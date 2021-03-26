package gameElement;

import generation.DungeonStructure;
import generation.Seed;
import utils.Direction;
import utils.Position;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class generates randomly and procedurally a Hashmap containing the couple Int number of the Room
 * with the int[] containing its linked neighbor and its a relative position.
 *
 * @author Antoine && Luca
 */
public class GraphDungeon {
    private final HashMap<Integer, int[]> graph;
    private final HashMap<Position, Integer> existingRoom;
    private final Seed seed;
    private final int quantity;
    private final int MAX_BOUND = 5;
    private int acc;
    private boolean created;
    public ArrayList<Direction> probDirection;

    /**
     * This constructor creates a gameElement.GraphDungeon from the seed given in parameter.
     * initialises acc at 0, acc being used to manage the global number of room.
     * initialises created at false, created being used to verify globally if the room we're iterating on
     * has been created or was already.
     * initialises probDirection as an ArrayList containing one of each direction (0,1,2,3), probDirection is the ArrayList
     * used to manage the pseudo-reinforcement learning used to generate the graph.
     * Calls GraphDungeon.attributePath.
     * @param seed used to determine some parameters for the Dungeon.
     * @see Seed
     */

    public GraphDungeon(Seed seed) {
        this.seed = seed;
        this.quantity = DungeonStructure.numberOfRoom(seed);
        this.graph = new HashMap<>();
        this.existingRoom = new HashMap<>();
        this.acc=0;
        created = false;
        probDirection = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            probDirection.add(Direction.intToDirection(i));
        }
        attributePath();
    }

    public int getQuantity() {
        return quantity;
    }

    public HashMap<Integer, int[]> getGraph() {
        return graph;
    }

    private void attributePath() {
        createSourceVertice();
        int compteur = quantity-1;
        ArrayList<Integer> sizeList = new ArrayList<>();
        if (compteur%2 == 0) {
            sizeList.add(compteur/2);
            sizeList.add(compteur/2);
        }else {
            sizeList.add(compteur/2);
            sizeList.add(compteur/2+1);
        }
        for (int size : sizeList) {
            drawPath(size, 0);
            compteur -= size;
        }
    }

    private void drawPath(int size, int source) { //source will change in the future
        int current = source;
        Direction previousDirection = Direction.NONE;

        // 0-N, 1-E, 2-S, 3-O
        while (size>0) {
            Direction direction = calculDirection(current, previousDirection);
            int next = createVertice(current, direction);
            graph.get(next)[direction.oppositeDirection().getValue()] = current;
            graph.get(current)[direction.getValue()] = next;

            if (created) {
                size --;
            }

            previousDirection = direction;
            current=next;
        }
    }

    /**
     * This method chooses a direction for the graph (to add a new room)
     * by reinforcement learning and deleting the impossible pathway.
     * It then increments probDirection with the chosen direction to up
     * the probability to continue in this direction.
     *
     * @param current number of the current room.
     * @param previousDirection last selected direction.
     * @return direction
     */
    public Direction calculDirection(int current,  Direction previousDirection) {
        Direction oppositeDirection = previousDirection.oppositeDirection();
        List<Direction> copyOfProbDirection = List.copyOf(probDirection);
        ArrayList<Direction> voisin = new ArrayList<>();
        if (graph.get(current)[4] != 0) {
            voisin.add(Direction.NORTH);
        }
        if (graph.get(current)[5] < MAX_BOUND) {
            voisin.add(Direction.EAST);
        }
        if (graph.get(current)[4] < MAX_BOUND) {
            voisin.add(Direction.SOUTH);
        }
        if (graph.get(current)[5] != 0) {
            voisin.add(Direction.WEST);
        }

        voisin.remove(oppositeDirection);

        copyOfProbDirection = copyOfProbDirection.stream()
                                .filter(voisin::contains)
                                .collect(Collectors.toList());
        Direction direction = moreProbable(copyOfProbDirection);
        probDirection.add(direction);
        return direction;
    }

    private Direction moreProbable(List<Direction> copyOfProbDirection) {
        int size = copyOfProbDirection.size();
        int index = Integer.valueOf(seed.getSeed().get(size%15), 16)%size;
        return copyOfProbDirection.get(index);
     }

    private void createSourceVertice() {
        int[] sourceVertice = DungeonStructure.initNextlist();
        sourceVertice[4]= Integer.valueOf(seed.getSeed().get(1), 16)%(MAX_BOUND+1);
        sourceVertice[5]= Integer.valueOf(seed.getSeed().get(2), 16)%(MAX_BOUND+1);
        Position sourcePos = new Position(sourceVertice[5], sourceVertice[4]);
        existingRoom.put(sourcePos, 0);
        graph.put(0, sourceVertice);
    }

    private int createVertice(int current, Direction direction) {
        int[] vertice = DungeonStructure.initNextlist();
        switch (direction) {
            case NORTH:
                vertice[4] = graph.get(current)[4]-1;
                vertice[5] = graph.get(current)[5];
                break;
            case EAST:
                vertice[4] = graph.get(current)[4];
                vertice[5] = graph.get(current)[5]+1;
                break;
            case SOUTH:
                vertice[4] = graph.get(current)[4]+1;
                vertice[5] = graph.get(current)[5];
                break;
            case WEST:
                vertice[4] = graph.get(current)[4];
                vertice[5] = graph.get(current)[5]-1;
                break;
        }
        Position testPosition = new Position(vertice[5], vertice[4]);
        boolean exist = existingRoom.containsKey(testPosition);
        int next;
        if(!exist) {
            acc++;
            graph.put(acc, vertice);
            next = acc;
            existingRoom.put(testPosition, acc);
            created = true;
        } else {
            next = existingRoom.get(testPosition);
            created = false;
        }
        return next;
    }

    public static void main(String[] args) {
        GraphDungeon test = new GraphDungeon(new Seed());
        int quantite = test.getQuantity();
        HashMap<Integer, int[]> graphtest = test.getGraph();
        for (int i = 0; i < graphtest.size(); i++) {
            System.out.println(Arrays.toString(graphtest.get(i)) + " " + i);
        }
    }
}