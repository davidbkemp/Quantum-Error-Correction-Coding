package quantumlunch.bestgraph.bruteforce;

import org.junit.Test;
import quantumlunch.QecGraph;
import quantumlunch.bestgraph.bruteforce.BestGraphFinder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


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

//    Takes 14 seconds
//    @Test
    public void bestGraphForSixNodesShouldHaveDistanceThree() throws Exception {
        QecGraph graph = new BestGraphFinder().findBestGraph(6);
        System.out.println(graph);
        assertThat(graph.distance(), is(3));
    }
    
    //2000 seconds
//    @Test
    public void bestGraphForSevenNodesShouldHaveDistanceThree() throws Exception {
        QecGraph graph = new BestGraphFinder().findBestGraph(7);
        System.out.println(graph);
        assertThat(graph.distance(), is(3));
    }
}
