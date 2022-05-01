package game.catan.simulation;

import game.catan.simulation.enums.ResourceType;

public class Trade {

    private static GameState gameState;
    private static Board board;

    private static ResourceType tradingResource = null;
    private static ResourceType receivingResource = null;

    private static Harbor harbor = null;

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
        resetResources();
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

        resetHarbor();
        resetResources();
        return true;
    }

    public static void setTradingResource(ResourceType tradingResource) {
        Trade.tradingResource = tradingResource;
    }

    public static void setReceivingResource(ResourceType receivingResource) {
        Trade.receivingResource = receivingResource;
    }

    public static ResourceType getTradingResource() {
        return tradingResource;
    }

    public static ResourceType getReceivingResource() {
        return receivingResource;
    }

    public static void resetResources() {
        Trade.tradingResource = null;
        Trade.receivingResource = null;
    }

    public static Harbor getHarbor() {
        return harbor;
    }

    public static void setHarbor(Harbor harbor) {
        Trade.harbor = harbor;
    }

    public static void resetHarbor() {
        harbor = null;
    }
}
