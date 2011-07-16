package quantumlunch.bestgraph.semibruteforce;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.fixedValue;

/**
 * Notes:
 * Assumes that "down stream nodes" are those whose id's are less than this one's.
 * All white nodes have id's that are less than those of the black nodes.  eg. if this is node number 5, and there are
 * 3 down stream black nodes, then nodes 2, 3, and 4 are black, and nodes 0 and 1 are white.
 * Advancing this enumerator will eventually return it back to the beginning (roll over).
 * At the point of roll over, the number of down stream black nodes, neighbours, and black neighbours may change, and hence
 * this enumerator is supplied with ValueSource objects for these instead of constants.
 */
public class EdgeEnumerator implements StateEnumerator {
    private final int nodeNum;
    private final int graphSize;
    private final ValueSource numDownStreamBlackNodesValueSource;
    private final ValueSource numDownStreamNeighboursValueSource;
    private final ValueSource numDownStreamBlackNeighboursValueSource;

    private boolean needsRollOver;
    private int numDownStreamBlackNodes;
    private int numDownstreamBlackNeighbours;
    private int numDownstreamWhiteNeighbours;
    private int numDownstreamNeighbours;
    private IntegerEnumerator[] whiteNodeEnumerators;
    private IntegerEnumerator[] blackNodeEnumerators;

    public EdgeEnumerator(int nodeNum, int graphSize, ValueSource numDownStreamBlackNodesValueSource, ValueSource numDownStreamNeighboursValueSource,
                          ValueSource numDownStreamBlackNeighboursValueSource) {
        this.nodeNum = nodeNum;
        this.graphSize = graphSize;
        this.numDownStreamBlackNodesValueSource = numDownStreamBlackNodesValueSource;
        this.numDownStreamNeighboursValueSource = numDownStreamNeighboursValueSource;
        this.numDownStreamBlackNeighboursValueSource = numDownStreamBlackNeighboursValueSource;
        rollOver();
        assert isConsistent();
    }

    private void rollOver() {
        // Grab initial values.  Will potentially change after each roll-over.
        this.numDownStreamBlackNodes = this.numDownStreamBlackNodesValueSource.value();
        this.numDownstreamBlackNeighbours = this.numDownStreamBlackNeighboursValueSource.value();
        this.numDownstreamNeighbours = this.numDownStreamNeighboursValueSource.value();
        this.numDownstreamWhiteNeighbours = numDownstreamNeighbours - numDownstreamBlackNeighbours;

        this.whiteNodeEnumerators = createWhiteNodeEnumerators();
        this.blackNodeEnumerators = createBlackNodeEnumerators();
        this.needsRollOver = false;
    }

    private IntegerEnumerator[] createBlackNodeEnumerators() {
        IntegerEnumerator[] enumerators = new IntegerEnumerator[numDownstreamBlackNeighbours];
        if (numDownstreamBlackNeighbours < 1) return enumerators;
        enumerators[0] = new IntegerEnumerator(fixedValue(nodeNum - numDownStreamBlackNodes), fixedValue(nodeNum - numDownstreamBlackNeighbours));
        for(int i = 1; i < numDownstreamBlackNeighbours; i++) {
            enumerators[i] = new IntegerEnumerator(addOneTo(enumerators[i - 1]), fixedValue(nodeNum - numDownstreamBlackNeighbours + i));
        }
        return enumerators;
    }

    private IntegerEnumerator[] createWhiteNodeEnumerators() {
        IntegerEnumerator[] enumerators = new IntegerEnumerator[numDownstreamWhiteNeighbours];
        if (numDownstreamWhiteNeighbours < 1) return enumerators;
        int numOfDownstreamWhiteNodes = nodeNum - numDownStreamBlackNodes;
        enumerators[0] = new IntegerEnumerator(fixedValue(0), fixedValue(numOfDownstreamWhiteNodes - numDownstreamWhiteNeighbours));
        for(int i = 1; i < numDownstreamWhiteNeighbours; i++) {
            enumerators[i] = new IntegerEnumerator(addOneTo(enumerators[i - 1]), fixedValue(numOfDownstreamWhiteNodes - numDownstreamWhiteNeighbours + i));
        }
        return enumerators;
    }

    private ValueSource addOneTo(final IntegerEnumerator enumerator) {
        return new ValueSource() {
            public Integer value() {
                return enumerator.value() + 1;
            }
        };
    }

    public boolean advance() {
        if (needsRollOver) {
            rollOver();
        }
        for(int i = whiteNodeEnumerators.length - 1; i >= 0; i--) {
            if (whiteNodeEnumerators[i].advance()) return true;
        }
        for(int i = blackNodeEnumerators.length - 1; i >= 0; i--) {
            if (blackNodeEnumerators[i].advance()) return true;
        }
        assert isConsistent();
        needsRollOver = true;
        return false;
    }

    public List<Integer> value() {
        if (needsRollOver) {
            rollOver();
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (IntegerEnumerator integerEnumerator : whiteNodeEnumerators) {
            result.add(integerEnumerator.value());
        }
        for (IntegerEnumerator integerEnumerator : blackNodeEnumerators) {
            result.add(integerEnumerator.value());
        }
        return result;
    }

    private boolean isConsistent() {
        assert numDownStreamBlackNodes >= numDownstreamBlackNeighbours : "numDownStreamBlackNodes must be at least numDownstreamBlackNeighbours " + this;
        assert numDownstreamNeighbours >= numDownstreamBlackNeighbours : "numDownstreamNeighbours must be at least numDownstreamBlackNeighbours" + this;
        assert this.graphSize > numDownStreamBlackNodes : "graphSize must be at least numDownstreamBlackNodes " + this;
        assert this.graphSize > numDownstreamNeighbours : "graphSize must be at least numDownstreamNeighbours " + this;
        assert this.nodeNum >= numDownstreamNeighbours : this.toString();
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
