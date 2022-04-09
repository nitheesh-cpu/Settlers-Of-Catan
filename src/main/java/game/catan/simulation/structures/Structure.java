package game.catan.simulation.structures;

import game.catan.simulation.engine.Location;
import game.catan.simulation.engine.Player;
import javafx.scene.image.ImageView;

public class Structure {

    private Location location;
    private Player player;
    private ImageView image;
    private StructureType type;

    public Structure(Location location, ImageView image) {
        this.type = StructureType.SETTLEMENT;
        this.location = location;
        this.image = image;
    }

    public enum StructureType{
        SETTLEMENT,
        CITY;
    }
}
