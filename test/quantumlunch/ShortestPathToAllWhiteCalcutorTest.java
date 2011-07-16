package quantumlunch;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static quantumlunch.QecGraphBuilder.qecGraph;


public class ShortestPathToAllWhiteCalcutorTest {

    @Test
    public void pathOfLengthOneExample() throws Exception {
        QecGraph qec = qecGraph(5).edge(0, 1).edge(1, 2).build();

        List<QecGraph> path = new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(qec);
        assertThat(path, is(Collections.singletonList(qec)));
    }
    
    @Test
    public void pathOfLengthTwoExample() throws Exception {
        QecGraph start = qecGraph(5).withBlackNodes(0).edge(0, 1).edge(1, 2).build();
        QecGraph finish = qecGraph(5).withBlackNodes().edge(0, 1).edge(1, 2).build();
        
        List<QecGraph> path = new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(start);
        assertThat(path, is(Arrays.asList(start, finish)));
    }
    
    
    @Test
    public void pathOfLengthThreeExample() throws Exception {
        QecGraph start = qecGraph(4).withBlackNodes(0,1,2,3)
            .edge(0, 1).edge(1, 2).edge(2, 3).edge(3, 0).build();
        
        List<QecGraph> path = new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(start);
        assertThat(path.get(0), is(start));
        assertThat(path.size(), is(3));
        assertTrue(path.get(2).isAllWhite());
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
        
        assertThat(new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(qec).size(), is(4));
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
        
        assertThat(new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(qec).size(), is(3));
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
        
        assertThat(new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(qec).size(), is(3));
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
        
        assertThat(new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(qec).size(), is(3));
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
        
        assertThat(new ShortestPathToAllWhiteCalcutor().shortestPathToAllWhite(qec).size(), is(5));
    }

}
