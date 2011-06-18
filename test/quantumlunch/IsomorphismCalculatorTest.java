package quantumlunch;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static quantumlunch.QecGraphBuilder.qecGraph;

import org.junit.Test;
import quantumlunch.isomorphism.IsomorphismCalculator;


public class IsomorphismCalculatorTest {

    @Test
    public void differentSizedGraphsAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(1).build();
        QecGraph qec2 = qecGraph(2).build();
        assertAreNotIsomorphic(qec1, qec2);
    }
    
    @Test
    public void graphsWithDifferentNumbersOfBlackNodesAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(3).withBlackNodes(0).build();
        QecGraph qec2 = qecGraph(3).withBlackNodes(0, 1).build();
        assertAreNotIsomorphic(qec1, qec2);
    }
    
    @Test
    public void graphsWithDifferentNeighbourFrequenciesAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(3).edge(0,1).edge(0,2).build();
        QecGraph qec2 = qecGraph(3).edge(0,1).edge(0,2).edge(2,1).build();
        assertAreNotIsomorphic(qec1, qec2);
        assertThat(new IsomorphismCalculator(qec1).isomorphismHashCode(), is(not(new IsomorphismCalculator(qec2).isomorphismHashCode())));
    }
    
    @Test
    public void graphsWithDifferentNeighbourFrequenciesPerColourAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(3).withBlackNodes(0).edge(0,1).edge(0,2).build();
        QecGraph qec2 = qecGraph(3).withBlackNodes(1).edge(0,1).edge(0,2).build();
        assertAreNotIsomorphic(qec1, qec2);
        assertThat(new IsomorphismCalculator(qec1).isomorphismHashCode(), is(not(new IsomorphismCalculator(qec2).isomorphismHashCode())));
    }
    
    @Test
    public void graphsWithDifferentPathsAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(6).edge(0,1).edge(1,2).edge(2,3).edge(3,0)
                            .edge(0,4).edge(1,5).build();
        QecGraph qec2 = qecGraph(6).edge(0,1).edge(1,2).edge(2,3).edge(3,0)
                            .edge(0,4).edge(2,5).build();
        assertAreNotIsomorphic(qec1, qec2);
    }
    
    @Test
    public void identicalGraphsAreIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(1).build();
        QecGraph qec2 = qecGraph(1).build();
        assertAreIsomophic(qec1, qec2);
    }
    
    @Test
    public void similarGraphsAreIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(6).edge(0,1).edge(1,2).edge(2,3).edge(3,0)
            .edge(0,4).edge(1,5).build();
        QecGraph qec2 = qecGraph(6).edge(5,1).edge(1,2).edge(2,3).edge(3,5)
            .edge(5,4).edge(1,0).build();
        assertAreIsomophic(qec1, qec2);
    }

    @Test
    public void graphsThatAreSimilarOtherThanLocationOfBlackNodesAreNotIsomorphic() throws Exception {
        QecGraph qec1 = qecGraph(7).withBlackNodes(2).edge(0,1).edge(1,2).edge(2,3).edge(3, 4).edge(5,6).build();
        QecGraph qec2 = qecGraph(7).withBlackNodes(3).edge(0,1).edge(1,2).edge(2,3).edge(3, 4).edge(5,6).build();
        assertAreNotIsomorphic(qec1, qec2);
    }

    private void assertAreIsomophic(QecGraph qec1, QecGraph qec2) {
        IsomorphismCalculator calculator1 = new IsomorphismCalculator(qec1);
        IsomorphismCalculator calculator2 = new IsomorphismCalculator(qec2);
        assertThat(calculator2.isomorphicTo(calculator1), is(true));
        assertThat(calculator1.isomorphismHashCode(), is(calculator2.isomorphismHashCode()));
    }

    private void assertAreNotIsomorphic(QecGraph qec1, QecGraph qec2) {
        IsomorphismCalculator calculator1 = new IsomorphismCalculator(qec1);
        IsomorphismCalculator calculator2 = new IsomorphismCalculator(qec2);
        assertThat(calculator2.isomorphicTo(calculator1), is(false));
    }
}
