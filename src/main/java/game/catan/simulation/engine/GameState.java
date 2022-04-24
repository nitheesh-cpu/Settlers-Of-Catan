package game.catan.simulation.engine;

import game.catan.graphics.GameController;
import game.catan.graphics.MenuController;
import game.catan.simulation.structures.*;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class GameState {
    public static int numPlayers;
    public static Player[] players;
    public static Player currentPlayer;
    public static int currentPlayerIndex;
    public static int currentTurn;
    public static Stack<Integer> TileNumbers;
    public Tile[][] tiles;
    public HashMap<String, Tile> tileMap;
    private Board board;
    private Random rand;
    private HashMap<Polygon, Tile> tilePolygonMap;
    private HashMap<Rectangle, Road> roadMap;
    private HashMap<ImageView, Structure> structureMap;
    public static GameController gameController;


    public GameState(GameController gameController) {
        numPlayers = MenuController.players;
        GameState.gameController = gameController;
        players = new Player[numPlayers];
        TileNumbers = new Stack<>();
        int[] tileNumbers = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};
        for (int i : tileNumbers)
            TileNumbers.push(i);
        tileMap = new HashMap<>();
        roadMap = new HashMap<>();
        structureMap = new HashMap<>();
        tilePolygonMap = new HashMap<>();
        engine.Dice.init(1);
    }

    public void start() {
        rand = new Random(123);
        initializePlayers(numPlayers);
        this.board = new Board(tiles);
        setUpPhase();

        // TODO: prompt user for location of roads and settlements
//        buildRoad(players[0]);
//        buildSettlement(players[0]);

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
            board.produceResources(roll);
        }


    }


    private int setupCounter = 0;
    public void setUpPhase() {
        if(setupCounter == 24) return;
        setupCounter++;
        if (setupCounter<=12) {
            if(setupCounter%3 == 1)
                buildSettlement();
            else if(setupCounter%3 == 2) {
                gameController.actionLogText.appendText("Player " + (currentPlayerIndex+1) + " built a settlement\n");
                buildRoad();
            }
            else if(setupCounter%3 == 0) {
                gameController.actionLogText.appendText("Player " + (currentPlayerIndex+1) + " built a road\n");
                nextTurn();
                setUpPhase();
            }
        }
        if(setupCounter == 12) {
            currentPlayerIndex--;
            if (currentPlayerIndex < 0)
                currentPlayerIndex = numPlayers - 1;
        }
        else {
            if(setupCounter%3 == 1) {
                gameController.updatePlayerStats();
                buildSettlement();
            }
            else if(setupCounter%3 == 2) {
                gameController.actionLogText.appendText("Player " + (currentPlayerIndex+1) + " built a settlement\n");
                buildRoad();
            }
            else if(setupCounter%3 == 0) {
                gameController.actionLogText.appendText("Player " + (currentPlayerIndex+1) + " built a road\n");
                currentPlayerIndex--;
                if(currentPlayerIndex<0)
                    currentPlayerIndex = numPlayers-1;
                currentPlayer = players[currentPlayerIndex];
                setUpPhase();
            }
        }
    }

    public static void nextTurn() {
        currentPlayerIndex++;

        if (currentPlayerIndex >= players.length)
            currentPlayerIndex = 0;

        currentPlayer = players[currentPlayerIndex];
        System.out.println("Next turn: " + currentPlayer);
        gameController.updatePlayerStats();
    }

    public static int nextTurnIndex(int index) {
        index++;

        if (index >= numPlayers)
            index -= numPlayers;

        return index;
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
        for(Tile[] tileRow : tiles) {
            for (Tile w : tileRow) {
                tilePolygonMap.put(w.getPolygon(), w);
            }
        }
        this.board = new Board(tiles);
    }

    private void initializePlayers(int numPlayers) {
        TreeMap<Integer, Player> playerMap = new TreeMap<>();

        for (int i = 0; i < numPlayers; i++) {
            int roll = engine.Dice.roll();

            while (playerMap.containsKey(roll)) {
                roll = engine.Dice.roll();
            }

            playerMap.put(roll, new Player(i));
        }

        Set<Integer> keys = playerMap.keySet();

        int index = numPlayers - 1;

        for (Integer key: keys) {
            players[index] = playerMap.get(key);
            index--;
        }

        currentPlayer = players[0];
        currentPlayerIndex = 0;
    }

    public void buildRoad(){
        for (Road r : roadMap.values()) {
            if (r.getOwner() == null) {
                r.getRectangle().setOnMouseEntered(event -> r.getRectangle().setFill(Color.YELLOW));
                r.getRectangle().setOnMouseExited(event -> r.getRectangle().setFill(Color.TRANSPARENT));
                r.getRectangle().setOnMouseClicked(event -> {
                    r.setOwner(currentPlayer);
                    currentPlayer.addRoad(r);
                    gameController.updatePlayerStats();
                    r.getRectangle().setFill(Color.web(currentPlayer.getColorHex()));
                    disableBuildRoad();
                    setUpPhase();
                });
            }
        }
    }

    public void buildSettlement() {
        for (Structure r : structureMap.values()) {
            if (r.getOwner() == null) {
                r.getImage().setOnMouseEntered(event -> r.getImage().setOpacity(1));
                r.getImage().setOnMouseExited(event -> r.getImage().setOpacity(0));
                r.getImage().setOnMouseClicked(event -> {
                    r.setOwner(currentPlayer);
                    currentPlayer.addSettlement(r);
                    gameController.updatePlayerStats();
                    r.getImage().setImage(currentPlayer.getImages().get("Settlement"));
                    disableBuildSettlement();
                    setUpPhase();
                });
            }
        }
    }

    public void disableBuildRoad(){
        for (Road r : roadMap.values()) {
            r.getRectangle().setOnMouseEntered(Event::consume);
            r.getRectangle().setOnMouseExited(Event::consume);
            r.getRectangle().setOnMouseClicked(Event::consume);
        }
    }

    public void disableBuildSettlement(){
        for (Structure r : structureMap.values()) {
            r.getImage().setOnMouseEntered(Event::consume);
            r.getImage().setOnMouseExited(Event::consume);
            r.getImage().setOnMouseClicked(Event::consume);
        }
    }

    public static int[] rollDice(){
        // TODO: make dice roll functional
        engine.Dice.roll();
        int roll = engine.Dice.getFaceOne();
        int roll2 = engine.Dice.getFaceTwo();
        gameController.actionLogText.appendText("Dice rolled\n");
        gameController.updateDiceGraphic(roll, roll2);
        return new int[]{roll, roll2};
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
                Edge[] e = tiles[r][c].getEdges();
                if (w[3] == null) {
                    w[3] = tiles[r][c + 1].getRoads()[0];
                    e[3] = tiles[r][c + 1].getEdges()[0];
                }
                if (w[4] == null) {
                    if (r < 2){ w[4] = tiles[r + 1][c + 1].getRoads()[1];
                        e[4] = tiles[r + 1][c + 1].getEdges()[1];}
                    else{ w[4] = tiles[r + 1][c].getRoads()[1];
                        e[4] = tiles[r + 1][c].getEdges()[1];}
                }
                if (w[5] == null) {
                    if (r < 2){ w[5] = tiles[r + 1][c].getRoads()[2];
                        e[5] = tiles[r + 1][c].getEdges()[2];}
                    else{ w[5] = tiles[r + 1][c - 1].getRoads()[2];
                        e[5] = tiles[r + 1][c - 1].getEdges()[2];}
                }
                tiles[r][c].setRoads(w);
                tiles[r][c].setEdges(e);
            }
        }
    }

    public void createSingularRoad(int r, int c, int i, Pane roadPane) {
        int[][] roadOffset = new int[][]{{-78, 23, 59}, {-78, -30, -59}, {-30, -56, 0}, {17, -30, 59}, {17, 22, -59}, {-30, 50, 0}};
        Road[] w = tiles[r][c].getRoads();
        Edge[] q = tiles[r][c].getEdges();
        int x = (int) tiles[r][c].getPolygon().getLayoutX();
        int y = (int) tiles[r][c].getPolygon().getLayoutY();
        Location loc = new Location(x + roadOffset[i][0], y + roadOffset[i][1]);
        Rectangle rect = new Rectangle();
        rect.setX(loc.getX());
        rect.setY(loc.getY());
        rect.setRotate(roadOffset[i][2]);
        rect.setWidth(61);
        rect.setHeight(7);
        rect.setFill(Color.TRANSPARENT);
//        rect.setOnMouseClicked(event -> gameController.actionLogText.appendText("Road clicked\n"));
//        rect.setOnMouseExited(event ->
//                rect.setFill(Color.RED)
//        );
        roadPane.getChildren().add(rect);
        Road road = new Road(loc, rect);
        Edge edge = new Edge(road,loc);

        roadMap.put(rect, road);
        w[i] = road;
        q[i] = edge;
        tiles[r][c].setRoads(w);
        tiles[r][c].setEdges(q);
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
                Vertex[] q = tiles[r][c].getVertices();
                if (w[2] == null) {
                    w[2] = tiles[r][c + 1].getStructures()[0];
                    q[2] = tiles[r][c + 1].getVertices()[0];
                }
                if (w[3] == null) {
                    if (r < 2){w[3] = tiles[r + 1][c + 1].getStructures()[1];
                        q[3] = tiles[r + 1][c + 1].getVertices()[1];}
                    else{ w[3] = tiles[r + 1][c].getStructures()[1];
                        q[3] = tiles[r + 1][c].getVertices()[1];}
                }
                try {
                    if (w[4] == null) {
                        if (r < 2){ w[4] = tiles[r + 1][c + 1].getStructures()[0];
                            q[4] = tiles[r + 1][c + 1].getVertices()[0];}
                        else{ w[4] = tiles[r + 1][c].getStructures()[0];
                            q[4] = tiles[r + 1][c].getVertices()[0];}
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                if (w[5] == null) {
                    if (r < 2){ w[5] = tiles[r + 1][c].getStructures()[1];
                        q[5] = tiles[r + 1][c].getVertices()[1];}
                    else if (r == 4){ w[5] = tiles[r][c - 1].getStructures()[3];
                        q[5] = tiles[r][c - 1].getVertices()[3];}
                    else{ w[5] = tiles[r + 1][c - 1].getStructures()[1];
                        q[5] = tiles[r + 1][c - 1].getVertices()[1];}
                }
                tiles[r][c].setStructures(w);
                tiles[r][c].setVertices(q);
            }
        }
        Structure[] w = tiles[2][4].getStructures();
        Vertex[] q = tiles[2][4].getVertices();
        w[4] =  tiles[3][3].getStructures()[2];
        q[4] =  tiles[3][3].getVertices()[2];
        tiles[2][4].setStructures(w);
        tiles[2][4].setVertices(q);
        w = tiles[3][3].getStructures();
        q = tiles[3][3].getVertices();
        w[4] =  tiles[4][2].getStructures()[2];
        q[4] =  tiles[4][2].getVertices()[2];
        tiles[3][3].setStructures(w);
        tiles[3][3].setVertices(q);
    }

    public void createSingularSettlement(int r, int c, int i, Pane settlementPane) {
        int[][] roadOffset = new int[][]{{-75, -18}, {-48, -70}, {17, -70}, {44, -18}, {17, 36}, {-48, 36}};
        Structure[] w = tiles[r][c].getStructures();
        Vertex[] q = tiles[r][c].getVertices();
        int x = (int) tiles[r][c].getPolygon().getLayoutX();
        int y = (int) tiles[r][c].getPolygon().getLayoutY();

        Location loc = new Location(x + roadOffset[i][0], y + roadOffset[i][1]);
        ImageView settl = new ImageView(new Image("game/catan/PlayerResources/hover.jpg"));
        settl.setX(loc.getX());
        settl.setY(loc.getY());
        settl.setFitWidth(32);
        settl.setFitHeight(31);
        settlementPane.getChildren().add(settl);
        settl.setOpacity(0);
        Structure settlement = new Structure(loc, settl);
        Vertex v = new Vertex(settlement, loc);
//        settl.setOnMouseClicked(event -> gameController.actionLogText.appendText("Settlement clicked\n"));
        //make Structure class
        structureMap.put(settl, settlement);
        w[i] = settlement;
        q[i] = v;
        tiles[r][c].setStructures(w);
        tiles[r][c].setVertices(q);
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

    public Player getCurrentPlayer() {
       return currentPlayer;
    }
}
