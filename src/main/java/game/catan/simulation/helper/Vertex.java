package game.catan.simulation.helper;

import game.catan.simulation.buildings.Structure;

public class Vertex {

    public static final int NORTHWEST = 0, NORTHEAST = 1, EAST = 2, SOUTHEAST = 3, SOUTHWEST = 4, WEST = 5;
    private static int count = 0;

    private int id;
    private Structure structure;
    private Edge[] adjacentEdges;

    public Vertex(){
        this(null);
    }

    public Vertex(Structure structure){
        this.structure = structure;
        this.id = count++;
        this.adjacentEdges = new Edge[3];
    }

    public void setAdjacentEdges(Edge[] adjacentEdges) {
        this.adjacentEdges = adjacentEdges;
    }

    public Edge[] getAdjacentEdges() {
        return adjacentEdges;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "" + id + (structure != null ? " Structure owned by " + structure.getOwner() : "");
    }
}
