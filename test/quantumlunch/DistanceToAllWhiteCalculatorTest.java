package quantumlunch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static quantumlunch.QecGraphBuilder.qecGraph;

import org.junit.Test;


public class DistanceToAllWhiteCalculatorTest {
    @Test
    public void pentagonOfAllBlackShouldHaveDistanceOf3() throws Exception {
        QecGraph qec = qecGraph(5).withBlackNodes(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 3)
            .edge(3, 4)
            .edge(4, 0)
            .build();
        
        assertThat(new DistanceToAllWhiteCalculator().distanceToAllWhite(qec), is(3));
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
        
        assertThat(new DistanceToAllWhiteCalculator().distanceToAllWhite(qec), is(2));
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
        
        assertThat(new DistanceToAllWhiteCalculator().distanceToAllWhite(qec), is(2));
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
        
        assertThat(new DistanceToAllWhiteCalculator().distanceToAllWhite(qec), is(2));
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
        
        assertThat(new DistanceToAllWhiteCalculator().distanceToAllWhite(qec), is(4));
    }

}
