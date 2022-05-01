package game.catan.graphics;

import game.catan.simulation.Player;
import game.catan.simulation.Trade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class domesticTradeConfirmController implements Initializable {

    @FXML
    private Text playerOneTitle;
    @FXML
    private Text playerTwoTitle;

    @FXML
    private Text playerOneBrick;
    @FXML
    private Text playerOneOre;
    @FXML
    private Text playerOneWool;
    @FXML
    private Text playerOneWood;
    @FXML
    private Text playerOneWheat;

    @FXML
    private Text playerTwoBrick;
    @FXML
    private Text playerTwoOre;
    @FXML
    private Text playerTwoWool;
    @FXML
    private Text playerTwoWood;
    @FXML
    private Text playerTwoWheat;

    private double xoffSet;
    private double yoffSet;

    public void update() {
        playerOneTitle.setText("Player " + Trade.getPlayerOne().getId() + "'s Trade");
        playerTwoTitle.setText("Player " + Trade.getPlayerTwo().getId() + "'s Trade");

        playerOneBrick.setText(Trade.getTradeOne().getBricks() + "");
        playerOneOre.setText(Trade.getTradeOne().getOre() + "");
        playerOneWool.setText(Trade.getTradeOne().getWool() + "");
        playerOneWood.setText(Trade.getTradeOne().getWood() + "");
        playerOneWheat.setText(Trade.getTradeOne().getWheat() + "");

        playerTwoBrick.setText(Trade.getTradeTwo().getBricks() + "");
        playerTwoOre.setText(Trade.getTradeTwo().getOre() + "");
        playerTwoWool.setText(Trade.getTradeTwo().getWool() + "");
        playerTwoWood.setText(Trade.getTradeTwo().getWood() + "");
        playerTwoWheat.setText(Trade.getTradeTwo().getWheat() + "");
    }

    @FXML
    void mousePressed(MouseEvent event) {
        xoffSet = event.getSceneX ();
        yoffSet = event.getSceneY ();
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        HelloApplication.domesticTradeStage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.domesticTradeStage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.domesticTradeStage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.domesticTradeStage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.domesticTradeStage.setOpacity (1.0f);
    }

    public void confirmTrade(MouseEvent event) {
        HelloApplication.domesticTradeConfirmStage.hide();
        Trade.domesticTrade(Trade.getPlayerOne(), Trade.getTradeOne(), Trade.getPlayerTwo(), Trade.getTradeTwo());
    }

    public void cancelTrade(MouseEvent event) {
        Trade.resetTrade();
        HelloApplication.domesticTradeConfirmStage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.domesticTradeConfirmController = this;
    }
}
