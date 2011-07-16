package quantumlunch.bestgraph.semibruteforce;

import org.junit.Test;
import quantumlunch.QecGraph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class BestGraphFinderTest {

    @Test
    public void bestGraphForThreeNodesShouldHaveDistanceOne() throws Exception {
       assertThat(new BestGraphFinder().findBestGraph(3, 1).distance(), is(1));
    }

    @Test
    public void bestGraphForFourNodesShouldHaveDistanceTwo() throws Exception {
       QecGraph graph = new BestGraphFinder().findBestGraph(4, 1);
       System.out.println(graph);
       assertThat(graph.distance(), is(2));
    }
    
    @Test
    public void bestGraphForFiveNodesShouldHaveDistanceThree() throws Exception {
        QecGraph graph = new BestGraphFinder().findBestGraph(5, 1);
        System.out.println(graph);
        assertThat(graph.distance(), is(3));
    }

//    Takes 4 seconds
    @Test
    public void bestGraphForSixNodesShouldHaveDistanceThree() throws Exception {
        QecGraph graph = new BestGraphFinder().findBestGraph(6, 3);
        System.out.println(graph);
        assertThat(graph.distance(), is(3));
    }

//    @Test
    public void bestGraphForSevenNodesShouldHaveDistanceThree() throws Exception {
        QecGraph graph = new BestGraphFinder().findBestGraph(7, 1);
        System.out.println(graph);
        assertThat(graph.distance(), is(3));
    }
}
