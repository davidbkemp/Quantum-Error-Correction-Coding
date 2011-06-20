package quantumlunch;

import quantumlunch.QecGraph.Operation;

import java.util.*;

public class ShortestPathToAllWhiteCalcutor {

    public List<QecGraph> shortestPathToAllWhite(QecGraph start) {
        Map<QecGraph, List<QecGraph>> visitedGraphs = new HashMap<QecGraph, List<QecGraph>>();
        visitedGraphs.put(start, singletonList(start));
        List<QecGraph> currentGraphs = Collections.<QecGraph> singletonList(start);
        List<QecGraph> nextGraphs = new ArrayList<QecGraph>();

        while (true) {
            for (QecGraph graph : currentGraphs) {
                if (graph.isAllWhite()) {
                    return visitedGraphs.get(graph);
                }
                exploreImmediateStateSpace(graph, visitedGraphs, nextGraphs);
            }
            currentGraphs = nextGraphs;
            nextGraphs = new ArrayList<QecGraph>();
        }

    }

    private List<QecGraph> singletonList(final QecGraph qecGraph) {
        return new ArrayList<QecGraph>() {{
            add(qecGraph);
        }};
    }
    
    private void exploreImmediateStateSpace(QecGraph graph, Map<QecGraph, List<QecGraph>> visitedGraphs, List<QecGraph> nextGraphs) {
        List<QecGraph> pathToCurrentGraph = visitedGraphs.get(graph);
        exploreViaTransformations(graph, pathToCurrentGraph, visitedGraphs, nextGraphs, Operation.X);
        exploreViaTransformations(graph, pathToCurrentGraph, visitedGraphs, nextGraphs, Operation.Y);
        exploreViaTransformations(graph, pathToCurrentGraph, visitedGraphs, nextGraphs, Operation.Z);
    }

    private void exploreViaTransformations(QecGraph graph, List<QecGraph> pathToCurrentGraph, Map<QecGraph, List<QecGraph>> visitedGraphs, List<QecGraph> nextGraphs,
            Operation operation) {
        for (int node = 0; node < graph.getSize(); node++) {
            QecGraph newGraph = graph.transform(node, operation);
            if (!visitedGraphs.containsKey(newGraph)) {
                visitedGraphs.put(newGraph, createPath(newGraph, pathToCurrentGraph));
                nextGraphs.add(newGraph);
            }
        }
    }

    private List<QecGraph> createPath(final QecGraph newGraph, final List<QecGraph> pathToCurrentGraph) {
        return new ArrayList<QecGraph>(){{
           addAll(pathToCurrentGraph);
           add(newGraph);
        }};
    }

}
