package game.catan.simulation;

import game.catan.graphics.GameController;
import game.catan.simulation.enums.ResourceType;

public class Trade {

    private static GameState gameState;
    private static Board board;

    private static ResourceType STOCKPILE_tradingResource = null;
    private static ResourceType STOCKPILE_receivingResource = null;

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
        resetStockpileResources();
        return true;
    }

    public static void setTradingResource(ResourceType tradingResource) {
        Trade.STOCKPILE_tradingResource = tradingResource;
    }

    public static void setReceivingResource(ResourceType receivingResource) {
        Trade.STOCKPILE_receivingResource = receivingResource;
    }

    public static ResourceType getTradingResource() {
        return STOCKPILE_tradingResource;
    }

    public static ResourceType getReceivingResource() {
        return STOCKPILE_receivingResource;
    }

    public static void resetStockpileResources() {
        Trade.STOCKPILE_tradingResource = null;
        Trade.STOCKPILE_receivingResource = null;
    }
}
