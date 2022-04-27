package game.catan.simulation.buildings;

import game.catan.simulation.helper.Edge;
import game.catan.simulation.helper.Location;
import game.catan.simulation.Player;

public class Road {

    private final Location location;
    private final Player owner;
    private Edge edge;

    public Road(Location location, Player owner) {
        this.location = location;
        this.owner = owner;
        this.edge = null;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Edge getEdge() {
        return edge;
    }

    public Location getLocation() {
        return location;
    }

    public Player getOwner() {
        return owner;
    }

    public String toString() {
        return edge.toString();
    }

}
