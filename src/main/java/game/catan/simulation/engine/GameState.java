package game.catan.simulation.engine;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import game.catan.simulation.structures.Tile;

import java.util.Random;
import java.util.Stack;

public class GameState {
    public static Stack<Integer> TileNumbers;
    public Tile[][] tiles;

    public GameState() {
        TileNumbers = new Stack<>();
        int[] tileNumbers = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};
        for (int i : tileNumbers)
            TileNumbers.push(i);
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
        int w = 1;
//        tiles[y][x].initialize();

        while (w == 1) {
            if (corner == 0 || depth > 0) {
                for (int q = 0; q < 2 / w; q++) {
                    tiles[y++][x].initialize();
                    depth++;
                }
                if (lastCorner == 0) break;
            }
            if (corner == 1 || depth > 0) {
                for (int q = 0; q < 2 ; q++) {
//                    System.out.println("x");
                    tiles[y++][x].initialize();
                    depth++;
                }
                if (lastCorner == 1) break;
            }
            if (corner == 2 || depth > 0) {
                for (int q = 0; q < 2 / w; q++) {
                    tiles[y][x++].initialize();
                    depth++;
                }
                if (lastCorner == 2) break;
            }
            if (corner == 3 || depth > 0) {
                for (int q = 0; q < 2 / w; q++) {
                    tiles[y--][x++].initialize();
                    depth++;
                }
                if (lastCorner == 3) break;
            }
            if (corner == 4 || depth > 0) {
                for (int q = 0; q < 2 / w; q++) {
                    tiles[y--][x--].initialize();
                    depth++;
                }
                if (lastCorner == 4) break;
            }
            if (corner == 5 || depth > 0) {
                for (int q = 0; q < 2 / w; q++) {
                    tiles[y][x--].initialize();
                    depth++;
                }
                if (lastCorner == 5) break;
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
    }
}
