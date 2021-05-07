package utils;

import display.GridMap;
import gameElement.Room;

import java.util.*;

public class Dijksrta {

    class Graph{
        HashMap<Position, Integer> associateNumberPosition;
        int vertex[];
        List<Integer> adj[];
        int n;

        public Graph(GridMap gridMap){
            Room room = gridMap.getRoom();
             this.n = room.getHeight()*room.getWidth();
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        private void addEdge(Position pos1, Position pos2){
            int a = associateNumberPosition.get(pos1);
            int b = associateNumberPosition.get(pos2);
            adj[a].add(b);
            adj[b].add(a);
        }

        private void addVertex(Position pos){
            associateNumberPosition.put(pos,associateNumberPosition.size());
        }

        private int getNumberWithPosition(Position pos){
            return associateNumberPosition.get(pos);
        }
    }

    public Dijksrta(){}

    public int[] dijkstra(Graph G, int src){
        // Initialisation
        int distance[] = new int[G.n];
        boolean visited[] = new boolean[G.n];
        Arrays.fill(distance,Integer.MAX_VALUE);
        Arrays.fill(visited,false);
        PriorityQueue<Integer> Q = new PriorityQueue<>(100);
        Q.add(src);
        distance[src] = 0;

        while (!Q.isEmpty()){
            int z = Q.remove();
            visited[z] = true;
            List<Integer> adjacent = G.adj[z];
            while(adjacent.size() != 0){
                int x = adjacent.remove(0);
                if (!visited[x] &&  distance[z]+1 < distance[x]){
                    distance[x] = distance[z]+1;
                    Q.add(x);
                }
            }
        }
        return distance;
    }

    private Graph createGraph(GridMap gridMap){
        Room room = gridMap.getRoom();
        Graph G = new Graph(gridMap);
        // Create the vertex
        for (int ord = 0; ord < room.getHeight(); ord++ ){
            for (int abs = 0; abs < room.getWidth(); abs++){
                G.addVertex(new Position(abs,ord));
            }
        }
        // Create the edges
        for (int ord = 0; ord < room.getHeight(); ord++ ){
            for (int abs = 0; abs < room.getWidth(); abs++){
                Position pos = new Position(abs,ord);
                int nbPos = G.getNumberWithPosition(pos);
                if (abs != 0){
                        G.adj[nbPos].add(G.getNumberWithPosition(new Position(abs-1,ord)));
                }
                if (abs != room.getWidth()-1){
                    G.adj[nbPos].add(G.getNumberWithPosition(new Position(abs+1,ord)));
                }
                if (ord != 0){
                    G.adj[nbPos].add(G.getNumberWithPosition(new Position(abs,ord-1)));
                }
                if (ord != room.getHeight()-1){
                    G.adj[nbPos].add(G.getNumberWithPosition(new Position(abs,ord+1)));
                }
            }
        }
        return G;
    }

    public boolean isThereAPath(Position src, GridMap gridMap, List<Position> dest ){
        Graph G = createGraph(gridMap);
        int distance[] =  dijkstra(G,G.getNumberWithPosition(src));
        for (Position pos : dest){
            int nbDest = G.getNumberWithPosition(pos);
            if (distance[nbDest] == Integer.MAX_VALUE){
                return false;
            }
        }
        return true;
    }

}
