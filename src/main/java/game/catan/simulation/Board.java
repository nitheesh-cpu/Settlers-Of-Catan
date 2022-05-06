package game.catan.simulation;

import game.catan.graphics.GameController;
import game.catan.simulation.buildings.Road;
import game.catan.simulation.buildings.Structure;
import game.catan.simulation.cards.DevelopmentCard;
import game.catan.simulation.enums.DevelopmentCardType;
import game.catan.simulation.enums.Phase;
import game.catan.simulation.enums.ResourceType;
import game.catan.simulation.enums.StructureType;
import game.catan.simulation.helper.Edge;
import game.catan.simulation.helper.Location;
import game.catan.simulation.helper.Vertex;

import java.util.*;

public class Board {

    private static Tile[][] board;
    private static HashMap<Integer, Vertex> vertices = new HashMap<>(); // key: vertex id, value: vertex
    private static HashMap<Integer, Edge> edges = new HashMap<>(); // key: edge id, value: edge
    private static ArrayList<Harbor> harbors = new ArrayList<>();
    private static Stockpile boardStockpile = new Stockpile(19, 19, 19, 19, 19);

    private HashMap<Location, Tile> tileLocations;
    private HashMap<Integer, ArrayList<Tile>> resourceTiles; // key: tile number, value: list of tiles
    private static Location robberLocation; // location of tile with robber

    private static Player longestRoadHolder = null;
    private static Player largestArmyHolder = null;

    private static Stack<DevelopmentCard> developmentCards;

    public Board() {
        initializeBoard();
        initializeDevelopmentCards();
    }

    // region Structure/Road Placement

    public void placeSettlement(Vertex vertex) {
        vertex.setStructure(new Structure(vertex, GameState.getCurrentPlayer()));
        GameState.getCurrentPlayer().addStructure(vertex.getStructure());
        GameState.checkWin();
    }

    public void placeRoad(Edge edge) {
        edge.setRoad(new Road(edge, GameState.getCurrentPlayer()));
        GameState.getCurrentPlayer().addRoad(edge.getRoad());
    }

    public void upgradeSettlementToCity(Vertex vertex) {
        vertex.getStructure().upgradeToCity();
        GameState.checkWin();
    }

    public HashSet<Edge> availableRoadPlacements()  {
        Player currentPlayer = GameState.getCurrentPlayer();
        ArrayList<Structure> playerStructures = currentPlayer.getStructures();
        HashSet<Edge> availableEdges = new HashSet<>(); // available edges to place a road on

        // must connect to a structure
        // can lead of an existing road
        // can not be placed on top of another road
        // can not be placed on top of a structure
        // can not go through other players' structures

        // loop through all player structures' adjacent edges
        for (Structure s: playerStructures) {
            Vertex vertex = s.getVertex();
            Edge[] adjacentEdges = vertex.getAdjacentEdges();

            for (Edge e: adjacentEdges) {
                if (e == null) continue;
                // if an adjacent edge to a structure doesn't have a road on it
                if (e.getRoad() == null) {
                    availableEdges.add(e);
                }
            }
        }

        for (Road r: currentPlayer.getRoads()) {
            Vertex[] adjacentVertices = r.getEdge().getAdjacentVertices();

            for (Vertex v: adjacentVertices) {
                // cannot build road through another player's structure
                if (v.getStructure() != null && !v.getStructure().getOwner().equals(currentPlayer)) continue;

                // either the vertex is occupied by the current player's structure or the adjacent edges have no road
                Edge[] adjacentEdges = v.getAdjacentEdges();

                for (Edge e: adjacentEdges) {
                    if (e == null) continue;

                    if (e.getRoad() == null) {
                        availableEdges.add(e);
                    }
                }
            }
        }

        return availableEdges;
    }

    // primarily for second settlement placement
    public HashSet<Edge> availableRoadPlacements(Structure structure) {
        HashSet<Edge> availableEdges = new HashSet<>();
        Edge[] adjacentEdges = structure.getVertex().getAdjacentEdges();

        for (Edge e: adjacentEdges) {
            if (e == null) continue;
            // if an adjacent edge to a structure doesn't have a road on it
            if (e.getRoad() == null) {
                availableEdges.add(e);
            }
        }

        return availableEdges;
    }

