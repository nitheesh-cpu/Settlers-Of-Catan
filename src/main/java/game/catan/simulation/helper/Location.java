package game.catan.simulation.helper;

public class Location {

    private final int row;
    private final int col;
    private final int orientation;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
        this.orientation = -1;
    }

    public Location(int row, int col, int orientation) {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
    }

    public int getRow() {
        return row;
    }

    public int getX() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getY() {
        return col;
    }

    public int getOrientation() {
        return orientation;
    }

    public boolean equals(Location other) {
        return row == other.row && col == other.col && orientation == other.orientation;
    }

    public String toString() {
//        return "(" + row + ", " + col + ", " + orientation + ")";
        return "(" + row + ", " + col + ")";
    }
}
