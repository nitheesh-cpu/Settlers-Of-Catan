package game.catan.simulation.buildings;

import game.catan.simulation.Player;
import game.catan.simulation.enums.StructureType;
import game.catan.simulation.helper.Vertex;

public class Structure {

    private Player owner;
    private StructureType type;
    private Vertex vertex;

    public Structure(Vertex vertex, Player owner) {
        this.vertex = vertex;
        this.owner = owner;
        this.type = StructureType.SETTLEMENT;
    }

    public void upgradeToCity() {
        this.type = StructureType.CITY;
        owner.upgradeToCity();
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Player getOwner() {
        return owner;
    }

    public StructureType getType() {
        return type;
    }
}
