package quantumlunch.isomorphism;

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import quantumlunch.QecGraph;

// Warning: Not thread safe.
public class IsomorphismDescription {

    private final QecGraph qecGraph;
    private final int size;

    // NOTE: These are lazily populated and hence not thread safe.
    private int numberOfBlackNodes = -1;
    private Map<NodeCharacterisation, Set<Integer>> characterisationToNodesMapping;
    private Map<Integer, NodeCharacterisation> nodeToCharacterisationMapping;

    public IsomorphismDescription(QecGraph qecGraph) {
        this.qecGraph = qecGraph;
        this.size = qecGraph.getSize();
    }

    public boolean isomorphicTo(IsomorphismDescription rhs) {
        return size == rhs.size
            && numberOfBlackNodes() == rhs.numberOfBlackNodes()
            && characterisationSetSizesMatch(rhs)
            && nodesCanBeMapped(rhs);
    }

    private int numberOfBlackNodes() {
        if (numberOfBlackNodes < 0)
            numberOfBlackNodes = qecGraph.getNumberOfBlackNodes();
        return numberOfBlackNodes;
    }

    private void populateCharacterisationMappings() {
        if (characterisationToNodesMapping != null) return;
        characterisationToNodesMapping = new HashMap<NodeCharacterisation, Set<Integer>>();
        for (int node = 0; node < size; node++) {
            int blackNeighbourCount = 0;
            int whiteNeighbourCount = 0;
            for (int possibleNeighbour = 0; possibleNeighbour < size; possibleNeighbour++) {
                if (qecGraph.edge(node, possibleNeighbour)) {
                    if (qecGraph.isBlack(possibleNeighbour)) {
                        blackNeighbourCount++;
                    } else {
                        whiteNeighbourCount++;
                    }
                }
            }
            addToCharacterisationMappings(node, qecGraph.isBlack(node), blackNeighbourCount, whiteNeighbourCount);
        }
    }

    private void addToCharacterisationMappings(Integer node, boolean isBlack, int blackNeighbours, int whiteNeighbours) {
        NodeCharacterisation nodeCharacterisation = new NodeCharacterisation(isBlack, whiteNeighbours, blackNeighbours);
        Set<Integer> nodes = characterisationToNodesMapping.get(nodeCharacterisation);
        if (nodes == null) {
            nodes = new HashSet<Integer>();
            characterisationToNodesMapping.put(nodeCharacterisation, nodes);
        }
        nodes.add(node);
    }

    private boolean characterisationSetSizesMatch(IsomorphismDescription rhs) {
        populateCharacterisationMappings();
        rhs.populateCharacterisationMappings();
        for (Entry<NodeCharacterisation, Set<Integer>> entry : characterisationToNodesMapping.entrySet()) {
            Set<Integer> rhsSet = rhs.characterisationToNodesMapping.get(entry.getKey());
            if (rhsSet == null || entry.getValue().size() != rhsSet.size()) {
                return false;
            }
        }
        return true;
    }

    private boolean nodesCanBeMapped(IsomorphismDescription rhs) {
        createNodeToCharacterisationMapping();
        rhs.createNodeToCharacterisationMapping();
        return true;
    }

    private void createNodeToCharacterisationMapping() {
        if (nodeToCharacterisationMapping != null) return;
        nodeToCharacterisationMapping = new HashMap<Integer, NodeCharacterisation>();
        for (Entry<NodeCharacterisation, Set<Integer>> entry : characterisationToNodesMapping.entrySet()) {
            NodeCharacterisation characterisation = entry.getKey();
            for (Integer node : entry.getValue()) {
                nodeToCharacterisationMapping.put(node, characterisation);
            }
        }
        assert nodeToCharacterisationMapping.size() == size : "Bug detected.  Should never happen";
    }

}
