package quantumlunch.bestgraph.semibruteforce;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.HashSet;
import java.util.Set;

public class GraphDescriptionMatcher extends TypeSafeMatcher<GraphDescription> {

    private final int size;
    private final int[] colours;
    private final int[] downstreamNeighbours;
    private final int[] downstreamBlackNeighbours;
    private final int[][] edges;
    private String message;

    public GraphDescriptionMatcher(int size, int[] colours, int[] downstreamNeighbours, int[] downstreamBlackNeighbours, int[][] edges) {
        this.size = size;
        this.colours = colours;
        this.downstreamNeighbours = downstreamNeighbours;
        this.downstreamBlackNeighbours = downstreamBlackNeighbours;
        this.edges = edges;
        if (edges.length != size) {
            throw new IllegalArgumentException("edges size");
        }
        if (colours.length != size) {
            throw new IllegalArgumentException("colours size");
        }
        if (downstreamNeighbours.length != size) {
            throw new IllegalArgumentException("downstreamNeighbours size");
        }
        if (downstreamBlackNeighbours.length != size) {
            throw new IllegalArgumentException("downstreamBlackNeighbours size");
        }
        this.message = this.toString();
    }

    @Override
    protected boolean matchesSafely(GraphDescription graphDescription) {
        if (graphDescription.getSize() != size) {
            message = "Graph with size: " + size;
            return false;
        }
        for (int node = 0; node < size; node++) {
            Integer actualColour = graphDescription.getColourEnumerators()[node].value();
            if (colours[node] != actualColour) {
                message = String.format("Graph whose node %s has colour %s (got %s)",
                        node, colours[node], actualColour);
                return false;
            }
            Integer actualNumDownStreamNeighbours = graphDescription.getNumDownStreamNeighboursEnumerators()[node].value();
            if (downstreamNeighbours[node] != actualNumDownStreamNeighbours) {
                message =String.format("Graph whose node %s has downstreamNeighbours %s (got %s)",
                        node, downstreamNeighbours[node], actualNumDownStreamNeighbours);
                return false;
            }
            Integer actualNumDownstreamBlackNeighbours = graphDescription.getNumDownStreamBlackNeighboursEnumerators()[node].value();
            if (downstreamBlackNeighbours[node] != actualNumDownstreamBlackNeighbours) {
                message = String.format("Graph whose node %s has downstreamBlackNeighbours %s (got %s)",
                        node, downstreamBlackNeighbours[node], actualNumDownstreamBlackNeighbours);
                return false;
            }
        }
        if (!checkEdges(edges, graphDescription)) {
            return false;
        }
        return true;
    }

    private boolean checkEdges(int[][] edges, GraphDescription graphDescription) {
        for (int node = 0; node < size; node++) {
            EdgeEnumerator edgeEnumerator = graphDescription.getEdgeEnumerators()[node];
            Set<Integer> actualEdges = new HashSet<Integer>(edgeEnumerator.value());
            Set<Integer> expectedEdges = asSet(edges[node]);
            if (!actualEdges.equals(expectedEdges)) {
                message = String.format("Graph whose node %s contains edges %s (got %s)", node, expectedEdges, actualEdges);
                return false;
            }
        }
        return true;
    }

    private Set<Integer> asSet(int[] edges) {
        Set<Integer> result = new HashSet<Integer>(edges.length);
        for(int edge : edges) {
            result.add(edge);
        }
        return result;
    }

    public void describeTo(Description description) {
        description.appendText(message);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
