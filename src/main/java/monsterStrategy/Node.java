package monsterStrategy;

import utils.Position;

public class Node {
    private int heuristic;
    private int distFromSource;
    private int relevance;
    private Position nodePos;
    private Node parentNode;

    public Node(Position nodePos, Position targetPos) {
        this.nodePos = nodePos;
        this.heuristic = Math.abs(nodePos.getAbs() - targetPos.getAbs())
                + Math.abs(nodePos.getOrd() - targetPos.getOrd()); //We're using the Manhattan Heuristic approximation
        distFromSource = 0;
        relevance = heuristic;
    }

    public Node(Position nodePos, Position targetPos, Position sourcePos, Node parentNode) {
        this(nodePos, targetPos);
        distFromSource = Math.abs(nodePos.getAbs() - sourcePos.getAbs())
                + Math.abs(nodePos.getOrd() - sourcePos.getOrd());
        relevance = distFromSource + heuristic;
        this.parentNode = parentNode;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getDistFromSource() {
        return distFromSource;
    }

    public void setDistFromSource(int distFromSource) {
        this.distFromSource = distFromSource;
        relevance = distFromSource + heuristic;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public Position getNodePos() {
        return nodePos;
    }

    public void setNodePos(Position nodePos) {
        this.nodePos = nodePos;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
}
