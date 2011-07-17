package quantumlunch.bestgraph.semibruteforce;

import org.apache.commons.lang.builder.ToStringBuilder;
import quantumlunch.QecGraph;
import quantumlunch.QecGraphBuilder;

import java.util.List;

import static quantumlunch.QecGraphBuilder.qecGraph;
import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.ZERO;
import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.fixedValue;
import static quantumlunch.bestgraph.semibruteforce.MinimumValueSource.minimumValue;

final class GraphDescription {
    private final int size;
    private final int minDistance;
    private final IntegerEnumerator[] colourEnumerators;
    private final IntegerEnumerator[] numDownStreamNeighboursEnumerators;
    private final IntegerEnumerator[] numDownStreamBlackNeighboursEnumerators;
    private final EdgeEnumerator[] edgeEnumerators;
    private final StateEnumerator[][] allEnumerators;
    private boolean rollingOver = false;

    public GraphDescription(int size, int minDistance) {
        this.size = size;
        this.minDistance = minDistance;
        this.colourEnumerators = new IntegerEnumerator[size];
        this.numDownStreamNeighboursEnumerators = new IntegerEnumerator[size];
        this.numDownStreamBlackNeighboursEnumerators = new IntegerEnumerator[size];
        this.edgeEnumerators = new EdgeEnumerator[size];
        populateColourEnumerators();
        populateNumDownStreamNeighboursEnumerators();
        populateNumDownStreamBlackNeighboursEnumerators();
        populateEdgeEnumerators();
        allEnumerators =
                new StateEnumerator[][] {edgeEnumerators, numDownStreamBlackNeighboursEnumerators, numDownStreamNeighboursEnumerators, colourEnumerators};
    }

    private void populateColourEnumerators() {
        IntegerEnumerator colourEnumerator = null;
        for (int nodeNum = size - 1; nodeNum > size - minDistance - 1; nodeNum--) {
            colourEnumerator = new IntegerEnumerator(fixedValue(1), fixedValue(1));
            colourEnumerators[nodeNum] = colourEnumerator;
        }
        for (int nodeNum = size - minDistance - 1; nodeNum >= 0; nodeNum--) {
            MinimumValueSource upperLimit = minimumValue(fixedValue(1), colourEnumerator);
            colourEnumerator = new IntegerEnumerator(upperLimit);
            colourEnumerators[nodeNum] = colourEnumerator;
        }
    }

    private void populateNumDownStreamNeighboursEnumerators() {
        IntegerEnumerator numDownStreamNeighboursEnumerator = null;
        int minDistanceEnforcedMinimumNumberOfDownstreamNeighbours = minDistance - 1;
        for (int nodeNum = size - 1; nodeNum >= 0; nodeNum--) {
            ValueSource upperLimit = minimumValue(fixedValue(nodeNum), numDownStreamNeighboursEnumerator);
            ValueSource lowerLimit = minDistanceEnforcedMinimumNumberOfDownstreamNeighbours > 0 ?
                    fixedValue(minDistanceEnforcedMinimumNumberOfDownstreamNeighbours) : ZERO;
            minDistanceEnforcedMinimumNumberOfDownstreamNeighbours--;
            numDownStreamNeighboursEnumerator = new IntegerEnumerator(lowerLimit, upperLimit);
            this.numDownStreamNeighboursEnumerators[nodeNum] = numDownStreamNeighboursEnumerator;
        }
    }

    private void populateNumDownStreamBlackNeighboursEnumerators() {
        IntegerEnumerator numDownStreamBlackNeighboursEnumerator = null;
        for (int nodeNum = size - 1; nodeNum >= 0; nodeNum--) {
            ValueSource upperLimit = minimumValue(numDownStreamBlackNeighboursEnumerator,
                    numDownStreamNeighboursEnumerators[nodeNum],
                    numberOfDownStreamBlackNodesValueSource(nodeNum));
            ValueSource lowerLimit = minimumNumberOfDownStreamBlackNeighboursValueSource(nodeNum);
            numDownStreamBlackNeighboursEnumerator = new IntegerEnumerator(lowerLimit, upperLimit);
            this.numDownStreamBlackNeighboursEnumerators[nodeNum] = numDownStreamBlackNeighboursEnumerator;
        }
    }

    private ValueSource minimumNumberOfDownStreamBlackNeighboursValueSource(final int nodeNum) {
        return new ValueSource() {
            public Integer value() {
                int min = numDownStreamNeighboursEnumerators[nodeNum].value() + numberOfDownStreamBlackNodesValueSource(nodeNum).value() - nodeNum;
                return min < 0 ? 0 : min;
            }
        };
    }

    private ValueSource numberOfDownStreamBlackNodesValueSource(final int nodeNum) {
        return new ValueSource() {
            public Integer value() {
                int result = 0;
                for(int downStreamNodeNum = nodeNum - 1; downStreamNodeNum >= 0; downStreamNodeNum --) {
                    if (colourEnumerators[downStreamNodeNum].value() > 0) result++;
                }
                return result;
            }
        };
    }

    private void populateEdgeEnumerators() {
        for (int nodeNum = 0; nodeNum < size; nodeNum++) {
            edgeEnumerators[nodeNum] = new EdgeEnumerator(nodeNum, size, numberOfDownStreamBlackNodesValueSource(nodeNum), numDownStreamNeighboursEnumerators[nodeNum], numDownStreamBlackNeighboursEnumerators[nodeNum]);
        }
    }

    public QecGraph next() {
        QecGraph result = createGraph();
        if (rollingOver) {
            rollingOver = false;
            return result;
        }
        rollingOver = !advance();
        return rollingOver ? null : result;
    }

    private boolean advance() {
        for (StateEnumerator[] enumerators : allEnumerators) {
            for(StateEnumerator enumerator : enumerators) {
                if (enumerator.advance()) return true;
            }
        }
        return false;
    }

    private QecGraph createGraph() {
        QecGraphBuilder graphBuilder = qecGraph(size);
        for (int node = 0; node < size; node++) {
            if (colourEnumerators[node].value() == 1) graphBuilder.withBlackNodes(node);
        }
        for (int node = 0; node < size; node++) {
            EdgeEnumerator edgeEnumerator = edgeEnumerators[node];
            List<Integer> edges = edgeEnumerator.getEdges();
            for (int otherNode: edges) {
                graphBuilder.edge(node, otherNode);
            }
        }
        return graphBuilder.build();
    }

    IntegerEnumerator[] getColourEnumerators() {
        return colourEnumerators;
    }

    IntegerEnumerator[] getNumDownStreamNeighboursEnumerators() {
        return numDownStreamNeighboursEnumerators;
    }

    IntegerEnumerator[] getNumDownStreamBlackNeighboursEnumerators() {
        return numDownStreamBlackNeighboursEnumerators;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    int getSize() {
        return size;
    }

    EdgeEnumerator[] getEdgeEnumerators() {
        return edgeEnumerators;
    }
}
