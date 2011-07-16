package quantumlunch.bestgraph.semibruteforce;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static quantumlunch.bestgraph.semibruteforce.EdgeEnumeratorBuilder.edgeEnumerator;

public class EdgeEnumeratorTest {

    @Test
    public void returnsEmptyListWhenNumDownstreamNeighboursIsZero() throws Exception {
        EdgeEnumerator edgeEnumerator = edgeEnumerator().graphSize(3).build();
        assertThat(edgeEnumerator.value(), Matchers.<Integer>empty());
    }

    @Test
    public void enumeratesDownStreamNeighbours() throws Exception {
        EdgeEnumerator edgeEnumerator = edgeEnumerator().graphSize(10).nodeNum(4).numDownStreamNeighbours(2).build();
        assertThat(edgeEnumerator.value(), is(nodeList(0, 1)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 2)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 3)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(1, 2)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(1, 3)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(2, 3)));

        assertThat(edgeEnumerator.advance(), is(false));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 1)));
    }

    @Test
    public void enumeratesDownstreamBlackNeighbours() throws Exception {
        EdgeEnumerator edgeEnumerator = edgeEnumerator().graphSize(10).nodeNum(4).numDownStreamBlackNodes(4)
                .numDownStreamNeighbours(2).numDownStreamBlackNeighbours(2).build();
        assertThat(edgeEnumerator.value(), is(nodeList(0, 1)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 2)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 3)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(1, 2)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(1, 3)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(2, 3)));

        assertThat(edgeEnumerator.advance(), is(false));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 1)));
    }

    @Test
    public void enumeratesWhiteAndBlackNeighbours() throws Exception {
        EdgeEnumerator edgeEnumerator = edgeEnumerator().graphSize(10).nodeNum(6).numDownStreamBlackNodes(3)
                .numDownStreamNeighbours(4).numDownStreamBlackNeighbours(2).build();

        assertThat(edgeEnumerator.value(), is(nodeList(0, 1, 3, 4)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 2, 3, 4)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(1, 2, 3, 4)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 1, 3, 5)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 2, 3, 5)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(1, 2, 3, 5)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 1, 4, 5)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 2, 4, 5)));
        assertThat(edgeEnumerator.advance(), is(true));
        assertThat(edgeEnumerator.value(), is(nodeList(1, 2, 4, 5)));

        assertThat(edgeEnumerator.advance(), is(false));
        assertThat(edgeEnumerator.value(), is(nodeList(0, 1, 3, 4)));
    }

    private List<Integer> nodeList(Integer... nodes) {
        return Arrays.asList(nodes);
    }
}
