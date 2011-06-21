package quantumlunch;

import org.apache.commons.lang.builder.EqualsBuilder;
import quantumlunch.isomorphism.IsomorphismCalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QecGraph {

    public static enum Operation {
        X, Y, Z
    }

    private final int size;

    // true => black, false => white
    private final boolean[] blackNodes;

    // triangular matrix: true means there is an edge.
    // First row is of length 0, last is of length size - 1.
    private final boolean[][] edges;

    private final IsomorphismCalculator isomorphismCalculator;

    public QecGraph(int size, boolean[] blackNodes, boolean[][] edges) {
        this.size = size;
        this.blackNodes = blackNodes;
        this.edges = edges;
        this.isomorphismCalculator = new IsomorphismCalculator(this);
    }

    public int getSize() {
        return size;
    }

    public int distance() {
        return distance(0);
    }

    // Filtered distance: terminate early and return 0 if the distance based on neighbor counts indicates that the distance cannot possibly be
    // more than the filter value.
    public int distance(int filter) {
        int distanceBasedOnNeighbourCounts = minNeighbourCountForBlackNodes() + 1;
        if (filter >= distanceBasedOnNeighbourCounts) return 0;
        int distanceToAllWhite = distanceToAllWhite();
        return distanceToAllWhite < distanceBasedOnNeighbourCounts ? distanceToAllWhite : distanceBasedOnNeighbourCounts;
    }

    public boolean isAllWhite() {
        for (boolean isBlackNode : blackNodes) {
            if (isBlackNode) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isomorphicTo(QecGraph rhs) {
        return isomorphismCalculator.isomorphicTo(rhs.isomorphismCalculator);
    }
    
    public QecGraph transform(int node, Operation operation) {
        boolean[] newBlackNodes = blackNodes.clone();
        switch (operation) {
        case X:
            toggleNeighbours(node, newBlackNodes);
            break;

        case Y:
            toggleNeighbours(node, newBlackNodes);
            toggleNode(node, newBlackNodes);
            break;

        case Z:
            toggleNode(node, newBlackNodes);
            break;

        default:
            throw new IllegalStateException("Unknown op: " + operation);
        }
        return new QecGraph(size, newBlackNodes, edges);

    }

    public boolean isBlack(int node) {
        return blackNodes[node];
    }
    public int getNumberOfBlackNodes() {
        int result = 0;
        for (boolean isBlack : blackNodes) {
             if (isBlack) result++;
        }
        return result;
    }

    public boolean edge(int n1, int n2) {
        if (n1 == n2) return false;
        return (n1 > n2) ? edges[n1][n2] : edges[n2][n1];
    }

    public Collection<Integer> neighbours(Integer node) {
        // TODO: Might be better if the graph were represented using maps and lists.
        List<Integer> neighbours = new ArrayList<Integer>();
        for(int n = 0; n < size; n++) {
            if (edge(node, n)) neighbours.add(n);
        }
        return neighbours;
    }

    @Override
    public int hashCode() {
        return isomorphismCalculator.isomorphismHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof QecGraph)) return false;
        QecGraph rhs = (QecGraph) obj;
        return new EqualsBuilder().append(blackNodes, rhs.blackNodes).append(edges, rhs.edges).isEquals()
                || isomorphicTo(rhs);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("QecGraph[size=")
            .append(size)
            .append(", blackNodes=");
        List<Integer> blackNodeList = new ArrayList<Integer>();
        for(int i = 0; i < size; i++) {
            if (blackNodes[i]) {
                blackNodeList.add(i);
            }
        }
        stringBuilder.append(blackNodeList)
            .append(", edges=");
        for(int row = 1; row < size; row++) {
            for (int col = 0; col < row; col++) {
                if (edge(row, col)) {
                    stringBuilder.append(" (" + row + "," + col + ") ");
                }
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private int distanceToAllWhite() {
        return new DistanceToAllWhiteCalculator().distanceToAllWhite(this);
    }

    int minNeighbourCountForBlackNodes() {
        int min = Integer.MAX_VALUE;
        boolean foundBlackNode = false;
        for (int node = 0; node < size; node++) {
            if (blackNodes[node]) {
                int neighborCount = neighborCount(node);
                if (neighborCount < min) {
                    min = neighborCount;
                    foundBlackNode = true;
                }
            }
        }
        return foundBlackNode ? min : 0;
    }

    private int neighborCount(int node) {
        int neighbours = 0;
        for (int col = 0; col < size; col++) {
            if (edge(node, col)) {
                neighbours++;
            }
        }
        return neighbours;
    }

    private void toggleNeighbours(int node, boolean[] newBlackNodes) {
        // flip neighbours
        for (int row = node + 1; row < size; row++) {
            if (edges[row][node]) {
                toggleNode(row, newBlackNodes);
            }
        }
        for (int col = 0; col < node; col++) {
            if (edges[node][col]) {
                toggleNode(col, newBlackNodes);
            }
        }
    }

    private void toggleNode(int node, boolean[] newBlackNodes) {
        newBlackNodes[node] = !blackNodes[node];
    }

}
