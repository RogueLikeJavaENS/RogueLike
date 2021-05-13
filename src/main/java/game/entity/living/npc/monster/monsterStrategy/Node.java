package game.entity.living.npc.monster.monsterStrategy;

import utils.Position;

/**
 * A node is an abstract position for the A* algorithm. It contains its distance from the of the algorithm, its position,
 * its parent node, its relevance to be picked by the algorithm, and its heuristic (which is calulated according to the
 * Manhattan Heuristic approximation).
 *
 * @author Raphael
 */
public class Node {
    private int heuristic;
    private int distFromSource;
    private int relevance;
    private Position nodePos;
    private Node parentNode;

    /**
     * Creates a node, usually used for the very first node for the algorithm.
     * The heuristic is calculated using the Manhattan Heuristic approximation as the game is on a grid with vertical and horizontal moves only.
     * The distance from the source is set to 0 and the relevance is equals to the heuristic.
     * @param nodePos position of the node to calculate the heuristic.
     * @param targetPos position of the target of the algorithm to calculate the heuristic.
     */
    public Node(Position nodePos, Position targetPos) {
        this.nodePos = nodePos;
        this.heuristic = Math.abs(nodePos.getAbs() - targetPos.getAbs())
                + Math.abs(nodePos.getOrd() - targetPos.getOrd());
        distFromSource = 0;
        relevance = heuristic;
    }

    /**
     * Creates a node.
     * The heuristic is calculated using the Manhattan Heuristic approximation as the game is on a grid with vertical and horizontal moves only.
     * The distance from the source is an addition of the distance in abscissa and the distance in ordinate.
     * The relevance is set to the heuristic + the distance from the source.
     * @param nodePos position of the node which will be used for the different calculations.
     * @param targetPos position of the target of the algorithm which will be used for the different calculations.
     * @param sourcePos position of the source of the algorithm which will be used for the different calculations.
     * @param parentNode node from which this node is created.
     */
    public Node(Position nodePos, Position targetPos, Position sourcePos, Node parentNode) {
        this(nodePos, targetPos);
        distFromSource = Math.abs(nodePos.getAbs() - sourcePos.getAbs())
                + Math.abs(nodePos.getOrd() - sourcePos.getOrd());
        relevance = distFromSource + heuristic;
        this.parentNode = parentNode;
    }

    public int getRelevance() {
        return relevance;
    }

    public Position getNodePos() {
        return nodePos;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
}
