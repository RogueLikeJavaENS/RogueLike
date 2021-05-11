package utils;

import display.GridMap;
import entity.Entity;
import gameElement.Room;

import java.util.*;

public class Dijksrta {
    HashMap<Position, Integer> associateNumberPosition;
    List<Integer>[] adj;
    int n;
    int acc;

    public Dijksrta(GridMap gridMap){
        Room room = gridMap.getRoom();
        n = room.getHeight()*room.getWidth();
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        associateNumberPosition = new HashMap<>();
        acc = 0;
        createGraph(gridMap);

    }

    public void addVertex(Position pos){
        associateNumberPosition.put(pos,acc);
        acc++;
    }

    public int getNumberWithPosition(Position pos){
        return associateNumberPosition.get(pos);
    }

    public int getN() { return n; }

    public int[] runDijkstra(int src){
        // Initialisation
        int[] distance = new int[n];
        boolean[] visited = new boolean[getN()];
        Arrays.fill(distance,Integer.MAX_VALUE);
        Arrays.fill(visited,false);
        PriorityQueue<Integer> Q = new PriorityQueue<>(100);
        Q.add(src);
        distance[src] = 0;

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
        return distance;
    }

    public void createGraph(GridMap gridMap){
        Room room = gridMap.getRoom();
        // Create the vertex
        for (int ord = 0; ord < room.getHeight(); ord++ ){
            for (int abs = 0; abs < room.getWidth(); abs++){
                addVertex(new Position(abs,ord));
            }
        }
        // Create the edges
        for (int ord = 0; ord < room.getHeight(); ord++ ){
            for (int abs = 0; abs < room.getWidth(); abs++){

                boolean isAccessible = isPositionAccessible(gridMap,new Position(abs,ord));
                if (isAccessible){
                    Position position = new Position(abs,ord);
                    int nbPos = getNumberWithPosition(position);

                    if (abs != 0){
                        Position adjPos = new Position(abs-1,ord);
                        int nbAdjPos = getNumberWithPosition(adjPos);
                        boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
                        if (adjAccessible){
                            adj[nbPos].add(nbAdjPos);
                        }
                    }
                    if (abs != room.getWidth()-1){
                        Position adjPos = new Position(abs+1,ord);
                        int nbAdjPos = getNumberWithPosition(adjPos);
                        boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
                        if (adjAccessible){
                            adj[nbPos].add(nbAdjPos);
                        }
                    }
                    if (ord != 0){
                        Position adjPos = new Position(abs,ord-1);
                        int nbAdjPos = getNumberWithPosition(adjPos);
                        boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
                        if (adjAccessible){
                            adj[nbPos].add(nbAdjPos);
                        }
                    }
                    if (ord != room.getHeight()-1){
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

    private boolean isPositionAccessible(GridMap gridMap, Position position){
        if (!gridMap.getTileAt(position.getAbs(), position.getOrd()).isNPCAccessible()){
            return false;
        }
        List<Entity> entityList = gridMap.getEntitiesAt(position.getAbs(),position.getOrd());
        boolean isAccessible = true;
        for (Entity entity : entityList){
            if (entity.isMonster()){
                break;
            }
            if (!entity.getIsNPCAccessible()){
                isAccessible = false;
                break;
            }
        }
        return isAccessible;
    }


    public boolean isThereAPath(Position src, List<Position>dest){
        int[] distance =  runDijkstra(getNumberWithPosition(src));

        for (Position pos : dest){
            int nbDest = getNumberWithPosition(pos);

            if (distance[nbDest] != Integer.MAX_VALUE){
                return true;
            }
        }
        return false;
    }

    public void update(GridMap gridMap, Position position) {
        int abs = position.getAbs();
        int ord = position.getOrd();

        int nbPos = getNumberWithPosition(position);

        if (abs != 0){
            Position adjPos = new Position(abs-1,ord);
            int nbAdjPos = getNumberWithPosition(adjPos);
            boolean adjAccessible = isPositionAccessible(gridMap,adjPos);
            if (adjAccessible){
                adj[nbPos].add(nbAdjPos);
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
