package quantumlunch.bestgraph.semibruteforce;

import quantumlunch.QecGraph;

import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.fixedValue;

class GraphDescription {
    private final int size;
    private final int minDistance;
    private final IntegerEnumerator[] colourEnumerators;
    private final IntegerEnumerator[] numDownStreamNeighboursEnumerators;
    private final IntegerEnumerator[] numDownStreamBlackNeighboursEnumerators;
    private final StateEnumerator[][] allEnumerators;
    private boolean finished = false;

    public GraphDescription(int size, int minDistance) {
        this.size = size;
        this.minDistance = minDistance;
        this.colourEnumerators = new IntegerEnumerator[size];
        this.numDownStreamNeighboursEnumerators = new IntegerEnumerator[size];
        this.numDownStreamBlackNeighboursEnumerators = new IntegerEnumerator[size];
        populateColourEnumerators();
        populateNumDownStreamNeighboursEnumerators();
        populateNumDownStreamBlackNeighboursEnumerators();
        allEnumerators = new IntegerEnumerator[][] {numDownStreamBlackNeighboursEnumerators, numDownStreamNeighboursEnumerators, colourEnumerators};
    }

    private void populateColourEnumerators() {
        IntegerEnumerator colourEnumerator = null;
        for (int nodeNum = size - 1; nodeNum >= 0; nodeNum--) {
            colourEnumerator = new IntegerEnumerator(fixedValue(1), colourEnumerator);
            colourEnumerators[nodeNum] = colourEnumerator;
        }
    }

    private void populateNumDownStreamNeighboursEnumerators() {
        IntegerEnumerator numDownStreamNeighboursEnumerator = null;
        for (int nodeNum = size - 1; nodeNum >= 0; nodeNum--) {
            numDownStreamNeighboursEnumerator = new IntegerEnumerator(fixedValue(nodeNum), numDownStreamNeighboursEnumerator);
            this.numDownStreamNeighboursEnumerators[nodeNum] = numDownStreamNeighboursEnumerator;
        }
    }

    private void populateNumDownStreamBlackNeighboursEnumerators() {
        IntegerEnumerator numDownStreamBlackNeighboursEnumerator = null;
        for (int nodeNum = size - 1; nodeNum >= 0; nodeNum--) {
            numDownStreamBlackNeighboursEnumerator =
                    new IntegerEnumerator(numDownStreamBlackNeighboursEnumerator, numDownStreamNeighboursEnumerators[nodeNum], numberOfDownStreamBlackNodesCalculator(nodeNum));
            this.numDownStreamBlackNeighboursEnumerators[nodeNum] = numDownStreamBlackNeighboursEnumerator;
        }
    }

    private ValueSource numberOfDownStreamBlackNodesCalculator(final int nodeNum) {
        return new ValueSource() {
            public Long value() {
                long result = 0;
                for(int downStreamNodeNum = nodeNum - 1; downStreamNodeNum >= 0; downStreamNodeNum --) {
                    if (colourEnumerators[downStreamNodeNum].value() > 0) result++;
                }
                return result;
            }
        };
    }

    public QecGraph next() {
        if (finished) {
            return null;
        }
        QecGraph result = createGraph();
        finished = !advance();
        return result;
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
        return null;  //To change body of created methods use File | Settings | File Templates.
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
}
