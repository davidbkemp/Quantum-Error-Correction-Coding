package quantumlunch.isomorphism;

import quantumlunch.QecGraph;

import java.util.*;
import java.util.Map.Entry;

// Not Thread safe.
public class IsomorphismCalculator {

    private final QecGraph qecGraph;
    private final int size;

    // Warning: These are all lazily calculated.
    private int numberOfBlackNodes = -1;
    private Map<NodeCharacterisation, Set<Integer>> characterisationToNodesMapping;
    private Map<Integer, NodeCharacterisation> nodeToCharacterisationMapping;
    private int isomorphicHashCode;

    public IsomorphismCalculator(QecGraph qecGraph) {
        this.qecGraph = qecGraph;
        size = qecGraph.getSize();
    }

    public boolean isomorphicTo(IsomorphismCalculator rhs) {
        assert mappingInvariantsSatisfied() : "Bug detected.  Should never happen";
        return size == rhs.size
                && getNumberOfBlackNodes() == rhs.getNumberOfBlackNodes()
                && characterisationSetSizesMatch(rhs)
                && nodesCanBeMapped(rhs);
    }

    private Map<NodeCharacterisation, Set<Integer>> createCharacterisationMappings() {
        Map<NodeCharacterisation, Set<Integer>> mapping = new HashMap<NodeCharacterisation, Set<Integer>>();
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
            addToCharacterisationMappings(mapping, node, qecGraph.isBlack(node), blackNeighbourCount, whiteNeighbourCount);
        }
        return mapping;
    }

    private void addToCharacterisationMappings(Map<NodeCharacterisation, Set<Integer>> mapping, Integer node, boolean isBlack, int blackNeighbours, int whiteNeighbours) {
        NodeCharacterisation nodeCharacterisation = new NodeCharacterisation(isBlack, whiteNeighbours, blackNeighbours);
        Set<Integer> nodes = mapping.get(nodeCharacterisation);
        if (nodes == null) {
            nodes = new HashSet<Integer>();
            mapping.put(nodeCharacterisation, nodes);
        }
        nodes.add(node);
    }

    private boolean characterisationSetSizesMatch(IsomorphismCalculator rhs) {
        for (Entry<NodeCharacterisation, Set<Integer>> entry : getCharacterisationToNodesMapping().entrySet()) {
            Set<Integer> rhsSet = rhs.getCharacterisationToNodesMapping().get(entry.getKey());
            if (rhsSet == null || entry.getValue().size() != rhsSet.size()) {
                return false;
            }
        }
        return true;
    }

    private boolean nodesCanBeMapped(IsomorphismCalculator rhs) {
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
        assert mappingInvariantsSatisfied() : "Bug detected.  Should never happen";
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
                assert mappingInvariantsSatisfied() : "Bug detected.  Should never happen";
                return true;
            }
            mapping.remove(node);
            mappedRhsNodes.remove(rhsNode);
        }
        mappedLhsNodes.remove(node);
        assert mappingInvariantsSatisfied() : "Bug detected.  Should never happen";
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
        NodeCharacterisation nodeCharacterisation = getNodeToCharacterisationMapping().get(node);
        return new HashSet<Integer>(rhs.getCharacterisationToNodesMapping().get(nodeCharacterisation));
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

    private Map<Integer, NodeCharacterisation> createNodeToCharacterisationMapping() {
        Map<Integer, NodeCharacterisation> mapping = new HashMap<Integer, NodeCharacterisation>();
        for (Entry<NodeCharacterisation, Set<Integer>> entry : getCharacterisationToNodesMapping().entrySet()) {
            NodeCharacterisation characterisation = entry.getKey();
            for (Integer node : entry.getValue()) {
                mapping.put(node, characterisation);
            }
        }
        return mapping;
    }

    private boolean mappingInvariantsSatisfied() {
        int mappedCount = 0;
        for (Entry<NodeCharacterisation, Set<Integer>> entry : getCharacterisationToNodesMapping().entrySet()) {
            mappedCount += entry.getValue().size();
        }
        return mappedCount == size && getNodeToCharacterisationMapping().size() == size;
    }

    private int calculateIsomorphismHashCode() {
        assert getNumberOfBlackNodes() >= 0 && getCharacterisationToNodesMapping() != null : "Buggy code.  Not initialized properly";
        assert mappingInvariantsSatisfied() : "Bug detected.  Should never happen";
        return 31 * getNumberOfBlackNodes() + getCharacterisationToNodesMapping().keySet().hashCode();
    }

    public int isomorphismHashCode() {
        if (isomorphicHashCode == 0) isomorphicHashCode = calculateIsomorphismHashCode();
        return isomorphicHashCode;
    }

    private Map<NodeCharacterisation, Set<Integer>> getCharacterisationToNodesMapping() {
        if (characterisationToNodesMapping == null) characterisationToNodesMapping = createCharacterisationMappings();
        return characterisationToNodesMapping;
    }

    private Map<Integer, NodeCharacterisation> getNodeToCharacterisationMapping() {
        if (nodeToCharacterisationMapping == null) nodeToCharacterisationMapping = createNodeToCharacterisationMapping();
        return nodeToCharacterisationMapping;
    }

    private int getNumberOfBlackNodes() {
        if (numberOfBlackNodes < 0) numberOfBlackNodes = qecGraph.getNumberOfBlackNodes();
        return numberOfBlackNodes;
    }
}
