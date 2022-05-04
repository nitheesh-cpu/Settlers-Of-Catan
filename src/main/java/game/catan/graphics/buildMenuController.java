package game.catan.graphics;

import game.catan.simulation.Board;
import game.catan.simulation.GameState;
import game.catan.simulation.Player;
import game.catan.simulation.Stockpile;
import game.catan.simulation.enums.ResourceType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

public class buildMenuController implements Initializable {

    public ImageView cityButton;
    public ImageView devCardButton;
    public ImageView roadButton;
    public Pane root;
    public Text title;
    public ImageView settlementButton;

    public void cityClicked(MouseEvent mouseEvent) {
        System.out.println("City clicked");
    }

    public void devCardClicked(MouseEvent mouseEvent) {
        System.out.println("Dev card clicked");
    }

    public void roadClicked(MouseEvent mouseEvent) {
        System.out.println("Road clicked");
    }

    public void settlementClicked(MouseEvent mouseEvent) {
        System.out.println("Settlement clicked");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.buildMenuController = this;
    }


    // 1 brick and wood
    // player can only have up to 5 roads
    public void buyRoad() {
        if (GameState.getCurrentPlayer().getRoads().size() == 15) {
            errorModal("You cannot have more than 15 roads!", "Too many roads");
            return;
        }

        if (GameState.getBoard().availableRoadPlacements().size() == 0) {
            errorModal("There are no available road placements!", "No available roads");
            return;
        }

        Stockpile stockpile = GameState.getCurrentPlayer().getStockpile();
        if (stockpile.getResourceCount(ResourceType.BRICK) >= 1 && stockpile.getResourceCount(ResourceType.WOOD) >= 1) {
            GameState.log(GameState.getCurrentPlayer() + " bought a road for 1x BRICK and 1x WOOD.");

            GameController.getGameState().buyRoad();
            HelloApplication.buildStage.hide();
            // GameState.getGameController().enableButtons();
        } else {
            errorModal("You do not have enough resources to build a road!\n(must have 1x BRICK and 1x WOOD)", "Not enough resources");
        }
    }

    // 1 brick, wood, wool, and grain
    // player can only have up to 5 settlements
    public void buySettlement() {
        if (GameState.getCurrentPlayer().getSettlementCount() == 5) {
            errorModal("You cannot have more than 5 settlements!", "Too many settlements");
            return;
        }

        if (GameState.getBoard().availableSettlementPlacements(false).size() == 0) {
            errorModal("There are no available settlement placements!", "No available settlements");
            return;
        }

        Stockpile stockpile = GameState.getCurrentPlayer().getStockpile();
        if (stockpile.getResourceCount(ResourceType.BRICK) >= 1
                && stockpile.getResourceCount(ResourceType.WOOD) >= 1
                && stockpile.getResourceCount(ResourceType.WOOL) >= 1
                && stockpile.getResourceCount(ResourceType.WHEAT) >= 1) {
            GameState.log(GameState.getCurrentPlayer() + " bought a settlement for 1x BRICK, 1x WOOD, 1x WOOL, and 1x WHEAT.");

            GameController.getGameState().buySettlement();
            HelloApplication.buildStage.hide();
            // GameState.getGameController().enableButtons();
        } else {
            errorModal("You do not have enough resources to build a settlement\n(must have 1x BRICK, 1x WOOD, 1x WOOL, and 1x WHEAT)", "Not enough resources");
        }
    }

    // 3 ore and 2 wheat need to implement
    // player can only have up to 4 cities
    public void buyCity() {
        if (GameState.getCurrentPlayer().getCityCount() == 4) {
            errorModal("You cannot have more than 4 cities!", "Too many cities");
            return;
        }

        Stockpile stockpile = GameState.getCurrentPlayer().getStockpile();
        if (GameState.getCurrentPlayer().getSettlementCount() == 0) {
            errorModal("No settlements to upgrade!", "No settlements");
            return;
        }

        if (stockpile.getResourceCount(ResourceType.ORE) >= 3 && stockpile.getResourceCount(ResourceType.WHEAT) >= 2) {
            GameState.log(GameState.getCurrentPlayer() + " bought a city for 3x ORE and 2x WHEAT.");

            GameController.getGameState().buyCity();
            HelloApplication.buildStage.hide();
            // GameState.getGameController().enableButtons();
        } else {
            errorModal("You do not have enough resources to build a city!\n(must have 3x ORE and 2x WHEAT)", "Not enough resources");
        }
    }

    // 1 ore, wool, and wheat
    public void buyDevelopmentCard() {
        if (Board.getDevelopmentCardCount() == 0) {
            errorModal("There are no development cards left!", "No development cards");
            return;
        }

        Stockpile stockpile = GameState.getCurrentPlayer().getStockpile();

        if (stockpile.getResourceCount(ResourceType.ORE) >= 1
                && stockpile.getResourceCount(ResourceType.WOOL) >= 1
                && stockpile.getResourceCount(ResourceType.WHEAT) >= 1) {
            GameState.log(GameState.getCurrentPlayer() + " bought a development card for 1x ORE, 1x WOOL, and 1x WHEAT.");

            GameController.getGameState().buyDevelopmentCard();
            HelloApplication.buildStage.hide();
            GameState.getGameController().enableButtons();
        } else {
            errorModal("You do not have enough resources to draw a development card! (must have 1x ORE, 1x WOOL, and 1x WHEAT)", "Not enough resources");
        }
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
        HelloApplication.buildStage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.buildStage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.buildStage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.buildStage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.buildStage.setOpacity (1.0f);
    }

    @FXML
    void menuCloseClick(MouseEvent event) {
        HelloApplication.toggleBuildMenu();
    }
}
