package game.catan.simulation.buildings;

import game.catan.simulation.helper.Edge;
import game.catan.simulation.helper.Location;
import game.catan.simulation.Player;
import javafx.scene.shape.Rectangle;

public class Road {

    private final Player owner;
    private Edge edge;

    private Rectangle rectangle;
    private Location screenLocation;


    public Road(Edge edge, Player owner) {
        this.owner = owner;
        this.edge = edge;
        this.rectangle = null;
        this.screenLocation = null;
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

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Edge getEdge() {
        return edge;
    }

    public Player getOwner() {
        return owner;
    }

    public String toString() {
        return edge.toString();
    }

}