    public HashSet<Vertex> availableSettlementPlacements(boolean startOfGame) {
        // build on unoccupied vertex
        // none of the 3 adjacent vertices contain a structure **distance rule
        // must always be connected to a road
        // exception where it's the start of the game and the player can build anywhere

        Player currentPlayer = GameState.getCurrentPlayer();

        ArrayList<Vertex> allVertices = new ArrayList<>();
        HashSet<Vertex> availableVertices = new HashSet<>();
        HashSet<Vertex> excludedVertices = new HashSet<>();

        for (Integer key: vertices.keySet()) {
            allVertices.add(vertices.get(key));
        }

        // excludes all vertices which are occupied by a structure and break the distance rule
        for (Vertex v: allVertices) {
            if (v.getStructure() == null) continue;

            // the vertex has a structure
            excludedVertices.add(v);

            Edge[] adjacentEdges = v.getAdjacentEdges();

            // distance rule
            for (Edge e: adjacentEdges) {
                if (e == null) continue;

                Vertex[] adjacentVertices = e.getAdjacentVertices();
                Vertex adjacentVertex = adjacentVertices[0].equals(v) ? adjacentVertices[1] : adjacentVertices[0];

                excludedVertices.add(adjacentVertex);
            }
        }

        // includes all vertices that are unoccupied and do not violate the distance rule
        if (startOfGame) {
            for (Vertex v: allVertices) {
                if (excludedVertices.contains(v)) continue;
                availableVertices.add(v);
            }

            return availableVertices;
        }

        // TODO: complete!! ** need to test
        // settlement must connect to player's roads
        for (Road r: currentPlayer.getRoads()) {
            Edge e = r.getEdge();
            Vertex[] adjacentVertices = e.getAdjacentVertices();

            for (Vertex v: adjacentVertices) {
                if (excludedVertices.contains(v)) continue;
                availableVertices.add(v);
            }
        }

        return availableVertices;
    }

    public HashSet<Vertex> availableSettlementToCityUpgrades() {
        Player currentPlayer = GameState.getCurrentPlayer();
        HashSet<Vertex> availableVertices = new HashSet<>();

        for (Structure structure: currentPlayer.getStructures()) {
            if (structure.getType() == StructureType.SETTLEMENT) {
                availableVertices.add(structure.getVertex());
            }
        }

        return availableVertices;
    }

    // endregion

    // region Resources
    public void produceResources(int diceRoll) {
        ArrayList<Tile> tiles = resourceTiles.get(diceRoll);
        HashMap<Player, TreeMap<ResourceType, Integer>> resourcesGained = new HashMap<>();

        for (Tile t: tiles) {
            if (t.getLocation().equals(robberLocation)) continue;
            if (t.getResource() == ResourceType.DESERT) continue;

            ResourceType resource = t.getResource();
            Vertex[] tileVertices = t.getVertices();

            for (Vertex v: tileVertices) {
                if (v.getStructure() == null) continue;

                Structure s = v.getStructure();
                Player owner = s.getOwner();
                Stockpile playerStockpile = owner.getStockpile();

                if (!resourcesGained.containsKey(owner)) resourcesGained.put(owner, new TreeMap<>());

                switch (s.getType()) {
                    case SETTLEMENT -> {
                        final int AMOUNT = 1;
                        if (boardStockpile.getResourceCount(resource) < AMOUNT) continue;

                        resourcesGained.get(owner).put(resource, resourcesGained.get(owner).get(resource) != null ? resourcesGained.get(owner).get(resource) + AMOUNT : AMOUNT);
                        transferResources(boardStockpile, playerStockpile, resource, AMOUNT);
                    }
                    case CITY -> {
                        final int AMOUNT = 2;
                        if (boardStockpile.getResourceCount(resource) < AMOUNT) continue;

                        transferResources(boardStockpile, playerStockpile, resource, AMOUNT);
                    }
                }
            }
        }

        for (Player player: resourcesGained.keySet()) {
            String message = "";

            TreeMap<ResourceType, Integer> resources = resourcesGained.get(player);

            for (ResourceType resource: resources.keySet()) {
                int count = resources.get(resource);
                message += count + "x " +  resource.name() + ", ";
            }

            if (message.length() > 0) message = message.substring(0, message.length() - 2);
            else message = "no resources";

            GameState.log(player + " gained " + message + ".");
            GameState.updatePlayerStats();
        }
    }

