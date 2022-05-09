package game.catan.simulation;

import game.catan.graphics.GameController;
import game.catan.graphics.HelloApplication;
import game.catan.simulation.cards.DevelopmentCard;
import game.catan.simulation.enums.Color;
import game.catan.simulation.enums.DevelopmentCardType;
import game.catan.simulation.enums.Phase;
import game.catan.simulation.enums.ResourceType;
import game.catan.simulation.helper.Edge;
import game.catan.simulation.helper.Location;
import game.catan.simulation.helper.Vertex;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class GameState {

    // TODO: allow player to play development card anytime through their turn

    public static boolean isGameStart = true;

    private static Player currentPlayer = null;
    private static int currentPlayerIndex = 0;
    private static Phase phase = Phase.SETUP;

    public static Player[] players;
    private static Board board;

    private Player longestRoadHolder = null;
    private Player largestArmyHolder = null;

    private HashMap<Rectangle, Edge> roadMap;
    private HashMap<ImageView, Vertex> structureMap;

    public static GameController gameController;
    public static boolean isStealing = false;

    public static DevelopmentCard boughtDevelopmentCard = null;
    public static boolean usedDevelopmentCard = false;

    public GameState(int numPlayers, int seed, GameController controller) {
        Dice.init(seed);

        gameController = controller;
        board = new Board();
        players = new Player[numPlayers];
        roadMap = new HashMap<>();
        structureMap = new HashMap<>();

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(Color.values()[i]);
        }

        currentPlayer = players[currentPlayerIndex];

        Trade.initialize(this);
    }

    // region Switching Turns/Rolling Dice
    public void start() {
        gameController.log("Setup phase has begun. Each player rolls the dice to determine the order of play.\n");
        setUpPhase();
    }

    public static boolean checkWin() {
        if (currentPlayer.getTotalVictoryPoints() >= 10) {
            gameController.disableButtons();
            gameController.log(currentPlayer + " has won the game!");
            gameController.showWinner(currentPlayer);
            gameController.disableButtons();
            return true;
        }

        return false;
    }

    public static int nextTurnIndex(int i) {
        int nextTurnIndex = i + 1;

        if (nextTurnIndex >= players.length)
            nextTurnIndex -= players.length;

        return nextTurnIndex;
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void nextTurnBackwards() {
        currentPlayerIndex--;

        if (currentPlayerIndex < 0)
            currentPlayerIndex = 0;

        currentPlayer = players[currentPlayerIndex];
        System.out.println("Next turn: " + currentPlayer);
        gameController.updatePlayerStats();

        if (phase == Phase.SETUP) {
            setUpPhase();
        }
    }

    public void nextTurn() {
        if (checkWin()) return;

        Player lastPlayer = currentPlayer;

        boughtDevelopmentCard = null;
        usedDevelopmentCard = false;

        currentPlayerIndex++;

        if (currentPlayerIndex >= players.length)
            currentPlayerIndex = 0;

        currentPlayer = players[currentPlayerIndex];
        System.out.println("Next turn: " + currentPlayer);
        gameController.updatePlayerStats();

        if (phase == Phase.SETUP) {
            setUpPhase();
        } else {
            phase = Phase.RESOURCE_PRODUCTION;
            log(lastPlayer + "'s turn has ended.\n");
            log(currentPlayer + "'s turn.");

            gameController.updateButtons();
            gameController.showDice();
            gameController.updatePlayerCards();
        }
    }

    public int[] rollDice() {
        int roll = Dice.roll();
        int diceOne = Dice.getFaceTwo();
        int diceTwo = Dice.getFaceOne();

        gameController.log(currentPlayer.getColor() + " rolled " + roll + ".");
        gameController.updateDiceGraphic(diceOne, diceTwo);

        switch (phase) {
            case SETUP -> {
                if (addPlayerRoll(roll))
                    nextTurn();
            }
            case RESOURCE_PRODUCTION -> {
                resourceProductionPhase(roll);
            }
        }

        return new int[]{diceOne, diceTwo};
    }

    public static Phase getPhase() {
        return phase;
    }

    public static void log(String message) {
        gameController.log(message);
    }

    public static void updatePlayerStats() {
        gameController.updatePlayerStats();
    }

    // endregion

    // region Setup Phase
    private int SETUP_numOfRolls = 0;
    private boolean SETUP_determinePlayerOrder = false;
    private boolean SETUP_placedSettlement = false;
    private boolean SETUP_placedRoad = false;
    private boolean SETUP_playerRepeat = false; // 4th player repeats and places settlement/road
    private boolean SETUP_backwards = false; // Player order goes backwards
    public void setUpPhase() {
        // Still determining player order; allow player to roll dice
        if (SETUP_numOfRolls < players.length) {
            gameController.showDice();
            return;
        }

        // Every player has rolled the dice; determine player order
        if (!SETUP_determinePlayerOrder) {
            SETUP_determinePlayerOrder = true;
            determinePlayerOrder();
            return;
        }

        // If current player hasn't placed their settlement
        if (!SETUP_placedSettlement) {
            log(currentPlayer + ", place your " + (!SETUP_backwards ? "first" : "second" ) + " starting settlement.");
            showSettlements();
        } else if (!SETUP_placedRoad) {
            log(currentPlayer + ", place your " + (!SETUP_backwards ? "first" : "second" ) + " starting road.");
            showRoads();
        } else {
            // Current player placed their road and settlement
            SETUP_placedSettlement = false;
            SETUP_placedRoad = false;

            // Reverse player order
            if (currentPlayerIndex == players.length-1) {
                SETUP_backwards = true;
            }

            if (SETUP_backwards && !SETUP_playerRepeat) {
                SETUP_playerRepeat = true;
                setUpPhase();
            } else if (SETUP_backwards) {
                board.produceResources(currentPlayer.getStructures().get(1));

                // The last player has placed their final settlement/road
                if (currentPlayerIndex == 0) {
                    phase = Phase.RESOURCE_PRODUCTION;
                    isGameStart = false; // no longer start of the game
                    log("End of setup phase.\n");
                    // GameController.actionButtonEnabled = true;
                    gameController.enableButtons();
                    // gameController.showTrade();
                    log(currentPlayer + "'s turn.");
                    gameController.showDice();
                }
                else {
                    nextTurnBackwards();
                }

            } else {
                nextTurn();
            }
        }
    }

    private TreeMap<Integer, Player> playerMap = new TreeMap<>();
    private boolean addPlayerRoll(int roll) {
        if (playerMap.containsKey(roll)) {
            log("Number has already been rolled. Reroll the dice.");
            gameController.showDice();
            return false;
        }

        SETUP_numOfRolls++;
        playerMap.put(roll, currentPlayer);

        if (SETUP_numOfRolls == players.length) {
            setUpPhase();
        }

        return true;
    }

    private void determinePlayerOrder() {
        Set<Integer> keys = playerMap.descendingKeySet();

        // int index = players.length - 1;
        int index = 0;

        for (Integer key: keys) {
            players[index] = playerMap.get(key);
            players[index].setId(index+1);
            index++;
        }

        currentPlayer = players[0];
        gameController.log("Player order: " + Arrays.toString(players) + "\n");
    }
    // endregion

    public void resourceProductionPhase(int roll) {
        if (roll == 7) {
            phase = Phase.TRADE;
            log("Rolled a 7. Activated robber.");
            gameController.updateButtons();
            discardHalf();
            return;
        } else {
            board.produceResources(roll);
        }

        log("End of resource production phase.\n");
        tradePhase();
    }

    // harbor, stockpile, domestic
    public void tradePhase() {
        log("Start of trade phase.");
        gameController.showTrade();
        phase = Phase.TRADE;

        // gameController.actionButtonEnabled = true;
        gameController.updateButtons();
    }

    public void discardHalf() {
        TreeMap<Player, Integer> playerMap = new TreeMap<>();

        for (Player player: players) {
            if (player.getStockpile().getTotal() > 7)
                playerMap.put(player, player.getStockpile().getTotal() / 2);
        }

        if (playerMap.size() > 0) {
            gameController.showDiscard(playerMap);
        } else {
            log("No players have more than 7 resources. No one discards.");
            gameController.showAvailableRobberTiles();
        }
        gameController.updatePlayerStats();
    }

    public void stealFromPlayer(Player player) {
        board.stealRandomResource(player);
        gameController.enableButtons();
    }

    // endregion

    // region Buy Phase
    public void buyPhase() {
        log("End of trade phase.\n");
        log("Start of buy phase.");

        phase = Phase.BUY;

        // GameController.actionButtonEnabled = true;
        gameController.disableTrade();
        gameController.updateButtons();
    }

    public void buyDevelopmentCard() {
        Stockpile playerStockpile = currentPlayer.getStockpile();

        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.ORE, 1);
        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.WOOL, 1);
        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.WHEAT, 1);

        DevelopmentCard card = board.drawDevelopmentCard();
        currentPlayer.addDevelopmentCard(card);
        boughtDevelopmentCard = card;

        // log(currentPlayer + " obtain a " + card.getType().name().replaceAll("_", " ") + " card.");

        if (card.getType() != DevelopmentCardType.VICTORY_POINT) {
            log("Bought card cannot be played on this turn.");
        }

        updatePlayerStats();
        gameController.updatePlayerCards();
        GameState.checkWin();
    }

    public void buyRoad() {
        log("Hover over an available edge and place a road.");

        Stockpile playerStockpile = currentPlayer.getStockpile();

        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.BRICK, 1);
        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.WOOD, 1);
        gameController.updatePlayerStats();

        showRoads();
    }

    public void buySettlement() {
        log("Hover over an available vertex and place a settlement.");

        Stockpile playerStockpile = currentPlayer.getStockpile();

        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.BRICK, 1);
        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.WOOL, 1);
        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.WOOD, 1);
        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.WHEAT, 1);
        gameController.updatePlayerStats();

        showSettlements();
    }

    public void buyCity() {
        log("Hover over an existing settlement to upgrade to a city.");

        Stockpile playerStockpile = currentPlayer.getStockpile();

        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.ORE, 3);
        board.transferResources(playerStockpile, Board.getStockpile(), ResourceType.WHEAT, 2);
        gameController.updatePlayerStats();

        showSettlementToCityUpgrades();
    }

    // endregion

    // TODO: remember that development cards can be played at any time, including before the roll
    // TODO: only 1 development card can be played per turn; obtained development card only played on a different turn

    public void useRoadBuildingCard() {
        if (!currentPlayer.hasTypeOfDevelopmentCard("Road Building")) {
            log("You do not have a road building card.");
            return;
        }

        log(currentPlayer + " played a Road Building card.");
        log("Place two roads.");
        showRoads(true);
    }

    public void useYearOfPlentyCard() {
        if (!currentPlayer.hasTypeOfDevelopmentCard("Year of Plenty")) {
            log("You do not have a year of plenty card.");
            return;
        }

        log(currentPlayer + " played a Year of Plenty card.");
        log("Choose two resources to obtain.");
        HelloApplication.showYearOfPlentyMenu();
    }

    public void useMonopolyCard() {
        if (!currentPlayer.hasTypeOfDevelopmentCard("Monopoly")) {
            log("You do not have a monopoly card.");
            return;
        }

        log(currentPlayer + " played a Monopoly card.");
        log("Choose a resource to steal from all players.");
        HelloApplication.showMonopolyMenu();
    }


    // region Place settlements/roads

    private void showSettlements() {
        HashSet<Vertex> availableVertices = board.availableSettlementPlacements(isGameStart);
        gameController.disableButtons();

        for (Vertex vertex : structureMap.values()) {
            // no player has placed a settlement on this vertex
            if (availableVertices.contains(vertex) && vertex.getStructure() == null) {
                ImageView image = vertex.getImage();

                image.getStyleClass().add("hover");
                image.setVisible(true);

                image.setOnMouseEntered(event -> {
                    vertex.getImage().setOpacity(1);
                    System.out.println("Hover over vertex " + vertex.getId());
                });
                image.setOnMouseExited(event -> vertex.getImage().setOpacity(0));
                image.setOnMouseClicked(event -> buildSettlement(vertex, image));
            }
        }
    }

    private void showSettlementToCityUpgrades() {
        HashSet<Vertex> availableSettlementUpgrades = board.availableSettlementToCityUpgrades();
        gameController.disableButtons();

        for (Vertex vertex : structureMap.values()) {
            if (availableSettlementUpgrades.contains(vertex)) {
                ImageView image = vertex.getImage();

                Image settlementImage = currentPlayer.getImages().get("Settlement");
                Image hoverImage = Initialize.settlementHover;

                image.getStyleClass().add("hover");
                image.setVisible(true);

                image.setOnMouseEntered(event -> {
                    image.setImage(hoverImage);
                    System.out.println("Hover over vertex " + vertex.getId());
                });
                image.setOnMouseExited(event -> image.setImage(settlementImage));
                image.setOnMouseClicked(event -> upgradeSettlement(vertex, image));
            }
        }
    }

    private void upgradeSettlement(Vertex vertex, ImageView image) {
        log(currentPlayer + " upgraded a settlement to city.");

        board.upgradeSettlementToCity(vertex);
        gameController.updatePlayerStats();
        image.setImage(currentPlayer.getImages().get("City"));
        image.setFitHeight(38);
        image.setFitWidth(26);
        image.setX(image.getX() + 3);
        image.setY(image.getY() - 3);
        disableVertices();

        gameController.enableButtons();
    }

    private void buildSettlement(Vertex vertex, ImageView image) {
        log(currentPlayer + " placed a settlement." + (phase == Phase.SETUP ? "\n" : ""));

        board.placeSettlement(vertex);
        Board.updateLongestRoad();
        gameController.updatePlayerStats();

        image.setImage(currentPlayer.getImages().get("Settlement"));
        image.setFitHeight(32);
        image.setFitWidth(32);
        disableVertices();

        if (phase == Phase.SETUP) {
            SETUP_placedSettlement = true;
            setUpPhase();
        } else {
            gameController.enableButtons();
        }
    }

    public void disableVertices(){
        for (Vertex vertex : structureMap.values()) {
            ImageView image = vertex.getImage();
            image.getStyleClass().remove("hover");

            if (vertex.getStructure() == null) image.setVisible(false);

            image.setOnMouseEntered(Event::consume);
            image.setOnMouseExited(Event::consume);
            image.setOnMouseClicked(Event::consume);
        }
    }

    public void showRoads() {
        HashSet<Edge> availableEdges;

        // when placing second road
        if (phase == Phase.SETUP && SETUP_backwards) {
            availableEdges = board.availableRoadPlacements(currentPlayer.getStructures().get(1));
        } else {
            availableEdges = board.availableRoadPlacements();
        }

        gameController.disableButtons();

        for (Edge e : roadMap.values()) {
            if (availableEdges.contains(e) && e.getRoad() == null) {
                Rectangle rect = e.getRectangle();
                rect.getStyleClass().add("hover");

                rect.setOnMouseEntered(event -> {
                    rect.setFill(javafx.scene.paint.Color.GRAY);
                    System.out.println("Hover over edge " + e.getId());
                });

                rect.setOnMouseExited(event -> rect.setFill(javafx.scene.paint.Color.TRANSPARENT));
                rect.setOnMouseClicked(event -> buildRoad(e, rect, false));
            }
        }
    }

    private int numOfPlacedRoads = 0; // ONLY FOR ROAD BUILDING
    public void showRoads(boolean isRoadBuilding) {
        if (!isRoadBuilding) return;

        HashSet<Edge> availableEdges = board.availableRoadPlacements();
        gameController.disableButtons();

        for (Edge e : roadMap.values()) {
            if (availableEdges.contains(e) && e.getRoad() == null) {
                Rectangle rect = e.getRectangle();

                rect.setOnMouseEntered(event -> {
                    rect.setFill(javafx.scene.paint.Color.GRAY);
                    System.out.println("Hover over edge " + e.getId());
                });

                rect.setOnMouseExited(event -> rect.setFill(javafx.scene.paint.Color.TRANSPARENT));
                rect.setOnMouseClicked(event -> buildRoad(e, rect, true));
            }
        }
    }

    public void buildRoad(Edge edge, Rectangle rect, boolean isRoadBuilding) {
        log(currentPlayer + " placed a road." + (phase == Phase.SETUP ? "\n" : ""));

        rect.setFill(currentPlayer.getColorHex());
        board.placeRoad(edge);
        gameController.updatePlayerStats();

        if (isRoadBuilding) numOfPlacedRoads++;

        disableBuildRoad();
        if (phase == Phase.SETUP) {
            SETUP_placedRoad = true;
            setUpPhase();
        } else if (!isRoadBuilding || numOfPlacedRoads == 2) {
            numOfPlacedRoads = 0;
            gameController.enableButtons();
        } else {
            showRoads(true);
        }
    }

    public void disableBuildRoad() {
        for (Edge e : roadMap.values()) {
            Rectangle rect = e.getRectangle();
            rect.getStyleClass().remove("hover");

            rect.setOnMouseEntered(Event::consume);
            rect.setOnMouseExited(Event::consume);
            rect.setOnMouseClicked(Event::consume);
        }
    }

    // Corresponding road buttons with each road
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
                // create six roads for each tile
                for (int orientation = 0; orientation < 6; orientation++) {
                    createSingularRoad(r, c, orientation, roadPane);
                }
            }
        }
    }

    public void createSingularRoad(int r, int c, int i, Pane roadPane) {
        int[][] roadOffset = new int[][]{{-78, -30, -59}, {-30, -56, 0}, {17, -30, 59}, {17, 22, -59}, {-30, 50, 0}, {-78, 23, 59}};
        Tile tile = Board.getBoard()[r][c];
        Edge edge = tile.getEdge(i);

        if (edge.getRectangle() != null) return;

        int x = (int) tile.getPolygon().getLayoutX();
        int y = (int) tile.getPolygon().getLayoutY();

        Location loc = new Location(x + roadOffset[i][0], y + roadOffset[i][1]);
        Rectangle rect = new Rectangle();

        rect.setX(loc.getX());
        rect.setY(loc.getY());

        rect.setRotate(roadOffset[i][2]);
        rect.setWidth(61);
        rect.setHeight(7);
        rect.setFill(javafx.scene.paint.Color.TRANSPARENT);

        roadPane.getChildren().add(rect);

        edge.setScreenLocation(loc);
        edge.setRectangle(rect);
        roadMap.put(rect, edge);
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
                // create six settlement places for each tile
                for (int orientation = 0; orientation < 6; orientation++) {
                    createSingularSettlement(r, c, orientation, settlementPane);
                }
            }
        }
    }

    public void createSingularSettlement(int r, int c, int i, Pane settlementPane) {
        int[][] roadOffset = new int[][]{{-48, -70}, {17, -70}, {44, -18}, {17, 36}, {-48, 36}, {-75, -18}};

        Tile[][] tiles = Board.getBoard();
        Vertex vertex = tiles[r][c].getVertices()[i];

        if (tiles[r][c].getVertex(i).getImage() != null) return;


        int x = (int) tiles[r][c].getPolygon().getLayoutX();
        int y = (int) tiles[r][c].getPolygon().getLayoutY();

        Location loc = new Location(x + roadOffset[i][0], y + roadOffset[i][1]);
        ImageView settl = new ImageView(Initialize.settlementHover);
        settl.setX(loc.getX());
        settl.setY(loc.getY());
        settl.maxHeight(32);
        settl.maxWidth(32);
        settlementPane.getChildren().add(settl);
        settl.setOpacity(0);

        vertex.setImage(settl);
        structureMap.put(settl, vertex);
    }

    // endregion

    public void moveRobber(Location location) {
        Location oldLocation = Board.getRobberLocation();

        if (board.moveRobber(location)) {
            gameController.updateRobberLocation(location, oldLocation);
            GameState.updatePlayerStats();
        }
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static Player[] getPlayers() {
        return players;
    }

    public static int getNumOfPlayers() {
        return players.length;
    }

    public static Board getBoard() {
        return board;
    }

    public static GameController getGameController() {
        return gameController;
    }
}
