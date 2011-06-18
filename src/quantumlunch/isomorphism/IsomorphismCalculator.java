package quantumlunch.isomorphism;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.HashCodeBuilder;
import quantumlunch.QecGraph;

// Warning: Not thread safe.
public class IsomorphismCalculator {

    private final QecGraph qecGraph;
    private final int size;

    // NOTE: These are lazily populated and hence not thread safe.
    private int numberOfBlackNodes = -1;
    private Map<NodeCharacterisation, Set<Integer>> characterisationToNodesMapping;
    private Map<Integer, NodeCharacterisation> nodeToCharacterisationMapping;

    public IsomorphismCalculator(QecGraph qecGraph) {
        this.qecGraph = qecGraph;
        this.size = qecGraph.getSize();
        numberOfBlackNodes = qecGraph.getNumberOfBlackNodes();
    }

    public boolean isomorphicTo(IsomorphismCalculator rhs) {
        return size == rhs.size
                && numberOfBlackNodes == rhs.numberOfBlackNodes
                && characterisationSetSizesMatch(rhs)
                && nodesCanBeMapped(rhs);
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

    private boolean characterisationSetSizesMatch(IsomorphismCalculator rhs) {
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

    private boolean nodesCanBeMapped(IsomorphismCalculator rhs) {
        createNodeToCharacterisationMapping();
        rhs.createNodeToCharacterisationMapping();
        Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();
        Set<Integer> mappedLhsNodes = new HashSet<Integer>();
        Set<Integer> mappedRhsNodes = new HashSet<Integer>();
        Set<Integer> leadingEdgeNodes = new HashSet<Integer>();
        return nodesCanBeMapped(rhs, mapping, mappedLhsNodes, mappedRhsNodes, leadingEdgeNodes);
    }

    private boolean nodesCanBeMapped(IsomorphismCalculator rhs, Map<Integer, Integer> mapping, Set<Integer> mappedLhsNodes, Set<Integer> mappedRhsNodes,
                                     Set<Integer> leadingEdgeNodes) {
        assert mapping.size() == mappedLhsNodes.size() && mapping.size() == mappedRhsNodes.size():
                String.format("Mapping sizes are different: %s, %s, %s", mapping.size(), mappedLhsNodes.size(), mappedRhsNodes.size());
        if (mapping.size() == size) return true;
        Integer node = chooseNode(mappedLhsNodes, leadingEdgeNodes);
        mappedLhsNodes.add(node);
        Set<Integer> newLeadingEdgeNodes = new HashSet<Integer>(leadingEdgeNodes);
        newLeadingEdgeNodes.addAll(qecGraph.neighbours(node));
        newLeadingEdgeNodes.removeAll(mappedLhsNodes);
        Iterable<Integer> rhsCandidates = findCandidateMappings(node, rhs, mapping, mappedRhsNodes);
        for (Integer rhsNode : rhsCandidates) {
            mapping.put(node, rhsNode);
            mappedRhsNodes.add(rhsNode);
            if (nodesCanBeMapped(rhs, mapping, mappedLhsNodes, mappedRhsNodes, newLeadingEdgeNodes)) {
                return true;
            }
            mapping.remove(node);
            mappedRhsNodes.remove(rhsNode);
        }
        mappedLhsNodes.remove(node);
        return false;
    }

    private Iterable<Integer> findCandidateMappings(Integer node, IsomorphismCalculator rhs, Map<Integer, Integer> mapping, Set<Integer> mappedRhsNodes) {
        Set<Integer> candidateMappings = findCandidateMappingsBasedOnNodeCharacterisation(node, rhs);
        candidateMappings.removeAll(mappedRhsNodes);
        return filterAgainstExistingMappings(node, rhs, mapping, candidateMappings);
    }

    private Set<Integer> filterAgainstExistingMappings(Integer node, IsomorphismCalculator rhs, Map<Integer, Integer> mapping, Set<Integer> candidateMappings) {
        Collection<Integer> neighbours = qecGraph.neighbours(node);
        Set<Integer> filteredCandidates = new HashSet<Integer>();
        for (Integer candidate : candidateMappings) {
            if (agreesWithExistingMappings(candidate, neighbours, rhs, mapping)) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    private boolean agreesWithExistingMappings(Integer candidate, Collection<Integer> neighbours, IsomorphismCalculator rhs, Map<Integer, Integer> mapping) {
        for (Integer neighbour : neighbours) {
            Integer mappedNeighbour = mapping.get(neighbour);
            if (mappedNeighbour != null && !rhs.qecGraph.edge(candidate, mappedNeighbour)) {
                return false;
            }
        }
        return true;
    }

    private Set<Integer> findCandidateMappingsBasedOnNodeCharacterisation(Integer node, IsomorphismCalculator rhs) {
        NodeCharacterisation nodeCharacterisation = nodeToCharacterisationMapping.get(node);
        return rhs.characterisationToNodesMapping.get(nodeCharacterisation);
    }

    private Integer chooseNode(Set<Integer> mappedLhsNodes, Set<Integer> leadingEdgeNodes) {
        if (! leadingEdgeNodes.isEmpty()) {
            return leadingEdgeNodes.iterator().next();
        }
        for (int node = 0; node < size; node++) {
            if (!mappedLhsNodes.contains(node)) return node;
        }
        throw new IllegalStateException("Buggy Code.  Should never reach this line!");
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

    public int isomorphismHashCode() {
        populateCharacterisationMappings();;
        assert numberOfBlackNodes >= 0 && characterisationToNodesMapping != null : "Buggy code.  Not initialized properly";
        return new HashCodeBuilder().append(numberOfBlackNodes).append(characterisationToNodesMapping.keySet()).toHashCode();
    }
}
