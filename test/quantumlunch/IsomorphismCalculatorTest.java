package quantumlunch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static quantumlunch.QecGraphBuilder.qecGraph;

import org.junit.Test;


public class IsomorphismCalculatorTest {

    @Test
    public void differentSizedGraphsAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(1).build();
        QecGraph qec2 = qecGraph(2).build();
        assertThat(qec1.isomorphicTo(qec2), is(false));
    }
    
    @Test
    public void graphsWithDifferentNumbersOfBlackNodesAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(3).withBlackNodes(0).build();
        QecGraph qec2 = qecGraph(3).withBlackNodes(0, 1).build();
        assertThat(qec1.isomorphicTo(qec2), is(false));
    }
    
    @Test
    public void graphsWithDifferentNeighbourFrequenciesAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(3).edge(0,1).edge(0,2).build();
        QecGraph qec2 = qecGraph(3).edge(0,1).edge(0,2).edge(2,1).build();
        assertThat(qec1.isomorphicTo(qec2), is(false));
    }
    
    @Test
    public void graphsWithDifferentNeighbourFrequenciesPerColourAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(3).withBlackNodes(0).edge(0,1).edge(0,2).build();
        QecGraph qec2 = qecGraph(3).withBlackNodes(1).edge(0,1).edge(0,2).build();
        assertThat(qec1.isomorphicTo(qec2), is(false));
    }
    
    @Test
    public void graphsWithDifferentPathsAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(6).edge(0,1).edge(1,2).edge(2,3).edge(3,0)
                            .edge(0,4).edge(1,5).build();
        QecGraph qec2 = qecGraph(6).edge(0,1).edge(1,2).edge(2,3).edge(3,0)
                            .edge(0,4).edge(2,5).build();
        assertThat(qec1.isomorphicTo(qec2), is(false));
    }
    
    @Test
    public void identicalGraphsAreIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(1).build();
        QecGraph qec2 = qecGraph(1).build();
        assertThat(qec1.isomorphicTo(qec2), is(true));
    }
    
    @Test
    public void similarGraphsAreIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(6).edge(0,1).edge(1,2).edge(2,3).edge(3,0)
            .edge(0,4).edge(1,5).build();
        QecGraph qec2 = qecGraph(6).edge(5,1).edge(1,2).edge(2,3).edge(3,5)
            .edge(5,4).edge(1,0).build();
        assertThat(qec1.isomorphicTo(qec2), is(true));
    }
}
