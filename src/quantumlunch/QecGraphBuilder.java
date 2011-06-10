package quantumlunch;

public class QecGraphBuilder {

	private final int size;
	
	// true => black, false => white 
	private final boolean[] blackNodes;
	
	// triangular matrix: true means there is an edge.
	// First row is of length 0, last is of length size - 1.
	private final boolean[][] edges;

	public QecGraphBuilder(int size) {
		this.size = size;
		this.blackNodes = new boolean[size];
		this.edges = new boolean[size][];
		for (int i = 1; i < size; i++) {
			this.edges[i] = new boolean[i];
		}
	}

	public static QecGraphBuilder qecGraph(int size) {
		return new QecGraphBuilder(size);
	}

	public QecGraphBuilder withBlackNodes(int... blackNodes) {
		for (int node : blackNodes) {
			assertIsValidNodeNumber(node);
			this.blackNodes[node] = true;
		}
		return this;
	}

	public QecGraphBuilder edge(int n1, int n2) {
		assertTrue("Nodes cannot connect to themselves", n1 != n2);
		assertIsValidNodeNumber(n1);
		assertIsValidNodeNumber(n2);
		if (n1 < n2) {
			int tmp = n1;
			n1 = n2;
			n2 = tmp;
		}
		this.edges[n1][n2] = true;
		return this;
	}

	public QecGraph build() {
		return new QecGraph(size, blackNodes, edges);
	}
	
	private void assertIsValidNodeNumber(int node) {
		assertTrue("Node must be between 0 and (size-1) but was: " + node, (node >=0 && node < size));
	}

	private void assertTrue(String message, boolean predicate) {
		if (!predicate) {
			throw new IllegalArgumentException(message);
		}
	}

}
