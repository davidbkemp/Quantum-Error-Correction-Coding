package quantumlunch;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class QecGraph {

	public static enum Operation {X, Y, Z}
	
	private final int size;
	
	// true => black, false => white 
	private final boolean[] blackNodes;
	
	// triangular matrix: true means there is an edge.
	// First row is of length 0, last is of length size - 1.
	private final boolean[][] edges;

	public QecGraph(int size, boolean[] blackNodes, boolean[][] edges) {
		this.size = size;
		this.blackNodes = blackNodes;
		this.edges = edges;
	}
	
	public int getSize() {
		return size;
	}
	
	public int distance() {
		int distanceBasedOnNeighbourCounts = minNeighbourCountForBlackNodes() + 1;
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
	private int distanceToAllWhite() {
		return new DistanceToAllWhiteCalculator().distanceToAllWhite(this);
	}

	private int minNeighbourCountForBlackNodes() {
		int min = Integer.MAX_VALUE;
		for (int node = 0; node < size; node++) {
			if (blackNodes[node]) {
				int neighborCount = neighborCount(node);
				if (neighborCount < min) {
					min = neighborCount;
				}
			}
		}
		return min;
	}

	private int neighborCount(int node) {
		int neighbours = 0;
		for (int row = node + 1; row < size; row++) {
			if (edges[row][node]) {
				neighbours++;
			}
		}
		for(int col = 0; col < node; col++) {
			if (edges[node][col]) {
				neighbours++;
			}
		}
		return neighbours;
	}

	// TODO: Cache this hashcode, but watch out for reflection computation (especially of equals)
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
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

	private void toggleNeighbours(int node, boolean[] newBlackNodes) {
		// flip neighbours
		for (int row = node + 1; row < size; row++) {
			if (edges[row][node]) {
				toggleNode(row, newBlackNodes);
			}
		}
		for(int col = 0; col < node; col++) {
			if (edges[node][col]) {
				toggleNode(col, newBlackNodes);
			}
		}
	}

	private void toggleNode(int node, boolean[] newBlackNodes) {
		newBlackNodes[node] = !blackNodes[node];
	}

}
