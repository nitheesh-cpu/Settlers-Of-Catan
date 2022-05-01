package game.catan.graphics;

import game.catan.simulation.GameState;
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

public class maritimeTradeController implements Initializable {

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
        if (!Trade.verifyAmountOfResources(GameState.getCurrentPlayer().getStockpile(), resourceType, 4)) {
            errorModal("You don't have enough " + resourceType.toString().toLowerCase() + " to trade!");
            return;
        }

        Trade.setTradingResource(resourceType);

        GameController.actionButtonEnabled = false;
        HelloApplication.toggleMaritimeTradeMenu();
        HelloApplication.toggleMaritimeTradeMenu2();
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

    private double xoffSet = 0;
    private double yoffSet = 0;

    @FXML
    void mousePressed(MouseEvent event) {
        xoffSet = event.getSceneX ();
        yoffSet = event.getSceneY ();
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        HelloApplication.maritimeTradeStage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.maritimeTradeStage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.maritimeTradeStage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.maritimeTradeStage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.maritimeTradeStage.setOpacity (1.0f);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.maritimeTradeController = this;
    }
}
