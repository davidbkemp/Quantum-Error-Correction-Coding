package quantumlunch.bestgraph.semibruteforce;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static quantumlunch.bestgraph.semibruteforce.GraphDescriptionMatcherBuilder.graphDescriptionMatcher;

public class GraphDescriptionTest {

    @Test
    public void nextShouldIterateThroughGraphDescriptionsForGraphSize2() throws Exception {
        int size = 2;
        GraphDescription graphDescription = new GraphDescription(size, 0);

        Matcher<GraphDescription> initialGraphExpectation = graphDescriptionMatcher().size(size).colours(0, 0)
                .downstreamNeighbours(0, 0).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{}, {}}).build();
        assertThat(graphDescription, initialGraphExpectation);

        assertThat(graphDescription.next(), notNullValue());
        Matcher<GraphDescription> graphDescriptionMatcher =  graphDescriptionMatcher().size(size).colours(0, 0)
                .downstreamNeighbours(0, 1).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        assertThat(graphDescription.next(), notNullValue());
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1)
                .downstreamNeighbours(0, 0).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        assertThat(graphDescription.next(), notNullValue());
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1)
                .downstreamNeighbours(0, 1).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        assertThat(graphDescription.next(), notNullValue());
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1)
                .downstreamNeighbours(0, 0).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        assertThat(graphDescription.next(), notNullValue());
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1)
                .downstreamNeighbours(0, 1).downstreamBlackNeighbours(0, 1)
                .edges(new int[][]{{},{0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        assertThat(graphDescription.next(), nullValue());
        assertThat(graphDescription, initialGraphExpectation);

        assertThat(graphDescription.next(), notNullValue());
        assertThat(graphDescription, initialGraphExpectation);
    }

    @Test
    public void nextShouldIterateThroughGraphDescriptionsForGraphSize3() throws Exception {
        int size = 3;
        GraphDescription graphDescription = new GraphDescription(size, 0);
        Matcher<GraphDescription> graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 0, 0).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {0}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {0}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 0, 2).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {0, 1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 1, 2).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {0}, {0, 1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 1)
                .downstreamNeighbours(0, 0, 0).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 1)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 1)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 1)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {0}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 1)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {0}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 1)
                .downstreamNeighbours(0, 0, 2).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {0,1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 1)
                .downstreamNeighbours(0, 1, 2).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {0}, {0,1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1, 1)
                .downstreamNeighbours(0, 0, 0).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1, 1)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1, 1)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 1)
                .edges(new int[][]{{}, {}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1, 1)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {0}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1, 1)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 0, 1)
                .edges(new int[][]{{}, {0}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1, 1)
                .downstreamNeighbours(0, 0, 2).downstreamBlackNeighbours(0, 0, 1)
                .edges(new int[][]{{}, {}, {0,1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1, 1)
                .downstreamNeighbours(0, 1, 2).downstreamBlackNeighbours(0, 0, 1)
                .edges(new int[][]{{}, {0}, {0,1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1, 1)
                .downstreamNeighbours(0, 0, 0).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1, 1)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 1)
                .edges(new int[][]{{}, {}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1, 1)
                .downstreamNeighbours(0, 0, 1).downstreamBlackNeighbours(0, 0, 1)
                .edges(new int[][]{{}, {}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1, 1)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 1, 1)
                .edges(new int[][]{{}, {0}, {0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1, 1)
                .downstreamNeighbours(0, 1, 1).downstreamBlackNeighbours(0, 1, 1)
                .edges(new int[][]{{}, {0}, {1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1, 1)
                .downstreamNeighbours(0, 0, 2).downstreamBlackNeighbours(0, 0, 2)
                .edges(new int[][]{{}, {}, {0,1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1, 1)
                .downstreamNeighbours(0, 1, 2).downstreamBlackNeighbours(0, 1, 2)
                .edges(new int[][]{{}, {0}, {0,1}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0, 0)
                .downstreamNeighbours(0, 0, 0).downstreamBlackNeighbours(0, 0, 0)
                .edges(new int[][]{{}, {}, {}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);
    }


}
