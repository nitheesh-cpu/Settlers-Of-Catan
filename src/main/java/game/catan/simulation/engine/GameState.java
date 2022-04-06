package game.catan.simulation.engine;

import game.catan.simulation.structures.Road;
import game.catan.simulation.structures.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class GameState {
    public static Stack<Integer> TileNumbers;
    public Tile[][] tiles;
    public HashMap<String, Tile> tileMap;

    public GameState() {
        TileNumbers = new Stack<>();
        int[] tileNumbers = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};
        for (int i : tileNumbers)
            TileNumbers.push(i);
        tileMap = new HashMap<>();
    }

    public void initializeTileNumbers() {
        Random rand = new Random();
        int corner = rand.nextInt(6);
        int lastCorner = (corner + 5) % 6;
        int depth = 0;
        int xCoords[] = new int[]{0, 0, 0, 2, 4, 2};
        int yCoords[] = new int[]{0, 2, 4, 4, 2, 0};
        int x = xCoords[corner];
        int y = yCoords[corner];
        int xCoords2[] = new int[]{1, 1, 1, 2, 3, 2};
        int yCoords2[] = new int[]{1, 2, 3, 3, 2, 1};
        int x2 = xCoords2[corner];
        int y2 = yCoords2[corner];

        while (true) {
            if (corner == 0 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y++][x].initialize();
                    depth++;
                }if (lastCorner == 0) break;
            }
            if (corner == 1 || depth > 0) {
                for (int q = 0; q < 2 ; q++) {
                    tiles[y++][x].initialize();
                    depth++;
                }if (lastCorner == 1) break;
            }
            if (corner == 2 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y][x++].initialize();
                    depth++;
                }if (lastCorner == 2) break;
            }
            if (corner == 3 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y--][x++].initialize();
                    depth++;
                }if (lastCorner == 3) break;
            }
            if (corner == 4 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y--][x--].initialize();
                    depth++;
                }if (lastCorner == 4) break;
            }
            if (corner == 5 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y][x--].initialize();
                    depth++;
                }if (lastCorner == 5) break;
            }
        }
        depth = 0;
        while (true) {
            if (corner == 0 || depth > 0) {
                tiles[y2++][x2].initialize();
                depth++;
                if (lastCorner == 0) break;
            }
            if (corner == 1 || depth > 0) {
                tiles[y2++][x2].initialize();
                depth++;
                if (lastCorner == 1) break;
            }
            if (corner == 2 || depth > 0) {
                tiles[y2][x2++].initialize();
                depth++;
                if (lastCorner == 2) break;
            }
            if (corner == 3 || depth > 0) {
                tiles[y2--][x2++].initialize();
                depth++;
                if (lastCorner == 3) break;
            }
            if (corner == 4 || depth > 0) {
                tiles[y2--][x2--].initialize();
                depth++;
                if (lastCorner == 4) break;
            }
            if (corner == 5 || depth > 0) {
                tiles[y2][x2--].initialize();
                depth++;
                if (lastCorner == 5) break;
            }
        }
        tiles[2][2].initialize();
    }

    public void setTiles(Tile[][] x) {
        tiles = x;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                try{
                    String key = i + "," + j;
                    tileMap.put(key, tiles[i][j]);
                }catch (ArrayIndexOutOfBoundsException ignored){
                }
            }
        }
    }

    public void createRoads(Tile[][] tiles, Pane roadPane) {
        int[][] roadOffset = new int[][]{{-78, 23}, {-78, -30}, {-30, -56}, {17, -30}, {17, 22}, {-30, 50}};
        //Road Locations Relative to Tile
        //1-topleft -> clockwise -> 6-bottomleft
//        Road 1 = -78,-30
//        Road 2 = -30,-56
//        Road 3 = +17,-30
//        Road 4 = +17,+22
//        Road 5 = -30,+50
//        Road 6 = -78,+23
//
//        Width = 61
//        Height = 7
        for(int r = 0; r < tiles.length; r++) {
            for(int c = 0; c < tiles[0].length; c++) {
                //create 3 Location objects for each tile
                Road[] w = tiles[r][c].getRoads();
                for(int i = 0; i < 3; i++) {

                    int x = (int) tiles[r][c].getPolygon().getLayoutX();
                    int y = (int) tiles[r][c].getPolygon().getLayoutY();
                    Location loc = new Location(x+roadOffset[i][0], y+roadOffset[i][1]);
                    Rectangle rect = new Rectangle();
                    rect.setX(loc.getX());
                    rect.setY(loc.getY());
                    rect.setWidth(61);
                    rect.setHeight(7);
                    roadPane.getChildren().add(rect);
                    w[i] = new Road(loc,rect);
                }
            }
        }
    }
}
