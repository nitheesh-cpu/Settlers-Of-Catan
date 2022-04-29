package game.catan.simulation;

import game.catan.graphics.GameController;
import game.catan.simulation.buildings.Road;
import game.catan.simulation.buildings.Structure;
import game.catan.simulation.cards.DevelopmentCard;
import game.catan.simulation.enums.Color;
import game.catan.simulation.enums.Phase;
import game.catan.simulation.enums.ResourceType;
import game.catan.simulation.helper.Edge;
import game.catan.simulation.helper.Location;
import game.catan.simulation.helper.Vertex;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class GameState {

    // TODO: allow player to play development card anytime through their turn

    public static boolean isGameStart = true;

    private static Player currentPlayer = null;
    private static int currentPlayerIndex = 0;
    private static Phase phase = Phase.SETUP;

    public static Player[] players;
    private final Board board;

    private Player longestRoadHolder = null;
    private Player largestArmyHolder = null;

    private HashMap<Rectangle, Edge> roadMap;
    private HashMap<ImageView, Vertex> structureMap;

    public static GameController gameController;
    public static Scanner sc = new Scanner(System.in);

    public GameState(int numPlayers, int seed, GameController controller) {
        Dice.init(seed);

        gameController = controller;
        board = new Board();
        players = new Player[numPlayers];
        roadMap = new HashMap<>();
        structureMap = new HashMap<>();

        initializePlayers(numPlayers);
    }

    public void start() {
        setUpPhase();

        //        nextTurn();
//        placeSettlement();
//
//        while (true) {
//            placeRoad();
//            System.out.println("LONGEST ROAD: " + currentPlayer.longestRoad());
//        }

//        setUpPhase();
//
//        while (true) {
//            resourceProductionPhase();
//            tradePhase();
//            buyPhase();
//
//            nextTurn();
//        }


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

    public static void nextTurn() {
        // TODO: check win condition
        currentPlayerIndex++;

        if (currentPlayerIndex >= players.length)
            currentPlayerIndex = 0;

        currentPlayer = players[currentPlayerIndex];
        System.out.println("Next turn: " + currentPlayer);

        // TODO: player rolls dice
    }

    public static int[] rollDice() {
        Dice.roll();
        int diceOne = Dice.getFaceTwo();
        int diceTwo = Dice.getFaceOne();

        gameController.actionLogText.appendText("Dice rolled\n");
        gameController.updateDiceGraphic(diceOne, diceTwo);

        return new int[]{diceOne, diceTwo};
    }

    public static Phase getPhase() {
        return phase;
    }

    // region Setup Phase
    private boolean SETUP_placedSettlement = false;
    private boolean SETUP_placedRoad = false;
    public void setUpPhase() {
        if (!SETUP_placedSettlement) {
            placeSettlement();
        } else if (!SETUP_placedRoad) {
            placeRoad();
        } else {
            // produce resources
            System.out.println("Producing resources...");
        }

//        for (int i = 0; i < players.length; i++) {
//            placeSettlement();
//        }
//            placeRoad();
//            nextTurn();
//        }
//
//        for (int i = 0; i < players.length; i++) {
//            placeSettlement();
//            placeRoad();
//            board.produceResources(currentPlayer.getStructures().get(1));
//
//            for (Player player: players) {
//                System.out.println(player + " - " + player.getStockpile());
//            }
//
//            nextTurn();
//        }
//
//        isGameStart = false;
    }

    private void initializePlayers(int numPlayers) {
        TreeMap<Integer, Player> playerMap = new TreeMap<>();

        for (int i = 0; i < numPlayers; i++) {
            int roll = Dice.roll();

            while (playerMap.containsKey(roll)) {
                roll = Dice.roll();
            }

            playerMap.put(roll, new Player(Color.values()[i]));
        }

        Set<Integer> keys = playerMap.keySet();

        int index = numPlayers - 1;

        for (Integer key: keys) {
            players[index] = playerMap.get(key);
            index--;
        }

        currentPlayer = players[0];
    }
    // endregion

    // region Resource Production Phase
    public void resourceProductionPhase() {
        int diceRoll = Dice.roll();
        System.out.println(currentPlayer + " rolled " + diceRoll);

        if (diceRoll == 7) {
            discardHalf();
            moveRobber();
            stealResource();
        } else {
            board.produceResources(diceRoll);
        }
    }

    public void discardHalf() {
        for (Player player: players) {
            if (player.getStockpile().getTotal() > 7) {
                final int AMOUNT_TO_DISCARD = player.getStockpile().getTotal() / 2;

                // TODO: implement way for each player to choose which resources to discard
                System.out.println("PLAYER " + player.getId() + "");
            }
        }
    }

    public void moveRobber() {
        System.out.println("Enter location to move robber: ");
        String[] input = sc.nextLine().split(" ");
        boolean flag = board.moveRobber(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1])));

        while (!flag) {
            System.out.println("Invalid location. Enter location to move robber: ");
            flag = board.moveRobber(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1])));
        }
    }

    public void stealResource() {
        System.out.println("Available players: " + board.getAvailablePlayersToStealFrom());
        System.out.println("Enter player number to steal from:");

        int playerNum = sc.nextInt();
        Player player = null;
        boolean flag = false;

        while (!flag) {
            for (Player p: players) {
                if (p.getId() == playerNum) {
                    player = p;
                    flag = true;
                    break;
                }
            }

            System.out.println("Invalid player number. Enter player number to steal from:");
            playerNum = sc.nextInt();
        }

        board.stealRandomResource(player);
    }
    // endregion

    // region Trade Phase
    // TODO: complete this first
    public void tradePhase() {
        System.out.println("Start of the trade phase");
        System.out.println("Enter next action: ");
        String input = sc.nextLine();

        while (!input.equalsIgnoreCase("stop")) {
            switch (input.toLowerCase(Locale.ROOT)) {
                case "domestic" -> // TODO: need to test
                        domesticTrade();
                case "stockpile" -> // TODO: need to test
                        stockpileTrade();
                case "harbor" -> // TODO: need to test
                        harborTrade();
            }

            System.out.println("Enter next action: ");
            input = sc.nextLine();
        }
    }

    // TODO: must trade resource cards of different types
    public void domesticTrade() {
        System.out.println("Enter player number to trade with: ");

        int playerNum = sc.nextInt();
        Player traderOne = currentPlayer;
        Player traderTwo = null;

        if (playerNum > players.length || playerNum < 1 || playerNum == currentPlayer.getId()) {
            System.out.println("Invalid player number.");
            return;
        }

        for (Player player: players) {
            if (player.getId() == playerNum) {
                traderTwo = player;
                break;
            }
        }

        System.out.println("+=-=-=-Domestic Trade-=-=-=+");
        System.out.println("Enter amount of resources in order of: brick, wool, ore, wheat, and wood (separated by spaces)");
        System.out.println();
        System.out.println("PLAYER " + traderOne.getId() + "'s " + traderOne.getStockpile());
        System.out.println("PLAYER " + traderOne.getId() + ", enter resources to trade");

        String[] input = sc.nextLine().split(" ");
        Stockpile traderOneTradingResources = new Stockpile();

        traderOneTradingResources.add(ResourceType.BRICK, Integer.parseInt(input[0]));
        traderOneTradingResources.add(ResourceType.WOOL, Integer.parseInt(input[1]));
        traderOneTradingResources.add(ResourceType.ORE, Integer.parseInt(input[2]));
        traderOneTradingResources.add(ResourceType.WHEAT, Integer.parseInt(input[3]));
        traderOneTradingResources.add(ResourceType.WOOD, Integer.parseInt(input[4]));

        if (!verifyTrade(traderOne.getStockpile(), traderOneTradingResources)) {
            System.out.println("Invalid trade. Terminated domestic trade");
            System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            return;
        }

        System.out.println("PLAYER " + traderTwo.getId() + "'s " + traderTwo.getStockpile());
        System.out.println("PLAYER " + traderTwo.getId() + ", enter resources to trade");

        input = sc.nextLine().split(" ");
        Stockpile traderTwoTradingResources = new Stockpile();

        traderTwoTradingResources.add(ResourceType.BRICK, Integer.parseInt(input[0]));
        traderTwoTradingResources.add(ResourceType.WOOL, Integer.parseInt(input[1]));
        traderTwoTradingResources.add(ResourceType.ORE, Integer.parseInt(input[2]));
        traderTwoTradingResources.add(ResourceType.WHEAT, Integer.parseInt(input[3]));
        traderTwoTradingResources.add(ResourceType.WOOD, Integer.parseInt(input[4]));

        if (!verifyTrade(traderOne.getStockpile(), traderTwoTradingResources)) {
            System.out.println("Invalid trade. Terminated domestic trade");
            System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            return;
        }

        System.out.println("Player " + traderOne.getId() + "'s trade:\n" + traderOneTradingResources);
        System.out.println("Player " + traderTwo.getId() + "'s trade:\n" + traderTwoTradingResources);

        System.out.println("\nCONFIRM TRADE? (y/n)");

        if (!sc.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Trade cancelled.");
            System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            return;
        }

        board.transferResources(traderOne.getStockpile(), traderTwo.getStockpile(), traderOneTradingResources, traderTwoTradingResources);

        System.out.println("TRADE ACCEPTED");
        System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
    }

    public void stockpileTrade() {
        System.out.println("+=-=-=-Stockpile Trade-=-=-=+");
        System.out.println("4 identical resources for 1 different resource");

        System.out.println("Enter which resource you would like to trade:");
        String resourceInput = sc.nextLine();
        ResourceType tradingResource = null;
        ResourceType receivingResource = null;

        try {
            tradingResource = ResourceType.valueOf(resourceInput.toUpperCase());

            if (currentPlayer.getStockpile().getResourceCount(tradingResource) < 4) {
                System.out.println("You do not have enough of that resource to trade.");
                System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
                return;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid resource. Terminated stockpile trade");
            System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            return;
        }

        System.out.println("\nEnter which resource you would like to trade for:");

        try {
            receivingResource = ResourceType.valueOf(sc.nextLine().toUpperCase());

            if (receivingResource == tradingResource) {
                throw new IllegalArgumentException();
            }

            if (Board.getStockpile().getResourceCount(receivingResource) < 1) {
                System.out.println("Board does not have enough resources to trade for that resource.");
                System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
                return;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid resource. Terminated stockpile trade");
            System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
        }

        // Give board 4 of the trading resource
        board.transferResources(currentPlayer.getStockpile(), Board.getStockpile(), tradingResource, 4);

        // Give current player 1 of the receiving resource
        board.transferResources(Board.getStockpile(), currentPlayer.getStockpile(), receivingResource, 1);

        System.out.println("TRADE ACCEPTED");
        System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
    }

    public void harborTrade() {
        ArrayList<Harbor> availableHarbors = currentPlayer.getHarbors();

        if (availableHarbors.size() == 0) {
            System.out.println("You do not have any harbors to trade with.");
            return;
        }

        System.out.println("+=-=-=-Harbor Trade-=-=-=+");
        System.out.println("Available harbors: " + availableHarbors);

        System.out.println("\nEnter harbor number to trade with: ");
        int harborInput = sc.nextInt();
        sc.nextLine();

        Harbor harbor = null;
        for (Harbor h : availableHarbors) {
            if (h.getId() == harborInput) {
                harbor = h;
                break;
            }
        }

        if (harbor == null) {
            System.out.println("Invalid harbor number. Terminated harbor trade");
            System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            return;
        }

        System.out.println("Harbor ratio: " + harbor.getRatio() + ":1");
        System.out.println("Harbor resource: " + harbor.getResourceType());

        ResourceType tradingResource;
        ResourceType receivingResource;

        // Determine what resource the player wants to trade
        if (harbor.getResourceType() != ResourceType.MISC) {
            tradingResource = harbor.getResourceType();
        } else {
            System.out.println("Enter resource to trade: ");

            try {
                tradingResource = ResourceType.valueOf(sc.nextLine().toUpperCase());

                if (currentPlayer.getStockpile().getResourceCount(tradingResource) < 1) {
                    System.out.println("Not enough resources to trade");
                    System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
                    return;
                }


            } catch (IllegalArgumentException e) {
                System.out.println("Invalid resource. Terminated harbor trade");
                System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
                return;
            }
        }

        // Determine what resource the player wants to receive
        System.out.println("Enter resource to receive: ");

        try {
            receivingResource = ResourceType.valueOf(sc.nextLine().toUpperCase());

            if (Board.getStockpile().getResourceCount(receivingResource) < 1) {
                System.out.println("Not enough resources to trade");
                System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid resource. Terminated harbor trade");
            System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
            return;
        }

        // Transfer resources from player to board
        board.transferResources(currentPlayer.getStockpile(), Board.getStockpile(), tradingResource, harbor.getRatio());

        // Transfer resources from board to player
        board.transferResources(Board.getStockpile(), currentPlayer.getStockpile(), receivingResource, 1);

        System.out.println("TRADE ACCEPTED");
        System.out.println("+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+");
    }

    // verify that trader has sufficient resources given proposed trade
    public boolean verifyTrade(Stockpile traderStockpile, Stockpile tradingResources) {
        return traderStockpile.getResourceCount(ResourceType.BRICK) >= tradingResources.getResourceCount(ResourceType.BRICK) &&
                traderStockpile.getResourceCount(ResourceType.WOOL) >= tradingResources.getResourceCount(ResourceType.WOOL) &&
                traderStockpile.getResourceCount(ResourceType.ORE) >= tradingResources.getResourceCount(ResourceType.ORE) &&
                traderStockpile.getResourceCount(ResourceType.WHEAT) >= tradingResources.getResourceCount(ResourceType.WHEAT) &&
                traderStockpile.getResourceCount(ResourceType.WOOD) >= tradingResources.getResourceCount(ResourceType.WOOD);
    }
    // endregion

    // region Buy Phase

    public void buyPhase() {
        System.out.println("Start of the trade phase");
        System.out.println("Enter next action: ");
        String input = sc.nextLine();

        while (!input.equalsIgnoreCase("stop")) {
            switch (input.toLowerCase(Locale.ROOT)) {
                case "road":
                    buyRoad();
                    break;
                case "settlement":
                    buySettlement();
                    break;
                case "city":
                    buyCity();
                    break;
                case "development":
                    buyDevelopmentCard();
                    break;
            }

            System.out.println("Enter next action: ");
            input = sc.nextLine();
        }
    }

    public void buyRoad() {
        Stockpile playerStockpile = currentPlayer.getStockpile();

        if (playerStockpile.getResourceCount(ResourceType.BRICK) < 1 || playerStockpile.getResourceCount(ResourceType.WOOD) < 1) {
            System.out.println("Not enough resources to buy road");
            return;
        }

        playerStockpile.remove(ResourceType.BRICK, 1);
        playerStockpile.remove(ResourceType.WOOD, 1);

        placeRoad();
    }

    public void buySettlement() {
        Stockpile playerStockpile = currentPlayer.getStockpile();

        if (playerStockpile.getResourceCount(ResourceType.BRICK) < 1 || playerStockpile.getResourceCount(ResourceType.WOOL) < 1 ||
                playerStockpile.getResourceCount(ResourceType.WOOD) < 1 || playerStockpile.getResourceCount(ResourceType.WHEAT) < 1) {
            System.out.println("Not enough resources to buy settlement");
            return;
        }

        playerStockpile.remove(ResourceType.BRICK, 1);
        playerStockpile.remove(ResourceType.WOOL, 1);
        playerStockpile.remove(ResourceType.WOOD, 1);
        playerStockpile.remove(ResourceType.WHEAT, 1);

        placeSettlement();
    }

    public void buyCity() {
        Stockpile playerStockpile = currentPlayer.getStockpile();

        if (playerStockpile.getResourceCount(ResourceType.ORE) < 3 || playerStockpile.getResourceCount(ResourceType.WHEAT) < 2) {
            System.out.println("Not enough resources to buy city");
            return;
        }

        playerStockpile.remove(ResourceType.ORE, 3);
        playerStockpile.remove(ResourceType.WHEAT, 2);

        upgradeToCity();
    }

    public void buyDevelopmentCard() {
        Stockpile playerStockpile = currentPlayer.getStockpile();

        if (playerStockpile.getResourceCount(ResourceType.ORE) < 1 || playerStockpile.getResourceCount(ResourceType.WHEAT) < 1 || playerStockpile.getResourceCount(ResourceType.WOOL) < 1) {
            System.out.println("Not enough resources to buy development card");
            return;
        }

        DevelopmentCard drawnCard = board.drawDevelopmentCard();

        if (drawnCard == null) {
            System.out.println("No more development cards left");
            return;
        }

        playerStockpile.remove(ResourceType.ORE, 1);
        playerStockpile.remove(ResourceType.WHEAT, 1);
        playerStockpile.remove(ResourceType.WOOL, 1);

        currentPlayer.addDevelopmentCard(drawnCard);
    }
    // endregion

    // TODO: remember that development cards can be played at any time, including before the roll
    // TODO: only 1 development card can be played per turn; obtained development card only played on a different turn
    public void useDevelopmentCard() {

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

//        // loop through all tiles and create 6 roads for each tile
//        for (int r = 0; r < tiles.length; r++) {
//            for (int c = 0; c < tiles[r].length; c++) {
//                for (int i = 0; c == tiles[r].length - 1 ? (r > 1 ? i < 5 : i < 4) : i < 3; i++) {
//                    createSingularRoad(r, c, i, roadPane);
//                }
//            }
//        }
//
//        createSingularRoad(2, 0, 5, roadPane);
//        createSingularRoad(3, 0, 5, roadPane);
//        createSingularRoad(4, 0, 4, roadPane);
//        createSingularRoad(4, 0, 5, roadPane);
//        createSingularRoad(4, 1, 4, roadPane);
//        createSingularRoad(4, 1, 5, roadPane);
//        createSingularRoad(4, 2, 5, roadPane);

//        for (int r = 0; r < tiles.length; r++) {
//            for (int c = 0; c < tiles[r].length; c++) {
//                Road[] w = tiles[r][c].getRoads();
//                Edge[] e = tiles[r][c].getEdges();
//                if (w[3] == null) {
//                    w[3] = tiles[r][c + 1].getRoads()[0];
//                    e[3] = tiles[r][c + 1].getEdges()[0];
//                }
//                if (w[4] == null) {
//                    if (r < 2){ w[4] = tiles[r + 1][c + 1].getRoads()[1];
//                        e[4] = tiles[r + 1][c + 1].getEdges()[1];}
//                    else{ w[4] = tiles[r + 1][c].getRoads()[1];
//                        e[4] = tiles[r + 1][c].getEdges()[1];}
//                }
//                if (w[5] == null) {
//                    if (r < 2){ w[5] = tiles[r + 1][c].getRoads()[2];
//                        e[5] = tiles[r + 1][c].getEdges()[2];}
//                    else{ w[5] = tiles[r + 1][c - 1].getRoads()[2];
//                        e[5] = tiles[r + 1][c - 1].getEdges()[2];}
//                }
//                tiles[r][c].setRoads(w);
//                tiles[r][c].setEdges(e);
//            }
//        }
    }

    public void createSingularRoad(int r, int c, int i, Pane roadPane) {
        // int[][] roadOffset = new int[][]{{-78, 23, 59}, {-78, -30, -59}, {-30, -56, 0}, {17, -30, 59}, {17, 22, -59}, {-30, 50, 0}};
        int[][] roadOffset = new int[][]{{-78, -30, -59}, {-30, -56, 0}, {17, -30, 59}, {17, 22, -59}, {-30, 50, 0}, {-78, 23, 59}};
        Tile tile = Board.getBoard()[r][c];
        Edge edge = tile.getEdge(i);

        if (edge.getRectangle() != null) return;

//        int temp = i+1;
//        if (temp >= roadOffset.length) temp = 0;

//        Road[] w = tiles[r][c].getRoads();

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
//        rect.setOnMouseClicked(event -> gameController.actionLogText.appendText("Road clicked\n"));
//        rect.setOnMouseExited(event ->
//                rect.setFill(Color.RED)
//        );

        roadPane.getChildren().add(rect);

//        Road road = new Road(loc, rect);
//        Edge edge = new Edge(road, loc);

//        roadMap.put(rect, road);
        edge.setScreenLocation(loc);
        edge.setRectangle(rect);
        roadMap.put(rect, edge);

//        w[i] = road;
//        edges[i] = edge;
//        tiles[r][c].setRoads(w);
//        tiles[r][c].setEdges(edges);
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

//        for (int r = 0; r < tiles.length; r++) {
//            for (int c = 0; c < tiles[r].length; c++) {
//                for (int i = 0; (c == tiles[r].length - 1 ? (r == 2 || r == 3 ? i < 4 : i < 3) : i < 2); i++) {
//                    createSingularSettlement(r, c, i, settlementPane);
//                }
//            }
//        }
//
//        createSingularSettlement(2, 0, 5, settlementPane);
//        createSingularSettlement(3, 0, 5, settlementPane);
//        createSingularSettlement(4, 0, 5, settlementPane);
//        createSingularSettlement(4, 0, 3, settlementPane);
//        createSingularSettlement(4, 0, 4, settlementPane);
//        createSingularSettlement(4, 1, 3, settlementPane);
//        createSingularSettlement(4, 1, 4, settlementPane);
//        createSingularSettlement(4, 2, 3, settlementPane);
//        createSingularSettlement(4, 2, 4, settlementPane);
//
//        for (int r = 0; r < tiles.length; r++) {
//            for (int c = 0; c < tiles[r].length; c++) {
//                Structure[] w = tiles[r][c].getStructures();
//                Vertex[] q = tiles[r][c].getVertices();
//                if (w[2] == null) {
//                    w[2] = tiles[r][c + 1].getStructures()[0];
//                    q[2] = tiles[r][c + 1].getVertices()[0];
//                }
//                if (w[3] == null) {
//                    if (r < 2){w[3] = tiles[r + 1][c + 1].getStructures()[1];
//                        q[3] = tiles[r + 1][c + 1].getVertices()[1];}
//                    else{ w[3] = tiles[r + 1][c].getStructures()[1];
//                        q[3] = tiles[r + 1][c].getVertices()[1];}
//                }
//                try {
//                    if (w[4] == null) {
//                        if (r < 2){ w[4] = tiles[r + 1][c + 1].getStructures()[0];
//                            q[4] = tiles[r + 1][c + 1].getVertices()[0];}
//                        else{ w[4] = tiles[r + 1][c].getStructures()[0];
//                            q[4] = tiles[r + 1][c].getVertices()[0];}
//                    }
//                } catch (ArrayIndexOutOfBoundsException ignored) {
//                }
//                if (w[5] == null) {
//                    if (r < 2){ w[5] = tiles[r + 1][c].getStructures()[1];
//                        q[5] = tiles[r + 1][c].getVertices()[1];}
//                    else if (r == 4){ w[5] = tiles[r][c - 1].getStructures()[3];
//                        q[5] = tiles[r][c - 1].getVertices()[3];}
//                    else{ w[5] = tiles[r + 1][c - 1].getStructures()[1];
//                        q[5] = tiles[r + 1][c - 1].getVertices()[1];}
//                }
//                tiles[r][c].setStructures(w);
//                tiles[r][c].setVertices(q);
//            }
//        }
//
//        Structure[] w = tiles[2][4].getStructures();
//        Vertex[] q = tiles[2][4].getVertices();
//        w[4] =  tiles[3][3].getStructures()[2];
//        q[4] =  tiles[3][3].getVertices()[2];
//        tiles[2][4].setStructures(w);
//        tiles[2][4].setVertices(q);
//        w = tiles[3][3].getStructures();
//        q = tiles[3][3].getVertices();
//        w[4] =  tiles[4][2].getStructures()[2];
//        q[4] =  tiles[4][2].getVertices()[2];
//        tiles[3][3].setStructures(w);
//        tiles[3][3].setVertices(q);
    }

    public void createSingularSettlement(int r, int c, int i, Pane settlementPane) {
        // int[][] roadOffset = new int[][]{{-75, -18}, {-48, -70}, {17, -70}, {44, -18}, {17, 36}, {-48, 36}};
        int[][] roadOffset = new int[][]{{-48, -70}, {17, -70}, {44, -18}, {17, 36}, {-48, 36}, {-75, -18}};

        Tile[][] tiles = Board.getBoard();
        Vertex vertex = tiles[r][c].getVertices()[i];

        if (tiles[r][c].getVertex(i).getImage() != null) return;

//        Structure[] w = tiles[r][c].getStructures();
//        Vertex[] q = tiles[r][c].getVertices();

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

        vertex.setImage(settl);


//        Structure settlement = new Structure(loc, settl);
//        Vertex v = new Vertex(settlement, loc);
//        settl.setOnMouseClicked(event -> gameController.actionLogText.appendText("Settlement clicked\n"));
        //make Structure class
        structureMap.put(settl, vertex);
//        w[i] = settlement;
//        q[i] = v;
//        tiles[r][c].setStructures(w);
//        tiles[r][c].setVertices(q);
    }


    // region Place settlements/roads
    public void placeSettlement() {
        showSettlements();
//        System.out.println("Available settlement placements: " + board.availableSettlementPlacements(isGameStart));
//        System.out.println("Enter location to place settlement: ");
//        String[] input = sc.nextLine().split(" ");
//
//        if (input[0].equals("skip"))
//            return;
//
//        boolean flag = board.placeSettlement(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));
//
//        while (!flag) {
//            System.out.println("Available settlement placements: " + board.availableSettlementPlacements(isGameStart));
//            System.out.println("Invalid settlement placement, try again: ");
//            input = sc.nextLine().split(" ");
//            flag = board.placeSettlement(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));
//        }
//
//        System.out.println("Available harbors: " + currentPlayer.getHarbors());
    }

    private void showSettlements() {
        HashSet<Vertex> availableVertices = board.availableSettlementPlacements(isGameStart);

        for (Vertex vertex : structureMap.values()) {
            // no player has placed a settlement on this vertex
            if (availableVertices.contains(vertex) && vertex.getStructure() == null) {
                ImageView image = vertex.getImage();
                image.setOnMouseEntered(event -> {
                    vertex.getImage().setOpacity(1);
                    System.out.println("Hover over vertex " + vertex.getId());
                });
                image.setOnMouseExited(event -> vertex.getImage().setOpacity(0));
                image.setOnMouseClicked(event -> buildSettlement(vertex, image));
            }
        }
    }

    private void buildSettlement(Vertex vertex, ImageView image) {
        board.placeSettlement(vertex);
        gameController.updatePlayerStats();
        image.setImage(currentPlayer.getImages().get("Settlement"));
        disableBuildSettlement();

        if (phase == Phase.SETUP) {
            SETUP_placedSettlement = true;
            setUpPhase();
        }
    }

    public void disableBuildSettlement(){
        for (Vertex vertex : structureMap.values()) {
            ImageView image = vertex.getImage();

            image.setOnMouseEntered(Event::consume);
            image.setOnMouseExited(Event::consume);
            image.setOnMouseClicked(Event::consume);
        }
    }

//    public void placeRoad() {
//        System.out.println("Available road placements: " + board.availableRoadPlacements());
//        System.out.println("Enter location to place road: ");
//        String[] input = sc.nextLine().split(" ");
//
//        if (input[0].equals("skip"))
//            return;
//
//        boolean flag = board.placeRoad(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));
//
//        while (!flag) {
//            System.out.println("Available road placements: " + board.availableRoadPlacements());
//            System.out.println("Invalid road placement, try again: ");
//            input = sc.nextLine().split(" ");
//            flag = board.placeRoad(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));
//        }
//    }

    public void placeRoad() {
        showRoads();
    }

    public void showRoads() {
        HashSet<Edge> availableEdges = board.availableRoadPlacements();

        for (Edge e : roadMap.values()) {
            if (availableEdges.contains(e) && e.getRoad() == null) {
                Rectangle rect = e.getRectangle();

                rect.setOnMouseEntered(event -> {
                    rect.setFill(javafx.scene.paint.Color.YELLOW);
                    System.out.println("Hover over edge " + e.getId());
                });
                rect.setOnMouseExited(event -> rect.setFill(javafx.scene.paint.Color.TRANSPARENT));
                rect.setOnMouseClicked(event -> buildRoad(e, rect));
            }
        }
    }

    public void buildRoad(Edge edge, Rectangle rect) {
        board.placeRoad(edge);
        gameController.updatePlayerStats();
        rect.setFill(javafx.scene.paint.Color.web(currentPlayer.getColorHex()));
        disableBuildRoad();

        if (phase == Phase.SETUP) {
            SETUP_placedRoad = true;
            setUpPhase();
        }
    }

    public void disableBuildRoad() {
        for (Edge e : roadMap.values()) {
            Rectangle rect = e.getRectangle();
            rect.setOnMouseEntered(Event::consume);
            rect.setOnMouseExited(Event::consume);
            rect.setOnMouseClicked(Event::consume);
        }
    }

    public void upgradeToCity() {
        System.out.println("Available settlements to upgrade: " + board.availableSettlementToCityUpgrades());
        System.out.println("Enter location to upgrade settlement to city: ");
        String[] input = sc.nextLine().split(" ");

        boolean flag = board.upgradeSettlementToCity(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));

        while (!flag) {
            System.out.println("Available settlements to upgrade: " + board.availableSettlementToCityUpgrades());
            System.out.println("Invalid settlement upgrade, try again: ");
            input = sc.nextLine().split(" ");
            flag = board.upgradeSettlementToCity(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));
        }
    }
    // endregion

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static Player[] getPlayers() {
        return players;
    }

    public static int getNumOfPlayers() {
        return players.length;
    }

    public Board getBoard() {
        return board;
    }
}
