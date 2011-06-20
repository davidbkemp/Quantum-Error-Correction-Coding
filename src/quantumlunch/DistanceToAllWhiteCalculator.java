package quantumlunch;

import quantumlunch.QecGraph.Operation;

import java.util.*;

class DistanceToAllWhiteCalculator {

    public int distanceToAllWhite(QecGraph qecGraph) {
        Set<QecGraph> visitedGraphs = new HashSet<QecGraph>();
        visitedGraphs.add(qecGraph);
        List<QecGraph> currentGraphs = Collections.<QecGraph> singletonList(qecGraph);
        List<QecGraph> nextGraphs = new ArrayList<QecGraph>();
        int distance = 0;

        while (true) {
            for (QecGraph graph : currentGraphs) {
                if (graph.isAllWhite()) {
                    return distance;
                }
                exploreImmediateStateSpace(graph, visitedGraphs, nextGraphs);
            }
            currentGraphs = nextGraphs;
            nextGraphs = new ArrayList<QecGraph>();
            distance++;
        }
    }

    private void exploreImmediateStateSpace(QecGraph graph, Set<QecGraph> visitedGraphs, List<QecGraph> nextGraphs) {
        exploreViaTransformations(graph, visitedGraphs, nextGraphs, Operation.X);
        exploreViaTransformations(graph, visitedGraphs, nextGraphs, Operation.Y);
        exploreViaTransformations(graph, visitedGraphs, nextGraphs, Operation.Z);
    }

    private void exploreViaTransformations(QecGraph graph, Set<QecGraph> visitedGraphs, List<QecGraph> nextGraphs,
            Operation operation) {
        for (int node = 0; node < graph.getSize(); node++) {
            QecGraph newGraph = graph.transform(node, operation);
            if (!visitedGraphs.contains(newGraph)) {
                visitedGraphs.add(newGraph);
                nextGraphs.add(newGraph);
            }
        }
    }
}
