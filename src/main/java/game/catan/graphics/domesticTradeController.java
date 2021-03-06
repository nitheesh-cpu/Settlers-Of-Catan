package game.catan.graphics;

import game.catan.simulation.GameState;
import game.catan.simulation.Player;
import game.catan.simulation.Stockpile;
import game.catan.simulation.Trade;
import game.catan.simulation.enums.ResourceType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

public class domesticTradeController implements Initializable {

    @FXML
    private Text amtBricks;

    @FXML
    private Text amtLumber;

    @FXML
    private Text amtOres;

    @FXML
    private Text amtWheat;

    @FXML
    private Text amtWool;

    @FXML
    private ImageView cancel;

    @FXML
    private ImageView confirm;

    @FXML
    private ImageView lessBricks;

    @FXML
    private ImageView lessLumber;

    @FXML
    private ImageView lessOre;

    @FXML
    private ImageView lessWheat;

    @FXML
    private ImageView lessWool;

    @FXML
    private ImageView moreBricks;

    @FXML
    private ImageView moreLumber;

    @FXML
    private ImageView moreOre;

    @FXML
    private ImageView moreWheat;

    @FXML
    private ImageView moreWool;

    @FXML
    private Pane root;

    @FXML
    private Text titleText;

    public ImageView brick;
    public ImageView lumber;
    public ImageView ore;
    public ImageView wheat;
    public ImageView wool;


    private int brickAmt = 0;
    private int lumberAmt = 0;
    private int oreAmt = 0;
    private int wheatAmt = 0;
    private int woolAmt = 0;
    public Player player;

    public void dark(){
        brick.setImage(new Image(domesticTradeController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/bricks.png")));
        lumber.setImage(new Image(domesticTradeController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/lumber.png")));
        ore.setImage(new Image(domesticTradeController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/ore.png")));
        wheat.setImage(new Image(domesticTradeController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/wheat.png")));
        wool.setImage(new Image(domesticTradeController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/wool.png")));
        confirm.setImage(new Image(domesticTradeController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/confirm.png")));
        cancel.setImage(new Image(domesticTradeController.class.getClassLoader().getResourceAsStream("game/catan/DarkResources/DomesticTrade/cancel.png")));
    }

    public void increaseLumber(MouseEvent event) {
        if(lumberAmt < player.getResource(ResourceType.WOOD)) {
            lumberAmt++;
            amtLumber.setText(Integer.toString(lumberAmt));
        }
    }

    public void decreaseLumber(MouseEvent event) {
        if(lumberAmt > 0) {
        	lumberAmt--;
        	amtLumber.setText(Integer.toString(lumberAmt));
        }
    }

    public void increaseOre(MouseEvent event) {
        if(oreAmt < player.getResource(ResourceType.ORE)) {
            oreAmt++;
            amtOres.setText(Integer.toString(oreAmt));
        }
    }

    public void decreaseOre(MouseEvent event) {
        if(oreAmt > 0) {
        	oreAmt--;
        	amtOres.setText(Integer.toString(oreAmt));
        }
    }

    public void increaseWheat(MouseEvent event) {
        if (wheatAmt < player.getResource(ResourceType.WHEAT)) {
            wheatAmt++;
            amtWheat.setText(Integer.toString(wheatAmt));
        }
    }

    public void decreaseWheat(MouseEvent event) {
        if(wheatAmt > 0) {
        	wheatAmt--;
        	amtWheat.setText(Integer.toString(wheatAmt));
        }
    }

    public void increaseWool(MouseEvent event) {
        if (woolAmt < player.getResource(ResourceType.WOOL)) {
            woolAmt++;
            amtWool.setText(Integer.toString(woolAmt));
        }
    }

    public void decreaseWool(MouseEvent event) {
        if(woolAmt > 0) {
        	woolAmt--;
        	amtWool.setText(Integer.toString(woolAmt));
        }
    }

    public void increaseBricks(MouseEvent event) {
        if(brickAmt < player.getResource(ResourceType.BRICK)) {
            brickAmt++;
            amtBricks.setText(Integer.toString(brickAmt));
        }
    }

    public void decreaseBricks(MouseEvent event) {
        if(brickAmt > 0) {
        	brickAmt--;
        	amtBricks.setText(Integer.toString(brickAmt));
        }
    }

    public static boolean followup = false;
    public void confirmTrade(MouseEvent event) {
        //TODO: implement trade followup
        if(!followup) {
            Stockpile tradeOne = Trade.getTradeOne();
            Stockpile tradeTwo = new Stockpile(brickAmt, wheatAmt, lumberAmt, oreAmt, woolAmt);

            if (tradeTwo.getTotal() == 0) {
                errorModal("You must trade at least one resource.", "Invalid Trade");
                return;
            }

            if (!verifyTrade(tradeOne, tradeTwo)) {
                errorModal("Players cannot trade the same resource!", "Invalid Trade");
                return;
            }

            Trade.setTradeTwo(tradeTwo);
            Trade.setPlayerTwo(player);

            // show confirmation screen
            HelloApplication.domesticTradeStage.hide();
            HelloApplication.showDomesticConfirmTradeMenu();
        } else {
            Stockpile tradeOne = new Stockpile(brickAmt, wheatAmt, lumberAmt, oreAmt, woolAmt);

            if (tradeOne.getTotal() == 0) {
                errorModal("You must trade at least one resource.", "Invalid Trade");
                return;
            }

            HelloApplication.domesticTradeStage.hide();

            Trade.setTradeOne(tradeOne);
            Trade.setPlayerOne(player);

            GameState.gameController.domesticTradeFollowup();
            domesticTradeController.followup = false;
        }

        brickAmt = 0;
        lumberAmt = 0;
        oreAmt = 0;
        woolAmt = 0;
        wheatAmt = 0;
    }

    // cannot trade same resource
    private boolean verifyTrade(Stockpile tradeOne, Stockpile tradeTwo) {
        for (ResourceType r : ResourceType.values()) {
            if (tradeOne.getResourceCount(r) > 0 && tradeTwo.getResourceCount(r) > 0) {
                return false;
            }
        }

        return true;
    }

    public void cancelTrade(MouseEvent event) {
        HelloApplication.domesticTradeStage.hide();
        Trade.resetTrade();
        followup = false;

        GameState.getGameController().enableButtons();
    }

    private double xoffSet;
    private double yoffSet;

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

    public void updateText(){
        amtBricks.setText("0");
        amtLumber.setText("0");
        amtOres.setText("0");
        amtWool.setText("0");
        amtWheat.setText("0");
        brickAmt = 0;
        lumberAmt = 0;
        oreAmt = 0;
        woolAmt = 0;
        wheatAmt = 0;

        titleText.setText("Player "+ player.getId() +", enter # of resources you're trading");
    }

    public void newTrade(Player player) {
        this.player = player;
        updateText();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.domesticTradeController = this;
    }
}