    // when player gains resources from second settlement at beginning of game
    public void produceResources(Structure structure) {
        Vertex vertex = structure.getVertex();
        ArrayList<Tile> productionTiles = new ArrayList<>();
        TreeMap<ResourceType, Integer> resourceGained = new TreeMap<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Vertex[] tileVertices = board[i][j].getVertices();

                for (Vertex v: tileVertices) {
                    if (v.equals(vertex)) {
                        productionTiles.add(board[i][j]);
                        break;
                    }
                }
            }
        }

        for (Tile t: productionTiles) {
            ResourceType resource = t.getResource();

            if (t.getLocation().equals(robberLocation)) continue;
            if (resource == ResourceType.DESERT) continue;

            switch (structure.getType()) {
                case SETTLEMENT -> {
                    final int AMOUNT = 1;
                    if (boardStockpile.getResourceCount(resource) < AMOUNT) continue;

                    transferResources(boardStockpile, structure.getOwner().getStockpile(), resource, AMOUNT);
                    resourceGained.put(resource, resourceGained.get(resource) != null ? resourceGained.get(resource) + AMOUNT : AMOUNT);
                }
                case CITY -> {
                    final int AMOUNT = 2;
                    if (boardStockpile.getResourceCount(resource) < AMOUNT) continue;

                    transferResources(boardStockpile, structure.getOwner().getStockpile(), resource, AMOUNT);
                    resourceGained.put(resource, resourceGained.get(resource) != null ? resourceGained.get(resource) + AMOUNT : AMOUNT);
                }
            }
        }

        String message = "";

        for (ResourceType resource: resourceGained.keySet()) {
            int count = resourceGained.get(resource);
            message += count + "x " +  resource.name() + ", ";
        }

        if (message.length() == 0) {
            message = "no resources";
        } else {
            message = message.trim();
            message = message.substring(0, message.length() - 1);
        }

        GameState.log(structure.getOwner() + " gained " + message + "."  + (GameState.getPhase() == Phase.SETUP ? "\n" : ""));
        GameState.updatePlayerStats();
    }
    // endregion

    // region Robbing
    public boolean moveRobber(Location location) {
        if (location.equals(robberLocation)) return false;

        robberLocation = location;
        return true;
    }


    // All players around robber tile
    public ArrayList<Player> getAvailablePlayersToStealFrom() {
        Tile robberTile = board[robberLocation.getX()][robberLocation.getY()];
        Vertex[] vertices = robberTile.getVertices();

        ArrayList<Player> availablePlayers = new ArrayList<>();

        for (Vertex v: vertices) {
            Structure structure = v.getStructure();

            if (structure != null && !structure.getOwner().equals(GameState.getCurrentPlayer()) && structure.getOwner().getStockpile().getTotal() > 0) {
                availablePlayers.add(structure.getOwner());
            }
        }

        availablePlayers.remove(GameState.getCurrentPlayer());

        return availablePlayers;
    }

    public void stealRandomResource(Player player) {
        Player currentPlayer = GameState.getCurrentPlayer();
        ArrayList<ResourceType> availableResourcesToSteal = new ArrayList<>();

        for (ResourceType resource: ResourceType.values()) {
            if (player.getStockpile().getResourceCount(resource) > 0) {
                availableResourcesToSteal.add(resource);
            }
        }

        if (availableResourcesToSteal.size() == 0) {
            GameState.log(currentPlayer + " stole no resources from " + player + ".");
        }

        ResourceType randomResource = availableResourcesToSteal.get(Dice.getRef().nextInt(availableResourcesToSteal.size()));

        Stockpile currentPlayerStockpile = currentPlayer.getStockpile();
        Stockpile playerStockpile = player.getStockpile();

        transferResources(playerStockpile, currentPlayerStockpile, randomResource, 1);

        GameState.log(currentPlayer + " stole 1x " + randomResource + " from " + player + ".");
        GameState.updatePlayerStats();
    }
    // endregion

    // region Trading
    public void transferResources(Stockpile giver, Stockpile receiver, ResourceType resource, int amount) {
        giver.remove(resource, amount);
        receiver.add(resource, amount);
    }

    // for bulk trades
    public void transferResources(Stockpile traderOne, Stockpile traderTwo, Stockpile traderOneTradingResources, Stockpile traderTwoTradingResources) {
        traderOne.remove(traderOneTradingResources);
        traderTwo.remove(traderTwoTradingResources);

        traderOne.add(traderTwoTradingResources);
        traderTwo.add(traderOneTradingResources);
    }


    // endregion

    // region Development Cards

    public DevelopmentCard drawDevelopmentCard() {
        return developmentCards.pop();
    }

    // endregion

    // region Special Cards

    public static void updateLongestRoad() {
        Player previousPlayer = longestRoadHolder;

        for (Player player: GameState.getPlayers()) {
            int roadLength = player.longestRoad();
            GameState.log(player + " has a longest road of " + roadLength + ".");

            if (roadLength < 5) continue;

            if (longestRoadHolder == null) {
                longestRoadHolder = player;
                continue;
            }

            if (roadLength > longestRoadHolder.longestRoad()) {
                longestRoadHolder = player;
            }
        }

        // if the longest road holder no longer has a road length of 5
        if (previousPlayer != null && previousPlayer.equals(longestRoadHolder) && longestRoadHolder.getLengthOfLongestRoad() < 5) {
            GameState.log(longestRoadHolder + " no longer has a road length of 5. Longest road card has been removed.");
            longestRoadHolder.removeLongestRoad();
            GameState.getGameController().updatePlayerStats();
            GameState.updatePlayerStats();
            longestRoadHolder = null;
            return;
        }

        if (longestRoadHolder == null || (previousPlayer != null && previousPlayer.equals(longestRoadHolder))) return;

        longestRoadHolder.addLongestRoad();

        if (previousPlayer != null) {
            previousPlayer.removeLongestRoad();
        }

        GameState.log(longestRoadHolder + " is the new longest road holder of road length " + longestRoadHolder.getLengthOfLongestRoad() + ".");
        GameState.getGameController().updatePlayerCards();
        GameState.checkWin();
    }

    public static void updateLargestArmy() {
        Player previousPlayer = largestArmyHolder;

        for (Player player: GameState.getPlayers()) {
            int armySize = player.getNumOfKnights();
            if (armySize < 3) continue;

            if (largestArmyHolder == null) {
                largestArmyHolder = player;
                continue;
            }

            if (armySize > largestArmyHolder.getNumOfKnights()) {
                largestArmyHolder = player;
            }
        }

        if (largestArmyHolder == null || (largestArmyHolder.equals(previousPlayer))) return;

        largestArmyHolder.addLargestArmy();

        if (previousPlayer != null) {
            previousPlayer.removeLargestArmy();
        }

        GameState.log(largestArmyHolder + " is the new largest army holder of army size " + largestArmyHolder.getNumOfKnights() + ".");
        GameState.getGameController().updatePlayerCards();
        GameState.checkWin();
    }

    // region Initialization
    private void initializeBoard() {
        System.out.println("Initializing board...");
        board = new Tile[5][];

        board[0] = new Tile[3];
        board[1] = new Tile[4];
        board[2] = new Tile[5];
        board[3] = new Tile[4];
        board[4] = new Tile[3];

        setTiles();
        setHarbors();
        setNumberTokens();
    }

    private void initializeDevelopmentCards() {
        System.out.println("Initializing development cards...");
        developmentCards = new Stack<>();

        final int KNIGHT_COUNT = 14;
        final int VICTORY_POINT_COUNT = 5;
        final int PROGRESS_COUNT = 6;

        String[] victoryPointNames = {"Chapel", "Great Hall", "Library", "Market", "University"};
        String[] progressNames = {"Monopoly", "Road Building", "Year of Plenty"};

        for (int i = 0; i < KNIGHT_COUNT; i++) {
            developmentCards.push(new DevelopmentCard("Knight", DevelopmentCardType.KNIGHT));
        }

        for (int i = 0; i < VICTORY_POINT_COUNT; i++) {
            developmentCards.push(new DevelopmentCard(victoryPointNames[i], DevelopmentCardType.VICTORY_POINT));
        }

        for (int i = 0; i < PROGRESS_COUNT/2; i++) {
            developmentCards.push(new DevelopmentCard(progressNames[i], DevelopmentCardType.PROGRESS));
        }

        Collections.shuffle(developmentCards, Dice.getRef());

        System.out.println(developmentCards);
    }

    private void setTiles() {
        tileLocations = new HashMap<>();
        resourceTiles = new HashMap<>();

        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            tiles.add(new Tile(ResourceType.WHEAT));
            tiles.add(new Tile(ResourceType.WOOD));
            tiles.add(new Tile(ResourceType.WOOL));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(ResourceType.BRICK));
            tiles.add(new Tile(ResourceType.ORE));
        }

        tiles.add(new Tile(ResourceType.DESERT));

        Collections.shuffle(tiles, Dice.getRef());

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Location location = new Location(row, col);

                board[row][col] = tiles.remove(0);
                board[row][col].setLocation(location);

                if (board[row][col].getResource() == ResourceType.DESERT) {
                    robberLocation = location;
                }

                tileLocations.put(location, board[row][col]);
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col].setAdjacentTiles();
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col].setAdjacentVertices();
                board[row][col].setAdjacentEdges();
                board[row][col].setAdjacentEdgesToVertices();
                board[row][col].setAdjacentVerticesToEdges();
                board[row][col].setAdjacentTilesToVertices();
            }
        }
    }

    private void setHarbors() {
        final int MISC_HARBORS = 4;

        for (int i = 0; i < MISC_HARBORS; i++) {
            harbors.add(new Harbor(ResourceType.MISC));
        }

        harbors.add(new Harbor(ResourceType.BRICK));
        harbors.add(new Harbor(ResourceType.ORE));
        harbors.add(new Harbor(ResourceType.WOOL));
        harbors.add(new Harbor(ResourceType.WHEAT));
        harbors.add(new Harbor(ResourceType.WOOD));

        Collections.shuffle(harbors, Dice.getRef());

        Vertex[] vertices;

        // 0 2
        vertices = new Vertex[] {board[0][2].getVertex(Vertex.NORTHWEST), board[0][2].getVertex(Vertex.NORTHEAST)};
        harbors.get(0).setVertices(vertices);

        // 1 3
        vertices = new Vertex[] {board[1][3].getVertex(Vertex.NORTHEAST), board[1][3].getVertex(Vertex.EAST)};
        harbors.get(1).setVertices(vertices);

        // 3 3
        vertices = new Vertex[] {board[3][3].getVertex(Vertex.NORTHEAST), board[3][3].getVertex(Vertex.EAST)};
        harbors.get(2).setVertices(vertices);

        // 4 2
        vertices = new Vertex[] {board[4][2].getVertex(Vertex.EAST), board[4][2].getVertex(Vertex.SOUTHEAST)};
        harbors.get(3).setVertices(vertices);

        // 4 1
        vertices = new Vertex[] {board[4][1].getVertex(Vertex.SOUTHEAST), board[4][1].getVertex(Vertex.SOUTHWEST)};
        harbors.get(4).setVertices(vertices);

        // 3 0
        vertices = new Vertex[] {board[3][0].getVertex(Vertex.SOUTHEAST), board[3][0].getVertex(Vertex.SOUTHWEST)};
        harbors.get(5).setVertices(vertices);

        // 2 0
        vertices = new Vertex[] {board[2][0].getVertex(Vertex.SOUTHWEST), board[2][0].getVertex(Vertex.WEST)};
        harbors.get(6).setVertices(vertices);

        // 1 0
        vertices = new Vertex[] {board[1][0].getVertex(Vertex.WEST), board[1][0].getVertex(Vertex.NORTHWEST)};
        harbors.get(7).setVertices(vertices);

        // 0 1
        vertices = new Vertex[] {board[0][1].getVertex(Vertex.WEST), board[0][1].getVertex(Vertex.NORTHWEST)};
        harbors.get(8).setVertices(vertices);
    }

    private void setNumberTokens() {
        ArrayList<Integer> tokens = new ArrayList<>();

        Scanner sc = new Scanner("5 2 6 3 8 10 9 12 11 4 8 10 9 4 5 6 3 11");

        while (sc.hasNextInt()) {
            tokens.add(sc.nextInt());
        }

        sc.close();

        // left column
        for (int row = 0; row < board.length; row++) {
            if (board[row][0].getResource() != ResourceType.DESERT && board[row][0].getNumber() == -1) {
                board[row][0].setNumber(tokens.remove(0));
            }
        }

        // bottom row
        for (int col = 0; col < board[board.length-1].length; col++) {
            if (board[4][col].getResource() != ResourceType.DESERT && board[4][col].getNumber() == -1) {
                board[4][col].setNumber(tokens.remove(0));
            }
        }

        // right column
        for (int row = board.length-1; row >= 0; row--) {
            if (board[row][board[row].length-1].getResource() != ResourceType.DESERT && board[row][board[row].length-1].getNumber() == -1) {
                board[row][board[row].length-1].setNumber(tokens.remove(0));
            }
        }

        // top row
        if (board[0][1].getResource() != ResourceType.DESERT && board[0][1].getNumber() == -1) {
            board[0][1].setNumber(tokens.remove(0));
        }

        // middle row
        for (int row = 1; row < board.length-1; row++) {
            if (board[row][1].getResource() != ResourceType.DESERT && board[row][1].getNumber() == -1) {
                board[row][1].setNumber(tokens.remove(0));
            }
        }

        // middle right column
        for (int row = 3; row >= 0; row--) {
            if (board[row][board[row].length-2].getResource() != ResourceType.DESERT && board[row][board[row].length-2].getNumber() == -1) {
                board[row][board[row].length-2].setNumber(tokens.remove(0));
            }
        }

        // middle
        if (board[2][2].getResource() != ResourceType.DESERT && board[2][2].getNumber() == -1) {
            board[2][2].setNumber(tokens.remove(0));
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getNumber() == -1) continue;
                int number = board[row][col].getNumber();

                if (!resourceTiles.containsKey(number)) {
                    resourceTiles.put(number, new ArrayList<>());
                }

                resourceTiles.get(number).add(board[row][col]);
            }
        }
    }
    // endregion

    // region Static methods
    public static void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
    }

    public static void addEdge(Edge e) {
        edges.put(e.getId(), e);
    }

    public static boolean isValidCoordinate(Location location) {
        try {
            Tile locationTest = board[location.getRow()][location.getCol()];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static boolean isValidCoordinate(int row, int col) {
        return isValidCoordinate(new Location(row, col));
    }

    public static Tile[][] getBoard() {
        return board;
    }

    public static ArrayList<Harbor> getHarbors() {
        return harbors;
    }

    public static Tile getTile(int row, int col) {
        return getTile(new Location(row, col));
    }

    public static Tile getTile(Location l) {
        if (!isValidCoordinate(l.getRow(), l.getCol())) {
            return null;
        }

        return board[l.getRow()][l.getCol()];
    }

    public static Stockpile getStockpile() {
        return boardStockpile;
    }

    public static Location getRobberLocation() {
        return robberLocation;
    }

    public static int getDevelopmentCardCount() {
        return developmentCards.size();
    }

    public static Player getLongestRoadHolder() {
        return longestRoadHolder;
    }

    public static Player getLargestArmyHolder() {
        return largestArmyHolder;
    }

    // endregion

    public String toString() {
        return Arrays.deepToString(board).replace("], ", "]\n").replace("[[", "[").replace("]]", "]");
    }
}
