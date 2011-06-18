package quantumlunch.isomorphism;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class NodeCharacterisation {

    private final boolean black;
    private final int numberOfWhiteNeighbours;
    private final int numberOfBlackNeighbours;
    private final int hashCode;
    
    public NodeCharacterisation(boolean black, int numberOfWhiteNeighbours, int numberOfBlackNeighbours) {
        this.black = black;
        this.numberOfWhiteNeighbours = numberOfWhiteNeighbours;
        this.numberOfBlackNeighbours = numberOfBlackNeighbours;
        this.hashCode = new HashCodeBuilder()
            .append(black).append(numberOfWhiteNeighbours).append(numberOfBlackNeighbours).toHashCode();
    }
    
    public boolean isBlack() {
        return black;
    }
    public int getNumberOfWhiteNeighbours() {
        return numberOfWhiteNeighbours;
    }
    public int getNumberOfBlackNeighbours() {
        return numberOfBlackNeighbours;
    }
    
    @Override
    public int hashCode() {
        return hashCode; 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NodeCharacterisation)) return false;
        NodeCharacterisation rhs = (NodeCharacterisation) obj;
        return new EqualsBuilder()
            .append(black, rhs.black)
            .append(numberOfBlackNeighbours, rhs.numberOfBlackNeighbours)
            .append(numberOfWhiteNeighbours, rhs.numberOfWhiteNeighbours)
            .isEquals();
    }
}
