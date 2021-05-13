package utils;

import display.GridMap;
import game.entity.Entity;
import game.elements.Room;

import java.util.*;

/**
 * This class create a Graph which represent the gridmap of a room and had a method which permit to use Dijkstra from a source.
 *
 */
public class Dijksrta {
    HashMap<Position, Integer> associateNumberPosition;   // Associate all the Position with a number
    List<Integer>[] adj;            // List of the adjacent of the vertex
    int n;                  // Number of vertex
    int acc;

    /**
     * Create a new Object in order to use Dijkstra
     * @param gridMap the gridMap to make as a graph
     */
    public Dijksrta(GridMap gridMap){
        Room room = gridMap.getRoom();
        n = room.getHeight()*room.getWidth();
        adj = new ArrayList[n];    // create the array of list of the adjacent
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        associateNumberPosition = new HashMap<>();
        acc = 0;
        createGraph(gridMap);  // create the graph

    }

    /**
     * Add a vertex to the graph
     *
     * @param pos the position to add
     */
    public void addVertex(Position pos){
        associateNumberPosition.put(pos,acc);
        acc++;          // the number to associate with the position
    }

    /**
     * Get the number which is associate with the given Position
     * @param pos position
     * @return the associate number
     */
    public int getNumberWithPosition(Position pos){
        return associateNumberPosition.get(pos);
    }

    /**
     * Return the number of vertex in the graph
     * @return the number
     */
    public int getN() { return n; }

    /**
     * Method to use Dijkstra
     * @param src the source vertex use to run Dijkstra
     * @return an array of the distance of all vertex from src
     */
    public int[] runDijkstra(int src){
        // Initialisation
        int[] distance = new int[n];                // distance from src of each vertex
        Arrays.fill(distance,Integer.MAX_VALUE);
        distance[src] = 0;
        boolean[] visited = new boolean[getN()];
        Arrays.fill(visited,false);
        PriorityQueue<Integer> Q = new PriorityQueue<>(100);  // Queue of the next vertex to visit
        Q.add(src);

        // Body of the well-known algorithm Dijkstra
        while (!Q.isEmpty()){
            int z = Q.remove();
            visited[z] = true;
            List<Integer> adjacent = adj[z];
            for (int x : adjacent) {
                if (!visited[x] && distance[z] + 1 < distance[x]) {
                    distance[x] = distance[z] + 1;
                    Q.add(x);
                }
            }
        }
        // Return
        return distance;
    }

    /**
     * Create the graph of the given gridMap
     *
     * @param gridMap gridMap to use
     */
    public void createGraph(GridMap gridMap){
        Room room = gridMap.getRoom();
        // Create all the vertex (all the tiles in the gridMap)
        for (int ord = 0; ord < room.getHeight(); ord++ ){
            for (int abs = 0; abs < room.getWidth(); abs++){
                addVertex(new Position(abs,ord));
            }
        }
        // Create the edges
        for (int ord = 0; ord < room.getHeight(); ord++ ){
            for (int abs = 0; abs < room.getWidth(); abs++){ // For each position/vertex

                // if the position is not accessible don't rely her with it's neighbour
                boolean isAccessible = isPositionAccessible(gridMap,new Position(abs,ord));
                if (isAccessible){                                            // if the position is accessible
                    Position position = new Position(abs,ord);
                    int nbPos = getNumberWithPosition(position);

                    if (abs != 0){                                             // if the position (abs-1,ord) is valid (abs != 0)
                        Position adjPos = new Position(abs-1,ord);         // and the (abs-1,ord) is accessible
                        int nbAdjPos = getNumberWithPosition(adjPos);          // create a edges between the (abs,ord) and (abs-1,ord)
                        boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
                        if (adjAccessible){
                            adj[nbPos].add(nbAdjPos);
                        }
                    }
                    if (abs != room.getWidth()-1){                              // likewise with the position (abs+1,ord) valid (abs != room.getWidth-1)
                        Position adjPos = new Position(abs+1,ord);
                        int nbAdjPos = getNumberWithPosition(adjPos);
                        boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
                        if (adjAccessible){
                            adj[nbPos].add(nbAdjPos);
                        }
                    }
                    if (ord != 0){                                              // likewise with the position (abs,ord-1) valid (ord != 0)
                        Position adjPos = new Position(abs,ord-1);
                        int nbAdjPos = getNumberWithPosition(adjPos);
                        boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
                        if (adjAccessible){
                            adj[nbPos].add(nbAdjPos);
                        }
                    }
                    if (ord != room.getHeight()-1){                             // likewise with the position (abs,ord+1) valid (ord != room.getHeight-1)
                        Position adjPos = new Position(abs,ord+1);
                        int nbAdjPos = getNumberWithPosition(adjPos);
                        boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
                        if (adjAccessible){
                            adj[nbPos].add(nbAdjPos);
                        }
                    }
                }
            }
        }
    }

