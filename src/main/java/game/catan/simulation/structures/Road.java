package game.catan.simulation.structures;

import game.catan.simulation.engine.Location;
import game.catan.simulation.engine.Player;
import javafx.scene.shape.Rectangle;

public class Road {
    private Location location;
    private Rectangle rectangle;
    private Player owner;

    public Road(Location loc, Rectangle rect) {
        owner = null;
        location = loc;
        rectangle = rect;
    }

    public Location getLocation() {
        return location;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setOwner(Player p){
        owner = p;
   }

    public Player getOwner() {
        return owner;
    }
}
