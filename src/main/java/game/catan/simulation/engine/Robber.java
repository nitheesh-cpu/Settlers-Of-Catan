package game.catan.simulation.engine;

import javafx.scene.image.Image;

import java.util.Objects;

public class Robber {
    private Location location;
    private Image image;

    public Robber(Location location){
        this.location = location;
//        image = new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/")));
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

}
