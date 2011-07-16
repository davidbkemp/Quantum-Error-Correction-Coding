package quantumlunch.isomorphism;

public class NodeCharacterisation {

    private final boolean black;
    private final int numberOfWhiteNeighbours;
    private final int numberOfBlackNeighbours;
    private final int hashCode;
    
    public NodeCharacterisation(boolean black, int numberOfWhiteNeighbours, int numberOfBlackNeighbours) {
        this.black = black;
        this.numberOfWhiteNeighbours = numberOfWhiteNeighbours;
        this.numberOfBlackNeighbours = numberOfBlackNeighbours;
        this.hashCode = computeHashCode();
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

     private int computeHashCode() {
        int result = (black ? 1 : 0);
        result = 31 * result + numberOfWhiteNeighbours;
        result = 31 * result + numberOfBlackNeighbours;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeCharacterisation that = (NodeCharacterisation) o;

        return black == that.black && numberOfBlackNeighbours == that.numberOfBlackNeighbours && numberOfWhiteNeighbours == that.numberOfWhiteNeighbours;
    }

    @Override
    public String toString() {
        return "NodeCharacterisation{" +
                "black=" + black +
                ", numberOfWhiteNeighbours=" + numberOfWhiteNeighbours +
                ", numberOfBlackNeighbours=" + numberOfBlackNeighbours +
                ", hashCode=" + hashCode +
                '}';
    }
}
