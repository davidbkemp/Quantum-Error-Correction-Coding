package quantumlunch.bestgraph.semibruteforce;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GraphDescriptionTest {

    @Test
    public void nextShouldIterateThroughGraphDescriptions() throws Exception {
        int size = 3;
        GraphDescription graphDescription = new GraphDescription(size, 0);
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 0L}, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 2L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 0L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();

        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 1L}, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 2L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 0L, 0L});

        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 2L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 2L}, new Long[]{0L, 0L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 1L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 0L, 1L});

        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 0L, 1L}, new Long[]{0L, 0L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 1L}, new Long[]{0L, 0L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 1L}, new Long[]{0L, 1L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 0L, 2L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 0L, 2L}, new Long[]{0L, 0L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 0L, 2L}, new Long[]{0L, 0L, 2L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 0L, 0L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 0L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 1L, 1L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 0L, 2L});
        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{1L, 1L, 1L}, new Long[]{0L, 1L, 2L}, new Long[]{0L, 1L, 2L});

        graphDescription.next();
        assertThatGraphDescriptionIs(graphDescription, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 0L}, new Long[]{0L, 0L, 0L});
    }

    private void assertThatGraphDescriptionIs(
        GraphDescription graphDescription, Long[] colours, Long[] numDownStreamNeighbours, Long[] numDownStreamBlackNeighbours) {
        assertMatching("colours", colours, graphDescription.getColourEnumerators());
        assertMatching("numDownStreamNeighbours", numDownStreamNeighbours, graphDescription.getNumDownStreamNeighboursEnumerators());
        assertMatching("numDownStreamBlackNeighbours", numDownStreamBlackNeighbours, graphDescription.getNumDownStreamBlackNeighboursEnumerators());
    }

    private void assertMatching(String description, Long[] values, IntegerEnumerator[] enumerators) {
        for (int nodeNum = 0; nodeNum < values.length; nodeNum++) {
            String reason = String.format("%s did not match in node %s", description, nodeNum);
            assertThat(reason, enumerators[nodeNum].value(), is(values[nodeNum]));
        }
    }

}