    /**
     * Return a boolean which say if the position on the gridMap is accessible
     *
     * @param gridMap the gridMap
     * @param position the position
     * @return true if the position is accessible, false if not
     */
    private boolean isPositionAccessible(GridMap gridMap, Position position){
        if (!gridMap.getTileAt(position.getAbs(), position.getOrd()).isNPCAccessible()){   // tile inaccessible -> position inaccessible
            return false;
        }
        List<Entity> entityList = gridMap.getEntitiesAt(position.getAbs(),position.getOrd());
        boolean isAccessible = true;
        for (Entity entity : entityList){
            if (entity.isMonster()){        // make the monster accessible because we can kill him and he can move
                break;
            }
            if (!entity.getIsNPCAccessible()){      // if the entity is not NPC accessible directly return false
                isAccessible = false;
                break;
            }
        }
        return isAccessible;
    }


    /**
     * Method which tell if at least one destination is accessible from the source position
     * @param src the source position
     * @param dest the destination position to test
     * @return true if at least one destination position is accessible from the src, false if none of the destination is accessible
     */
    public boolean isThereAPath(Position src, List<Position>dest){
        int[] distance =  runDijkstra(getNumberWithPosition(src));

        for (Position pos : dest){
            int nbDest = getNumberWithPosition(pos);

            if (distance[nbDest] != Integer.MAX_VALUE){     // if the distance == Integer.MAX_Value it means there isn't a path betwen the source and the destination
                return true;
            }
        }
        return false;
    }

    /**
     * Update the graph by making a position accessible
     *
     * @param gridMap the gridMap
     * @param position the position making accessible
     */
    public void update(GridMap gridMap, Position position) {
        int abs = position.getAbs();
        int ord = position.getOrd();

        int nbPos = getNumberWithPosition(position);

        // look if the position around are accessible and make the edges between (like on the method createGraph)
        if (abs != 0){
            Position adjPos = new Position(abs-1,ord);
            int nbAdjPos = getNumberWithPosition(adjPos);
            boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
            if (adjAccessible){
                adj[nbPos].add(nbAdjPos);   // create to directed edges in order to an undirected graph
                adj[nbAdjPos].add(nbPos);
            }
        }
        if (abs != gridMap.getRoom().getWidth()-1){
            Position adjPos = new Position(abs+1,ord);
            int nbAdjPos = getNumberWithPosition(adjPos);
            boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
            if (adjAccessible){
                adj[nbPos].add(nbAdjPos);
                adj[nbAdjPos].add(nbPos);
            }
        }
        if (ord != 0){
            Position adjPos = new Position(abs,ord-1);
            int nbAdjPos = getNumberWithPosition(adjPos);
            boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
            if (adjAccessible){
                adj[nbPos].add(nbAdjPos);
                adj[nbAdjPos].add(nbPos);
            }
        }
        if (ord != gridMap.getRoom().getHeight()-1){
            Position adjPos = new Position(abs,ord+1);
            int nbAdjPos = getNumberWithPosition(adjPos);
            boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
            if (adjAccessible){
                adj[nbPos].add(nbAdjPos);
                adj[nbAdjPos].add(nbPos);
            }
        }
    }
}
