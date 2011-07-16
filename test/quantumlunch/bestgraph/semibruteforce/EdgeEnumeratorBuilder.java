package quantumlunch.bestgraph.semibruteforce;

import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.fixedValue;

public class EdgeEnumeratorBuilder {
    private int nodeNum;
    private int graphSize;
    private int numDownStreamBlackNodes;
    private int numDownStreamNeighbours;
    private int numDownStreamBlackNeighbours;

    static EdgeEnumeratorBuilder edgeEnumerator() {
        return new EdgeEnumeratorBuilder();
    }

     EdgeEnumerator build() {
         return new EdgeEnumerator(nodeNum, graphSize, fixedValue(numDownStreamBlackNodes), fixedValue(numDownStreamNeighbours),
                 fixedValue(numDownStreamBlackNeighbours));
     }

    EdgeEnumeratorBuilder nodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
        return this;
    }

    EdgeEnumeratorBuilder graphSize(int graphSize) {
        this.graphSize = graphSize;
        return this;
    }

    EdgeEnumeratorBuilder numDownStreamBlackNodes(int numDownStreamBlackNodes) {
        this.numDownStreamBlackNodes = numDownStreamBlackNodes;
        return this;
     }

    EdgeEnumeratorBuilder numDownStreamNeighbours(int numDownStreamNeighbours) {
        this.numDownStreamNeighbours = numDownStreamNeighbours;
        return this;
    }

    EdgeEnumeratorBuilder numDownStreamBlackNeighbours(int numDownStreamBlackNeighbours) {
        this.numDownStreamBlackNeighbours = numDownStreamBlackNeighbours;
        return this;
    }
}
