package game.catan.simulation;

import game.catan.simulation.enums.ResourceType;

public class Trade {

    private static GameState gameState;
    private static Board board;

    private static ResourceType tradingResource = null;
    private static ResourceType receivingResource = null;

    private static Harbor harbor = null;

    private static Player playerOne;
    private static Stockpile tradeOne;

    private static Player playerTwo;
    private static Stockpile tradeTwo;

    public static void initialize(GameState gameState) {
        Trade.gameState = gameState;
        board = GameState.getBoard();
    }

    public static boolean verifyAmountOfResources(Stockpile stockpile, ResourceType resource, int minimumAmount) {
        return stockpile.getResourceCount(resource) >= minimumAmount;
    }

    public static boolean stockpileTrade(ResourceType tradingResource, ResourceType receivingResource) {
        Player player = GameState.getCurrentPlayer();

        if (player.getStockpile().getResourceCount(tradingResource) < 4) {
            System.out.println("Not enough resources to trade");
            return false;
        } else if (Board.getStockpile().getResourceCount(receivingResource) == 0) {
            System.out.println("No resources to receive");
            return false;
        }

        // Player gives 4 of the tradingResource in return for 1 receivingResource
        board.transferResources(player.getStockpile(), Board.getStockpile(), tradingResource, 4);
        board.transferResources(Board.getStockpile(), player.getStockpile(), receivingResource, 1);

        GameState.getGameController().updatePlayerStats();
        GameState.log(GameState.getCurrentPlayer() + " traded 4x " + tradingResource + " for 1x " + receivingResource + " with the stockpile.");

        resetTrade();
        return true;
    }

    public static boolean harborTrade(ResourceType tradingResource, ResourceType receivingResource, Harbor harbor) {
        Player player = GameState.getCurrentPlayer();
        int ratio = harbor.getRatio();

        if (harbor.getResourceType() != ResourceType.MISC && harbor.getResourceType() != tradingResource) {
            System.out.println("Player must trade for the same resource type as the harbor");
            return false;
        }

        if (player.getStockpile().getResourceCount(tradingResource) < ratio) {
            System.out.println("Not enough resources to trade");
            return false;
        } else if (Board.getStockpile().getResourceCount(receivingResource) == 0) {
            System.out.println("No resources to receive");
            return false;
        }

        board.transferResources(player.getStockpile(), Board.getStockpile(), tradingResource, ratio);
        board.transferResources(Board.getStockpile(), player.getStockpile(), receivingResource, 1);

        GameState.getGameController().updatePlayerStats();
        GameState.log(GameState.getCurrentPlayer() + " traded " + ratio + "x " + tradingResource + " for 1x " + receivingResource + " with the harbor of type " + harbor.getResourceType() + ".");

        resetTrade();
        return true;
    }

    public static boolean domesticTrade(Player playerOne, Stockpile tradeOne, Player playerTwo, Stockpile tradeTwo) {
        for (ResourceType resource : ResourceType.values()) {
            if (!verifyAmountOfResources(playerOne.getStockpile(), resource, tradeOne.getResourceCount(resource))) {
                System.out.println("Player one doesn't have enough resources to trade");
                return false;
            }

            if (!verifyAmountOfResources(playerTwo.getStockpile(), resource, tradeTwo.getResourceCount(resource))) {
                System.out.println("Player two doesn't have enough resources to trade");
                return false;
            }

            // trading same resource
            if (tradeOne.getResourceCount(resource) != 0 && tradeTwo.getResourceCount(resource) != 0) {
                System.out.println("Cannot trade same resource");
                return false;
            }
        }

        board.transferResources(playerOne.getStockpile(), playerTwo.getStockpile(), tradeOne, tradeTwo);

        String tradeOneMsg = "";
        String tradeTwoMsg = "";

        for (ResourceType resource : ResourceType.values()) {
            if (tradeOne.getResourceCount(resource) != 0) {
                tradeOneMsg += tradeOne.getResourceCount(resource) + "x " + resource + ", ";
            }

            if (tradeTwo.getResourceCount(resource) != 0) {
                tradeTwoMsg += tradeTwo.getResourceCount(resource) + "x " + resource + ", ";
            }
        }

        GameState.getGameController().updatePlayerStats();
        GameState.log(GameState.getCurrentPlayer() + " traded " + tradeOneMsg.substring(0, tradeOneMsg.length()-2) + " with " + playerTwo + " for " + tradeTwoMsg.substring(0, tradeTwoMsg.length()-2) + ".");

        resetTrade();
        return true;
    }

    public static void obtainResource(ResourceType resource) {
        Player player = GameState.getCurrentPlayer();
        board.transferResources(Board.getStockpile(), player.getStockpile(), resource, 1);

        GameState.log(player + " obtained 1x " + resource + ".");
        GameState.getGameController().updatePlayerStats();
    }

    public static void stealAllResources(ResourceType resource) {
        Player player = GameState.getCurrentPlayer();

        for (Player otherPlayer : GameState.getPlayers()) {
            if (player.equals(otherPlayer)) continue;

            int amount = otherPlayer.getStockpile().getResourceCount(resource);
            if (amount == 0) continue;

            board.transferResources(otherPlayer.getStockpile(), player.getStockpile(), resource, amount);
            GameState.log(player + " stole " + amount + "x " + resource + " from " + otherPlayer + ".");
        }

        GameState.getGameController().updatePlayerStats();
    }

    public static void discardResource(Player player, ResourceType resource, int amount) {
        if (player.getStockpile().getResourceCount(resource) < amount) {
            System.out.println("Player doesn't have enough resources to discard");
            return;
        }

        board.transferResources(player.getStockpile(), Board.getStockpile(), resource, amount);
        GameState.log(player + " discarded " + amount + "x " + resource + ".");
        GameState.getGameController().updatePlayerStats();
    }

    public static void setTradingResource(ResourceType tradingResource) {
        Trade.tradingResource = tradingResource;
    }

    public static void setReceivingResource(ResourceType receivingResource) {
        Trade.receivingResource = receivingResource;
    }

    public static void setTradeOne(Stockpile tradeOne) {
        Trade.tradeOne = tradeOne;
    }

    public static void setTradeTwo(Stockpile tradeTwo) {
        Trade.tradeTwo = tradeTwo;
    }

    public static void setHarbor(Harbor harbor) {
        Trade.harbor = harbor;
    }

    public static void setPlayerOne(Player playerOne) {
        Trade.playerOne = playerOne;
    }

    public static void setPlayerTwo(Player playerTwo) {
        Trade.playerTwo = playerTwo;
    }

    public static ResourceType getTradingResource() {
        return tradingResource;
    }

    public static ResourceType getReceivingResource() {
        return receivingResource;
    }

    public static Stockpile getTradeOne() {
        return tradeOne;
    }

    public static Stockpile getTradeTwo() {
        return tradeTwo;
    }

    public static Harbor getHarbor() {
        return harbor;
    }

    public static Player getPlayerOne() {
        return playerOne;
    }

    public static Player getPlayerTwo() {
        return playerTwo;
    }

    public static void resetResources() {
        Trade.tradingResource = null;
        Trade.receivingResource = null;
    }

    public static void resetHarbor() {
        harbor = null;
    }

    public static void resetDomestic() {
        tradeOne = null;
        tradeTwo = null;
        playerOne = null;
        playerTwo = null;
    }

    public static void resetTrade() {
        resetResources();
        resetHarbor();
        resetDomestic();
    }
}
