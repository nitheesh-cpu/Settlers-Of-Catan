package game.catan.simulation;

import game.catan.simulation.cards.DevelopmentCard;
import game.catan.simulation.enums.Color;
import game.catan.simulation.enums.ResourceType;
import game.catan.simulation.helper.Location;

import java.util.*;

public class GameState {

    // TODO: allow player to play development card anytime through their turn

    public static boolean isGameStart = true;

    private static Player currentPlayer = null;
    private static int currentPlayerIndex = 0;

    private static Player[] players;
    private final Board board;

    private Player longestRoadHolder = null;
    private Player largestArmyHolder = null;

    private Scanner sc;

    public GameState(Scanner sc, int numPlayers, int seed) {
        this.sc = sc;
        Dice.init(seed);
        board = new Board();
        players = new Player[numPlayers];

        initializePlayers(numPlayers);

//        nextTurn();
//        placeSettlement();
//
//        while (true) {
//            placeRoad();
//            System.out.println("LONGEST ROAD: " + currentPlayer.longestRoad());
//        }

        setUpPhase();

        while (true) {
            resourceProductionPhase();
            tradePhase();
            buyPhase();

            nextTurn();
        }

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

    // region Setup Phase
    public void setUpPhase() {
        for (int i = 0; i < players.length; i++) {
            placeSettlement();
            placeRoad();
            nextTurn();
        }

        for (int i = 0; i < players.length; i++) {
            placeSettlement();
            placeRoad();
            board.produceResources(currentPlayer.getStructures().get(1));

            for (Player player: players) {
                System.out.println(player + " - " + player.getStockpile());
            }

            nextTurn();
        }

        isGameStart = false;
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


    // region Place settlements/roads
    public void placeSettlement() {
        System.out.println("Available settlement placements: " + board.availableSettlementPlacements(isGameStart));
        System.out.println("Enter location to place settlement: ");
        String[] input = sc.nextLine().split(" ");

        if (input[0].equals("skip"))
            return;

        boolean flag = board.placeSettlement(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));

        while (!flag) {
            System.out.println("Available settlement placements: " + board.availableSettlementPlacements(isGameStart));
            System.out.println("Invalid settlement placement, try again: ");
            input = sc.nextLine().split(" ");
            flag = board.placeSettlement(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));
        }

        System.out.println("Available harbors: " + currentPlayer.getHarbors());
    }

    public void placeRoad() {
        System.out.println("Available road placements: " + board.availableRoadPlacements());
        System.out.println("Enter location to place road: ");
        String[] input = sc.nextLine().split(" ");

        if (input[0].equals("skip"))
            return;

        boolean flag = board.placeRoad(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));

        while (!flag) {
            System.out.println("Available road placements: " + board.availableRoadPlacements());
            System.out.println("Invalid road placement, try again: ");
            input = sc.nextLine().split(" ");
            flag = board.placeRoad(new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])));
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
}
