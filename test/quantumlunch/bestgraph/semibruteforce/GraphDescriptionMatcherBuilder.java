package quantumlunch.bestgraph.semibruteforce;

import org.hamcrest.Matcher;

public class GraphDescriptionMatcherBuilder {
    private int size;
    private int[] colours;
    private int[] downstreamNeighbours;
    private int[] downstreamBlackNeighbours;
    private int[][] edges;

    static GraphDescriptionMatcherBuilder graphDescriptionMatcher() {
        return new GraphDescriptionMatcherBuilder();
    }

    public Matcher<GraphDescription> build() {
        return new GraphDescriptionMatcher(size, colours, downstreamNeighbours, downstreamBlackNeighbours, edges);
    }

    public GraphDescriptionMatcherBuilder size(int size) {
        this.size = size;
        return this;
    }

    public GraphDescriptionMatcherBuilder colours(int... colours ) {
        this.colours = colours;
        return this;
    }

    public GraphDescriptionMatcherBuilder downstreamNeighbours(int... downstreamNeighbours) {
        this.downstreamNeighbours = downstreamNeighbours;
        return this;
    }


    public GraphDescriptionMatcherBuilder downstreamBlackNeighbours(int... downstreamBlackNeighbours) {
        this.downstreamBlackNeighbours = downstreamBlackNeighbours;
        return this;
    }

    public GraphDescriptionMatcherBuilder edges(int[][] edges) {
        this.edges = edges;
        return this;
    }

}
