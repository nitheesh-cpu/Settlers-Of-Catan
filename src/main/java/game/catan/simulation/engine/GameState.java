package game.catan.simulation.engine;

import game.catan.simulation.structures.ResourceType;
import game.catan.simulation.structures.Road;
import game.catan.simulation.structures.Structure;
import game.catan.simulation.structures.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class GameState {
    public static Stack<Integer> TileNumbers;
    public Tile[][] tiles;
    public HashMap<String, Tile> tileMap;
    private Board board;


    public GameState() {
        TileNumbers = new Stack<>();
        int[] tileNumbers = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};
        for (int i : tileNumbers)
            TileNumbers.push(i);
        tileMap = new HashMap<>();
    }

    public void start() {
        // TODO: prompt for number of players
        // TODO: get game seed
        // TODO: roll dice to get player order
        // TODO: prompt user for location of roads and settlements

        this.board = new Board(tiles);

        // Set robber location
        board.initializeRobber();
        board.createCards();

        // TODO: get resources from surrounding tiles of second settlement
    }

    public void startResourceProduction() {
        // TODO: dice roll
        // will fix
        int roll = new Random().nextInt(7) + 1;

        if (roll == 7) {
            board.discardHalf();
            // TODO: prompt user for tile to move robber to
            // board.moveRobber();
        } else {

        }


    }

    public void rollDice() {
        // TODO: make dice roll functional
        System.out.println("Rolling dice...");
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
                }
                if (lastCorner == 0) break;
            }
            if (corner == 1 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y++][x].initialize();
                    depth++;
                }
                if (lastCorner == 1) break;
            }
            if (corner == 2 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y][x++].initialize();
                    depth++;
                }
                if (lastCorner == 2) break;
            }
            if (corner == 3 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y--][x++].initialize();
                    depth++;
                }
                if (lastCorner == 3) break;
            }
            if (corner == 4 || depth > 0) {
                for (int q = 0; q < 2; q++) {
                    tiles[y--][x--].initialize();
                    depth++;
                }
                if (lastCorner == 4) break;
            }
            if (corner == 5 || depth > 0) {
                for (int q = 0; q < 2; q++) {
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

    public Board getBoard() {
        return board;
    }

    public void setTiles(Tile[][] x) {
        tiles = x;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                try {
                    String key = i + "," + j;
                    tileMap.put(key, tiles[i][j]);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }

        this.board = new Board(tiles);
    }

    public void createRoads(Tile[][] tiles, Pane roadPane) {
//        Road Locations Relative to Tile
//        1-topleft -> clockwise -> 6-bottomleft
//        Road 1 = -78,-30
//        Road 2 = -30,-56
//        Road 3 = +17,-30
//        Road 4 = +17,+22
//        Road 5 = -30,+50
//        Road 6 = -78,+23
//        Width = 61
//        Height = 7
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                for (int i = 0; c == tiles[r].length - 1 ? (r > 1 ? i < 5 : i < 4) : i < 3; i++) {
                    createSingularRoad(r, c, i, roadPane);
                }
            }
        }
        createSingularRoad(2, 0, 5, roadPane);
        createSingularRoad(3, 0, 5, roadPane);
        createSingularRoad(4, 0, 4, roadPane);
        createSingularRoad(4, 0, 5, roadPane);
        createSingularRoad(4, 1, 4, roadPane);
        createSingularRoad(4, 1, 5, roadPane);
        createSingularRoad(4, 2, 5, roadPane);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                Road[] w = tiles[r][c].getRoads();
                if (w[3] == null) {
                    w[3] = tiles[r][c + 1].getRoads()[0];
                }
                if (w[4] == null) {
                    if (r < 2) w[4] = tiles[r + 1][c + 1].getRoads()[1];
                    else w[4] = tiles[r + 1][c].getRoads()[1];
                }
                if (w[5] == null) {
                    if (r < 2) w[5] = tiles[r + 1][c].getRoads()[2];
                    else w[5] = tiles[r + 1][c - 1].getRoads()[2];
                }
                tiles[r][c].setRoads(w);
            }
        }
    }

    public void createSingularRoad(int r, int c, int i, Pane roadPane) {
        int[][] roadOffset = new int[][]{{-78, 23, 59}, {-78, -30, -59}, {-30, -56, 0}, {17, -30, 59}, {17, 22, -59}, {-30, 50, 0}};
        Road[] w = tiles[r][c].getRoads();
        int x = (int) tiles[r][c].getPolygon().getLayoutX();
        int y = (int) tiles[r][c].getPolygon().getLayoutY();
        Location loc = new Location(x + roadOffset[i][0], y + roadOffset[i][1]);
        Rectangle rect = new Rectangle();
        rect.setX(loc.getX());
        rect.setY(loc.getY());
        rect.setRotate(roadOffset[i][2]);
        rect.setWidth(61);
        rect.setHeight(7);
        rect.setFill(Color.RED);
        rect.setOnMouseEntered(event -> rect.setFill(Color.GREEN));
        rect.setOnMouseExited(event ->
                rect.setFill(Color.RED)
        );
        roadPane.getChildren().add(rect);
        rect.setVisible(false);
        w[i] = new Road(loc, rect);
    }

    public void createSettlements(Tile[][] tiles, Pane settlementPane) {
//        Settlement 1 = -75,-18
//        Settlement 2 = -48,-70
//        Settlement 3 = +17,-70
//        Settlement 4 = +44,-18
//        Settlement 5 = +17,+36
//        Settlement 6 = -48,+36
//
//        Width = 32
//        Height = 31
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                for (int i = 0; (c == tiles[r].length - 1 ? (r == 2 || r == 3 ? i < 4 : i < 3) : i < 2); i++) {
                    createSingularSettlement(r, c, i, settlementPane);
                }
            }
        }
        createSingularSettlement(2, 0, 5, settlementPane);
        createSingularSettlement(3, 0, 5, settlementPane);
        createSingularSettlement(4, 0, 5, settlementPane);
        createSingularSettlement(4, 0, 3, settlementPane);
        createSingularSettlement(4, 0, 4, settlementPane);
        createSingularSettlement(4, 1, 3, settlementPane);
        createSingularSettlement(4, 1, 4, settlementPane);
        createSingularSettlement(4, 2, 3, settlementPane);
        createSingularSettlement(4, 2, 4, settlementPane);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                Structure[] w = tiles[r][c].getStructures();
                if (w[2] == null) {
                    w[2] = tiles[r][c + 1].getStructures()[0];
                }
                if (w[3] == null) {
                    if (r < 2) w[3] = tiles[r + 1][c + 1].getStructures()[1];
                    else w[3] = tiles[r + 1][c].getStructures()[1];
                }
                try {
                    if (w[4] == null) {
                        if (r < 2) w[4] = tiles[r + 1][c + 1].getStructures()[0];
                        else w[4] = tiles[r + 1][c].getStructures()[0];
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                if (w[5] == null) {
                    if (r < 2) w[5] = tiles[r + 1][c].getStructures()[1];
                    else if (r == 4) w[5] = tiles[r][c - 1].getStructures()[3];
                    else w[5] = tiles[r + 1][c - 1].getStructures()[1];
                }
                tiles[r][c].setStructures(w);
            }
        }
        Structure[] w = tiles[2][4].getStructures();
        w[4] =  tiles[3][3].getStructures()[2];
        tiles[2][4].setStructures(w);
        w = tiles[3][3].getStructures();
        w[4] =  tiles[4][2].getStructures()[2];
        tiles[3][3].setStructures(w);
    }

    public void createSingularSettlement(int r, int c, int i, Pane settlementPane) {
        int[][] roadOffset = new int[][]{{-75, -18}, {-48, -70}, {17, -70}, {44, -18}, {17, 36}, {-48, 36}};
        Structure[] w = tiles[r][c].getStructures();
        int x = (int) tiles[r][c].getPolygon().getLayoutX();
        int y = (int) tiles[r][c].getPolygon().getLayoutY();

        Location loc = new Location(x + roadOffset[i][0], y + roadOffset[i][1]);
        ImageView settl = new ImageView(new Image("game/catan/PlayerResources/player1Settlement.png"));
        settl.setX(loc.getX());
        settl.setY(loc.getY());
        settl.setFitWidth(32);
        settl.setFitHeight(31);
        settlementPane.getChildren().add(settl);
        settl.setVisible(false);
        //make Structure class
        w[i] = new Structure(loc, settl);
    }


}
