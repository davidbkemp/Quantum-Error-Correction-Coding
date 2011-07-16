package quantumlunch.bestgraph.semibruteforce;

import quantumlunch.QecGraph;

import java.util.HashSet;
import java.util.Set;

public class BestGraphFinder {

    public static void main(String[] args) {
        if (args.length != 2) {
            usage();
            System.exit(1);
        }
        try {
            int size = Integer.parseInt(args[0]);
            int minDistance = Integer.parseInt(args[1]);
            System.out.println(new BestGraphFinder().findBestGraph(size, minDistance));

        } catch (NumberFormatException e) {
            usage();
            System.exit(1);
        }
    }

    private static void usage() {
        System.err.println("BestGraph needs two arguments: graph size and minimum expected distance.");
    }

    /**
     * Find a graph with the maximum distance having a specified number of nodes.
     *
     * @param size        The number of nodes.
     * @param minDistance The minimum distance to consider.
     * @return A graph having the maximum distance greater than or equal to minDistance for the specified number of nodes, or null if there is none.
     */
    public QecGraph findBestGraph(int size, int minDistance) {
        int bestDistance = minDistance - 1;
        QecGraph bestGraph = null;
        Set<QecGraph> graphsTried = new HashSet<QecGraph>();
        GraphDescription graphDescription = new GraphDescription(size, minDistance);
        for (QecGraph graph = graphDescription.next(); graph != null; graph = graphDescription.next()) {
            if (graphsTried.add(graph)) {
                int distance = graph.distance(bestDistance);
                if (distance > bestDistance) {
                    bestDistance = distance;
                    bestGraph = graph;
                }
            }
        }

        return bestGraph;
    }
}
