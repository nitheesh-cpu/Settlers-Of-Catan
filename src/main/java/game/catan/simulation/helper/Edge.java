package game.catan.simulation.helper;

import game.catan.simulation.buildings.Road;
import javafx.scene.shape.Rectangle;

public class Edge {

    public static final int NORTHWEST = 0, NORTH = 1, NORTHEAST = 2, SOUTHEAST = 3, SOUTH = 4, SOUTHWEST = 5;
    private static int count = 0;

    private int id;
    private Road road;
    private Vertex[] adjacentVertices;

    private Location screenLocation;
    private Rectangle rectangle;

    public Edge() {
        this(null);
    }

    public Edge(Road road) {
        this.road = road;
        this.id = count++;
        adjacentVertices = new Vertex[2];

        this.screenLocation = null;
        this.rectangle = null;
    }

    public Location getScreenLocation() {
        return screenLocation;
    }

    public void setScreenLocation(Location screenLocation) {
        this.screenLocation = screenLocation;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setAdjacentVertices(Vertex[] adjacentVertices) {
        this.adjacentVertices = adjacentVertices;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Vertex[] getAdjacentVertices() {
        return adjacentVertices;
    }

    public Road getRoad() {
        return road;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "" + id + (road != null ? " Road owned by " + road.getOwner() : "");
    }
}
