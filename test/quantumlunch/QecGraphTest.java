package quantumlunch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static quantumlunch.QecGraphBuilder.qecGraph;
import org.junit.Test;


public class QecGraphTest {

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
	public void exampleOfDistanceFive() throws Exception {
		QecGraph qec = qecGraph(11).withBlackNodes(0, 1, 2, 3, 4)
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
			
			.edge(0, 6)
			.edge(1, 7)
			.edge(2, 8)
			.edge(3, 9)
			.edge(4, 5)
			
			.edge(5, 6)
			.edge(6, 7)
			.edge(7, 8)
			.edge(8, 9)
			.edge(9, 5)
			
			.edge(5, 10)
			.edge(6, 10)
			.edge(7, 10)
			.edge(8, 10)
			.edge(9, 10)
			.build();
		
		assertThat(qec.minNeighbourCountForBlackNodes(), is(4));
		assertThat(qec.distance(), is(5));
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

	private QecGraphBuilder exampleQec() {
		return qecGraph(5).withBlackNodes(0, 1, 2, 3, 4)
			.edge(0, 1)
			.edge(1, 2)
			.edge(2, 3)
			.edge(3, 4)
			.edge(4, 0);
	}
}
