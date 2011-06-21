package quantumlunch;

import java.util.HashSet;
import java.util.Set;

public class BestGraphFinder {

    public QecGraph findBestGraph(int size) {
        int bestDistance = 0;
        QecGraph bestGraph = null;
        Set<QecGraph> graphsTried = new HashSet<QecGraph>();
        
        int numberOfPossibleEdges = (size * (size - 1)) / 2;
        System.out.println("Number of possible edges: " + numberOfPossibleEdges);
        
        // Bit string representing every node connected directly to each other.
        long fullyConnected = (1L << numberOfPossibleEdges) - 1;
        
        System.out.println("Number of edge permutations: " + fullyConnected);
        for (int blackNodeCount = 1; blackNodeCount <= size; blackNodeCount++) {
            for (long edgeBitPattern = 0; edgeBitPattern <= fullyConnected; edgeBitPattern++) {
                QecGraphBuilder graphBuilder = new QecGraphBuilder(size);
                colourNodes(graphBuilder, blackNodeCount);
                addEdges(graphBuilder, size, edgeBitPattern);
                QecGraph graph = graphBuilder.build();
                if (graphsTried.add(graph)) {
                    int distance = graph.distance(bestDistance);
                    if (distance > bestDistance) {
                        bestDistance = distance;
                        bestGraph = graph;
                    }
                }
            }
        }
        return bestGraph;
    }

    private void addEdges(QecGraphBuilder graphBuilder, int size, long edgeBitPattern) {
        for (int row = 1; row < size; row ++) {
            for(int col = 0; col < row; col++) {
                if ((edgeBitPattern & bitPatternFor(row, col)) != 0) {
                    graphBuilder.edge(row, col);
                }
            }
        }
        
    }

    private long bitPatternFor(int row, int col) {
        long rowPattern = 1L << col;
        long paddingSize = (row * (row - 1)) / 2;
        return rowPattern << paddingSize;
    }

    private void colourNodes(QecGraphBuilder graphBuilder, int blackNodeCount) {
        for (int node = 0; node < blackNodeCount; node++) {
            graphBuilder.withBlackNodes(node);
        }
    }

}
