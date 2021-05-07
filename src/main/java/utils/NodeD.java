package utils;

import monsterStrategy.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NodeD {
    private String name;
    private List shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    Map adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance){
        adjacentNodes.put(destination,distance);
    }

    public NodeD(String name){
        this.name = name;
    }
}
