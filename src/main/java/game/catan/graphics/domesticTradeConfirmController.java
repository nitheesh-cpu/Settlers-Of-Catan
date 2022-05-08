package game.catan.graphics;

import game.catan.simulation.GameState;
import game.catan.simulation.Player;
import game.catan.simulation.Trade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public ImageView arrow;
    public ImageView bricks;
    public ImageView ore;
    public ImageView wool;
    public ImageView lumber;
    public ImageView wheat;
    public ImageView bricks2;
    public ImageView ore2;
    public ImageView wool2;
    public ImageView lumber2;
    public ImageView wheat2;
    public ImageView confirm;
    public ImageView cancel;

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

    public void dark(){
        arrow.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/arrow.png")));
        bricks.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/bricks.png")));
        ore.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/ore.png")));
        wool.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/wool.png")));
        lumber.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/lumber.png")));
        wheat.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/wheat.png")));
        bricks2.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/bricks.png")));
        ore2.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/ore.png")));
        wool2.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/wool.png")));
        lumber2.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/lumber.png")));
        wheat2.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/wheat.png")));
        confirm.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/confirm.png")));
        cancel.setImage(new Image(domesticTradeConfirmController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/cancel.png")));
    }

    @FXML
    void mousePressed(MouseEvent event) {
        xoffSet = event.getSceneX ();
        yoffSet = event.getSceneY ();
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        HelloApplication.domesticTradeConfirmStage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.domesticTradeConfirmStage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.domesticTradeConfirmStage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.domesticTradeConfirmStage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.domesticTradeConfirmStage.setOpacity (1.0f);
    }

    public void confirmTrade(MouseEvent event) {
        HelloApplication.domesticTradeConfirmStage.hide();
        Trade.domesticTrade(Trade.getPlayerOne(), Trade.getTradeOne(), Trade.getPlayerTwo(), Trade.getTradeTwo());
        Trade.resetTrade();

        GameState.getGameController().enableButtons();
    }

    public void cancelTrade(MouseEvent event) {
        Trade.resetTrade();
        HelloApplication.domesticTradeConfirmStage.hide();

        GameState.getGameController().enableButtons();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.domesticTradeConfirmController = this;
    }
}
