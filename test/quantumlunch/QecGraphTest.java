package quantumlunch;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static quantumlunch.QecGraphBuilder.qecGraph;
import org.junit.Test;

import java.util.*;


public class QecGraphTest {

    @Test
    public void allWhiteShouldHaveDistanceOfZeor() throws Exception {
        QecGraph qec = qecGraph(2).build();
        assertThat(qec.minNeighbourCountForBlackNodes(), is(0));
        assertThat(qec.distance(), is(0));
    }

    @Test
    public void pentagonOfAllBlackShouldHaveDistanceOf3() throws Exception {
        QecGraph qec = qecGraph(5).withBlackNodes(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 3)
            .edge(3, 4)
            .edge(4, 0)
            .build();
        
        assertThat(qec.minNeighbourCountForBlackNodes(), is(2));
        assertThat(qec.distance(), is(3));
    }
    
    @Test
    public void pentagonOfAllBlackButOneShouldHaveDistanceOf2() throws Exception {
        QecGraph qec = qecGraph(5).withBlackNodes(0, 1, 2, 3)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 3)
            .edge(3, 4)
            .edge(4, 0)
            .build();
        
        assertThat(qec.minNeighbourCountForBlackNodes(), is(2));
        assertThat(qec.distance(), is(2));
    }
    
    @Test
    public void hexagonOfAllBlackShouldHaveDistanceOf2() throws Exception {
        QecGraph qec = qecGraph(6).withBlackNodes(0, 1, 2, 3, 4, 5)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 3)
            .edge(3, 4)
            .edge(4, 5)
            .edge(5, 0)
            .build();
        
        assertThat(qec.minNeighbourCountForBlackNodes(), is(2));
        assertThat(qec.distance(), is(2));
    }
    
    @Test
    public void hexagonOfAllBlackButOneShouldHaveDistanceOf2() throws Exception {
        QecGraph qec = qecGraph(6).withBlackNodes(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 3)
            .edge(3, 4)
            .edge(4, 5)
            .edge(5, 0)
            .build();
        
        assertThat(qec.minNeighbourCountForBlackNodes(), is(2));
        assertThat(qec.distance(), is(2));
    }
    
    
    @Test
    public void exampleOfDistanceFour() throws Exception {
        QecGraph qec = qecGraph(10).withBlackNodes(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 3)
            .edge(3, 4)
            .edge(4, 0)
            
            .edge(0, 5)
            .edge(1, 6)
            .edge(2, 7)
            .edge(3, 8)
            .edge(4, 9)
            .build();
        
        assertThat(qec.minNeighbourCountForBlackNodes(), is(3));
        assertThat(qec.distance(), is(4));
    }
     
    @Test
    public void identicalGraphsHaveSameHashCode() throws Exception {
        QecGraph qec1 = exampleQec().build();
        QecGraph qec2 = exampleQec().build();
        assertThat(qec1.hashCode(), is(qec2.hashCode()));
    }
    
    @Test
    public void identialGraphsAreEqual() throws Exception {
        QecGraph qec1 = exampleQec().build();
        QecGraph qec2 = exampleQec().build();
        assertThat(qec1, is(qec2));
    }

    @Test
    public void neighboursReturnsNeighbours() throws Exception {
        QecGraph graph = qecGraph(4).edge(0, 1).edge(0, 2).edge(1, 2).build();
        assertThat(asSet(graph.neighbours(0)), is(asSet(1, 2)));
        assertThat(asSet(graph.neighbours(1)), is(asSet(0, 2)));
        assertThat(asSet(graph.neighbours(2)), is(asSet(1, 0)));
    }

    private Set<Integer> asSet(Integer... values) {
        return asSet(Arrays.<Integer>asList(values));
    }

    private Set<Integer> asSet(Collection<Integer> integers) {
        return new HashSet<Integer>(integers);
    }

    private QecGraphBuilder exampleQec() {
        return qecGraph(5).withBlackNodes(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 3)
            .edge(3, 4)
            .edge(4, 0);
    }
}
