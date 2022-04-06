package game.catan.simulation.engine;

public class Location {
    private int xCoord;
    private int yCoord;

    public int getX() {
        return xCoord;
    }

    public void setX(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public void setY(int yCoord) {
        this.yCoord = yCoord;
    }

    public Location(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
}
