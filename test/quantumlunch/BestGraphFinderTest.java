package quantumlunch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;


public class BestGraphFinderTest {

    @Test
    public void bestGraphForThreeNodesShouldHaveDistanceOne() throws Exception {
       assertThat(new BestGraphFinder().findBestGraph(3).distance(), is(1));
    }

    @Test
    public void bestGraphForFourNodesShouldHaveDistanceTwo() throws Exception {
       QecGraph graph = new BestGraphFinder().findBestGraph(4);
       System.out.println(graph);
       assertThat(graph.distance(), is(2));
    }
    
    @Test
    public void bestGraphForFiveNodesShouldHaveDistanceThree() throws Exception {
        QecGraph graph = new BestGraphFinder().findBestGraph(5);
        System.out.println(graph);
        assertThat(graph.distance(), is(3));
    }
}
