package game.catan.simulation.helper;

import game.catan.simulation.Tile;
import game.catan.simulation.buildings.Structure;
import javafx.scene.image.ImageView;

import java.util.Arrays;

public class Vertex {

    public static final int NORTHWEST = 0, NORTHEAST = 1, EAST = 2, SOUTHEAST = 3, SOUTHWEST = 4, WEST = 5;
    private static int count = 0;

    private int id;
    private Structure structure;
    private Edge[] adjacentEdges;
    private Tile[] adjacentTiles;

    private Location screenLocation;
    private ImageView image;

    public Vertex(){
        this(null);
    }

    public Vertex(Structure structure){
        this.structure = structure;
        this.id = count++;
        this.adjacentEdges = new Edge[3];
        this.adjacentTiles = new Tile[3];

        this.screenLocation = null;
        this.image = null;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setAdjacentEdges(Edge[] adjacentEdges) {
        this.adjacentEdges = adjacentEdges;
    }

    public void setAdjacentTiles(Tile[] adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }

    public Tile[] getAdjacentTiles() {
        return adjacentTiles;
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
