package gameElement;

import generation.DungeonStructure;
import generation.Seed;
import utils.Position;

import javax.xml.transform.Source;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class generates randomly and procedurally a Hashmap containing the couple Int number of the Room
 * with the int[] containing its linked neighbor and its a relative position.
 *
 * @author Antoine && Luca
 */
public class GraphDungeon {
    private HashMap<Integer, int[]> graph;
    private HashMap<Position, Integer> existingRoom;
    private final Seed seed;
    private int quantity;
    private final int MAX_BOUND = 5;
    private int acc;
    private boolean created;
    public ArrayList<Integer> probDirection;

    /**
     * This constructor creates a gameElement.GraphDungeon from the seed given in parameter.
     * initialises acc at 0, acc being used to manage the global number of room.
     * initialises created at false, created being used to verify globally if the room we're iterating on
     * has been created or was already.
     * initialises probDirection as an ArrayList containing one of each direction (0,1,2,3), probDirection is the ArrayList
     * used to manage the pseudo-reinforcement learning used to generate the graph.
     * Calls GraphDungeon.attributePath.
     * @param seed
     * @see Seed
     */

    public GraphDungeon(Seed seed) {
        this.seed = seed;
        this.quantity = DungeonStructure.numberOfRoom(seed);
        this.graph = new HashMap<Integer,int[]>();
        this.existingRoom = new HashMap<Position, Integer>();
        this.acc=0;
        created = false;
        probDirection = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            probDirection.add(i);
        }
        attributePath();
    }

    public int getQuantity() {
        return quantity;
    }

    public HashMap<Integer, int[]> getGraph() {
        return graph;
    }

    /**
     * This method chooses a direction for the graph (to add a new room)
     * by reinforcement learning and deleting the impossible pathway.
     * It then increments probDirection with the chosen direction to up
     * the probability to continue in this direction.
     *
     * @param current
     * @param oppositeDirection
     * @param previousDirection
     * @return direction
     */
    public int calculDirection(int current, int oppositeDirection, int previousDirection) {
        System.out.println(probDirection.toString());
        List<Integer> copyOfProbDirection = List.copyOf(probDirection);
        ArrayList<Integer> voisin = new ArrayList<>();
        voisin.clear();
        if (graph.get(current)[4] != 0) {
            voisin.add(0);
        }
        if (graph.get(current)[5] < MAX_BOUND) {
            voisin.add(1);
        }
        if (graph.get(current)[4] < MAX_BOUND) {
            voisin.add(2);
        }
        if (graph.get(current)[5] != 0) {
            voisin.add(3);
        }

        if (voisin.contains(oppositeDirection) && previousDirection!= -1) {
            voisin.remove(Integer.valueOf(oppositeDirection));
        }
        copyOfProbDirection = copyOfProbDirection.stream()
                                .filter(dir -> voisin.contains(Integer.valueOf(dir)))
                                .collect(Collectors.toList());
        int direction = moreProbable(copyOfProbDirection);
        probDirection.add(direction);
        return direction;
    }

    private int moreProbable(List<Integer> copyOfProbDirection) {
        int size = copyOfProbDirection.size();
        int index = Integer.valueOf(seed.getSeed().get(size%15), 16)%size;
        return copyOfProbDirection.get(index);
     }

    private void attributePath() {
        createSourceVertice();
        int compteur = quantity-1;
        int i = 1;
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
            i++;
        }
    }

    private void createSourceVertice() {
        int[] sourceVertice = DungeonStructure.initNextlist();
        sourceVertice[4]= Integer.valueOf(seed.getSeed().get(1), 16)%(MAX_BOUND+1);
        sourceVertice[5]= Integer.valueOf(seed.getSeed().get(2), 16)%(MAX_BOUND+1);
        Position sourcePos = new Position(sourceVertice[5], sourceVertice[4]);
        existingRoom.put(sourcePos, 0);
        graph.put(0, sourceVertice);
    }

    private void drawPath(int size, int source) {
        ArrayList<Integer> voisin = new ArrayList<>();
        int current = source;
        int previousDirection = -1;
        Random GEN = new Random();

        // 0-N, 1-E, 2-S, 3-O
        System.out.println("\n\nInitial size :"+size);
        while (size>0) {

            int oppositeDirection = (previousDirection+2) % 4;
            int direction = calculDirection(current, oppositeDirection, previousDirection);

            System.out.println("\nsize :"+size);
            System.out.println("Current Pos : " + graph.get(current)[4] + ", "+ graph.get(current)[5]);
            System.out.println("Current Voisin : "+voisin.toString());
            int next = createVertice(current, direction);
            graph.get(next)[(direction+2) % 4] = current;
            graph.get(current)[direction] = next;

            if (created) {
               size --;
            }

            previousDirection = direction;
            current=next;
            voisin.clear();
        }
    }

    private int createVertice(int current, int direction) {
        int[] vertice = DungeonStructure.initNextlist();
        switch (direction) {
            //north
            case 0:
                vertice[4] = graph.get(current)[4]-1;
                vertice[5] = graph.get(current)[5];
                break;
            //est
            case 1:
                vertice[4] = graph.get(current)[4];
                vertice[5] = graph.get(current)[5]+1;
                break;
            //sud
            case 2:
                vertice[4] = graph.get(current)[4]+1;
                vertice[5] = graph.get(current)[5];
                break;
            //ouest
            case 3:
                vertice[4] = graph.get(current)[4];
                vertice[5] = graph.get(current)[5]-1;
                break;
        }
        Position testPosition = new Position(vertice[5], vertice[4]);
        boolean exist = existingRoom.containsKey(testPosition);
        int next;
        if(!exist) {
            System.out.println("in verticCreate : Vertice :  "+ Arrays.toString(vertice)+ "\n");
            acc++;
            graph.put(acc, vertice);
            next = acc;
            existingRoom.put(testPosition, acc);
            created = true;
        } else {
            next = existingRoom.get(testPosition);
            System.out.println("not created " + next + "\n");
            created = false;
        }
        return next;
    }

    public static void main(String[] args) {
        GraphDungeon test = new GraphDungeon(new Seed());
        System.out.println("coucou");
        int quantite = test.getQuantity();
        HashMap<Integer, int[]> graphtest = test.getGraph();
        System.out.println("final size : " + graphtest.size() + " Intitial size" + quantite);
        for (int i = 0; i < graphtest.size(); i++) {
            System.out.println(Arrays.toString(graphtest.get(i)) + " " + i);
        }
    }
}
