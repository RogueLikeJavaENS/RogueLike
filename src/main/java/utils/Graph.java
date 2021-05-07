package utils;

import display.GridMap;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set nodes = new HashSet<>();

    public void addNode(NodeD nodeA){
        nodes.add(nodeA);
    }

    public Set getNodes() { return nodes; };

    public void setNodes(Set nodes) { this.nodes = nodes; }

    public Graph createGraph(GridMap gridMap){
        for (int abs = 0; abs < gridMap.getRoom().getWidth(); abs++){
            for (int ord = 0; ord < gridMap.getRoom().getHeight(); ord++){
                NodeD node = new NodeD()
            }
        }
    }
}
