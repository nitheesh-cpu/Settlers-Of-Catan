package game.catan.simulation.buildings;

import game.catan.simulation.Player;
import game.catan.simulation.enums.StructureType;
import game.catan.simulation.helper.Location;
import game.catan.simulation.helper.Vertex;

public class Structure {

    private Location location;
    private Player owner;
    private StructureType type;
    private Vertex vertex;

    public Structure(Vertex vertex, Location location, Player owner) {
        this.vertex = vertex;
        this.location = location;
        this.owner = owner;
        this.type = StructureType.SETTLEMENT;
    }

    public Structure(Location location, Player owner) {
        this(null, location, owner);
    }

    public void upgradeToCity() {
        this.type = StructureType.CITY;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Location getLocation() {
        return location;
    }

    public Player getOwner() {
        return owner;
    }

    public StructureType getType() {
        return type;
    }
}
