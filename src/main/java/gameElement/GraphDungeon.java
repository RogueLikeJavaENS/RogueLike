package gameElement;

import generation.DungeonStructure;
import generation.Seed;
import utils.Position;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphDungeon {
    public int getQuantity() {
        return quantity;
    }

    public HashMap<Integer, int[]> getGraph() {
        return graph;
    }

    private HashMap<Integer, int[]> graph;
    private HashMap<Position, Integer> existingRoom;
    private final Seed seed;
    private int quantity;
    private final int MAX_BOUND = 5;
    private int acc;

    public GraphDungeon(Seed seed) {
        this.seed = seed;
        this.quantity = DungeonStructure.numberOfRoom(seed);
        this.graph = new HashMap<Integer,int[]>();
        this.existingRoom = new HashMap<Position, Integer>();
        this.acc=0;
        attributePath();
    }

    private void createSourceVertice() {
        int[] sourceVertice = DungeonStructure.initNextlist();
        sourceVertice[4]= Integer.valueOf(seed.getSeed().get(1), 16)%(MAX_BOUND+1);
        sourceVertice[5]= Integer.valueOf(seed.getSeed().get(2), 16)%(MAX_BOUND+1);
        Position sourcePos = new Position(sourceVertice[5], sourceVertice[4]);
        existingRoom.put(sourcePos, 0);
        graph.put(0, sourceVertice);
    }

    private int createVertice(int current, int direction) {
        int[] vertice = DungeonStructure.initNextlist();
        switch (direction){
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
        Position testPosition = new Position(vertice[4], vertice[5]);
        boolean exist = existingRoom.containsKey(testPosition);
        int next = 0;
        if(! exist) {
            acc++;
            graph.put(acc, vertice);
            next = acc;
        }
        else {
            next = existingRoom.get(testPosition);
        }
        return next;
    }

    private void attributePath() {
        createSourceVertice();
        int compteur = quantity;
        while (compteur>0){
            int size = Integer.valueOf(seed.getSeed().get(4), 16)%(MAX_BOUND+1);
            drawPath(size, 0);
            compteur -= size;
        }
    }

    private void drawPath(int size, int source) {
        ArrayList<Integer> voisin = new ArrayList<>();
        int current = source;
        //0-N, 1-E, 2-S, 3-O
        while (size>0) {
            if (graph.get(current)[4] < MAX_BOUND) {
                voisin.add(2);
            }
            if (graph.get(current)[5] < MAX_BOUND) {
                voisin.add(1);
            }
            if (graph.get(current)[4] != 0) {
                voisin.add(0);
            }
            if (graph.get(current)[5] != 0) {
                voisin.add(3);
            }
            int index = Integer.valueOf(seed.getSeed().get(7), 16)%(voisin.size()-1);
            int direction = voisin.get(index);
            int next = createVertice(current, direction);
            if(!(next < acc)){
                size--;
            }
            current=next;
        }
    }

    public static void main(String[] args) {
        GraphDungeon test = new GraphDungeon(new Seed());
        System.out.println("coucou");
        int quantite = test.getQuantity();
        HashMap<Integer, int[]> graphtest = test.getGraph();
        for (int i = 0; i < quantite; i++) {
            System.out.printf("%d, %d\n", graphtest.get(i)[4], graphtest.get(i)[5]);
        }
    }
}
