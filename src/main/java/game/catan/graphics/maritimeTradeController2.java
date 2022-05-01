package game.catan.graphics;

import game.catan.simulation.Board;
import game.catan.simulation.Trade;
import game.catan.simulation.enums.ResourceType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

public class maritimeTradeController2 implements Initializable {

    public Pane root;

    public void brickClicked(MouseEvent event) {
        handleTrade(ResourceType.BRICK);
    }

    public void lumberClicked(MouseEvent event) {
        handleTrade(ResourceType.WOOD);
    }

    public void oreClicked(MouseEvent event) {
        handleTrade(ResourceType.ORE);
    }

    public void wheatClicked(MouseEvent event) {
        handleTrade(ResourceType.WHEAT);
    }

    public void woolClicked(MouseEvent event) {
        handleTrade(ResourceType.WOOL);
    }

    public void handleTrade(ResourceType resourceType) {
        if (resourceType == Trade.getTradingResource()) {
            errorModal("You can't trade for the same resource!", "Trade Error");
            return;
        }

        if (!Trade.verifyAmountOfResources(Board.getStockpile(), resourceType, 1)) {
            errorModal("Stockpile doesn't have enough of the specified resource to give!");
            return;
        }

        Trade.setReceivingResource(resourceType);
        HelloApplication.toggleMaritimeTradeMenu2();

        switch (maritimeTradeController.tradeType) {
            case STOCKPILE -> Trade.stockpileTrade(Trade.getTradingResource(), Trade.getReceivingResource());
            case HARBOR -> Trade.harborTrade(Trade.getTradingResource(), Trade.getReceivingResource(), Trade.getHarbor());
        }

        maritimeTradeController.tradeType = null;
        maritimeTradeController.harbor = null;
        GameController.actionButtonEnabled = true;
    }

    public void errorModal(String message) {
        Alert.AlertType type = Alert.AlertType.ERROR;
        Alert alert = new Alert (type, "");
        alert.initModality (Modality.APPLICATION_MODAL);
        alert.initOwner(root.getScene().getWindow());
        alert.getDialogPane().setContentText(message);
        alert.getDialogPane().setHeaderText("Not enough resources!"); // you can set header text
        alert.showAndWait();
    }

    public void errorModal(String message, String header) {
        Alert.AlertType type = Alert.AlertType.ERROR;
        Alert alert = new Alert (type, "");
        alert.initModality (Modality.APPLICATION_MODAL);
        alert.initOwner(root.getScene().getWindow());
        alert.getDialogPane().setContentText(message);
        alert.getDialogPane().setHeaderText(header); // you can set header text
        alert.showAndWait();
    }

    private double xoffSet = 0;
    private double yoffSet = 0;

    @FXML
    void mousePressed(MouseEvent event) {
        xoffSet = event.getSceneX ();
        yoffSet = event.getSceneY ();
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        HelloApplication.maritimeTradeStage2.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.maritimeTradeStage2.setX(event.getScreenX ()- xoffSet);
        HelloApplication.maritimeTradeStage2.setY (event.getScreenY ()- yoffSet);
        HelloApplication.maritimeTradeStage2.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.maritimeTradeStage2.setOpacity (1.0f);
    }

    @FXML
    void menuCloseClick(MouseEvent event) {
        Trade.resetResources();
        Trade.resetHarbor();
        maritimeTradeController.tradeType = null;
        maritimeTradeController.harbor = null;
        GameController.actionButtonEnabled = true;

        HelloApplication.toggleMaritimeTradeMenu2();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.maritimeTradeController2 = this;
    }

}
