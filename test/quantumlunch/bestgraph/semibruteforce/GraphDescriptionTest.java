package quantumlunch.bestgraph.semibruteforce;

import org.hamcrest.Matcher;
import org.junit.Test;

import static quantumlunch.bestgraph.semibruteforce.GraphDescriptionMatcherBuilder.graphDescriptionMatcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GraphDescriptionTest {

    @Test
    public void nextShouldIterateThroughGraphDescriptionsForGraphSize2() throws Exception {
        int size = 2;
        GraphDescription graphDescription = new GraphDescription(size, 0);
        Matcher<GraphDescription> graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0)
                .downstreamNeighbours(0, 0).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{}, {}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0)
                .downstreamNeighbours(0, 1).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1)
                .downstreamNeighbours(0, 0).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 1)
                .downstreamNeighbours(0, 1).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1)
                .downstreamNeighbours(0, 0).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{},{}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(1, 1)
                .downstreamNeighbours(0, 1).downstreamBlackNeighbours(0, 1)
                .edges(new int[][]{{},{0}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);

        graphDescription.next();
        graphDescriptionMatcher = graphDescriptionMatcher().size(size).colours(0, 0)
                .downstreamNeighbours(0, 0).downstreamBlackNeighbours(0, 0)
                .edges(new int[][]{{}, {}}).build();
        assertThat(graphDescription, graphDescriptionMatcher);
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

    private void assertThatGraphDescriptionIs(
            GraphDescription graphDescription, Integer[] colours, Integer[] numDownStreamNeighbours, Integer[] numDownStreamBlackNeighbours) {
        assertMatching("colours", colours, graphDescription.getColourEnumerators());
        assertMatching("numDownStreamNeighbours", numDownStreamNeighbours, graphDescription.getNumDownStreamNeighboursEnumerators());
        assertMatching("numDownStreamBlackNeighbours", numDownStreamBlackNeighbours, graphDescription.getNumDownStreamBlackNeighboursEnumerators());
    }

    private void assertMatching(String description, Integer[] values, IntegerEnumerator[] enumerators) {
        for (int nodeNum = 0; nodeNum < values.length; nodeNum++) {
            String reason = String.format("%s did not match in node %s", description, nodeNum);
            assertThat(reason, enumerators[nodeNum].value(), is(values[nodeNum]));
        }
    }

}
