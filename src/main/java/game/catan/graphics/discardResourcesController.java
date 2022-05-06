package game.catan.graphics;

import game.catan.simulation.GameState;
import game.catan.simulation.Player;
import game.catan.simulation.Stockpile;
import game.catan.simulation.Trade;
import game.catan.simulation.enums.ResourceType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class discardResourcesController implements Initializable {


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

    private int brickAmt = 0;
    private int lumberAmt = 0;
    private int oreAmt = 0;
    private int wheatAmt = 0;
    private int woolAmt = 0;


    private TreeMap<Player, Integer> playersToDiscard = new TreeMap<>();
    private HashSet<Player> usedPlayers = new HashSet<>();
    private Player player = null;
    private int amountToDiscard = 0;

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

    public void confirmTrade(MouseEvent event) {
        int total = brickAmt + lumberAmt + oreAmt + wheatAmt + woolAmt;

        if (total < amountToDiscard) {
            errorModal("Still need to discard " + (amountToDiscard - total) + " resources!", "More Resources to Discard");
            return;
        } else if (total > amountToDiscard) {
            errorModal("Only need to discard " + (amountToDiscard) + " resources!", "Discarding Too Many Resources");
            return;
        }

        Trade.discardResource(player, ResourceType.BRICK, brickAmt);
        Trade.discardResource(player, ResourceType.WOOD, lumberAmt);
        Trade.discardResource(player, ResourceType.ORE, oreAmt);
        Trade.discardResource(player, ResourceType.WHEAT, wheatAmt);
        Trade.discardResource(player, ResourceType.WOOL, woolAmt);

        GameState.updatePlayerStats();

        String message = "";

        if (brickAmt > 0) {
            message += brickAmt + "x BRICK, ";
        }

        if (lumberAmt > 0) {
            message += lumberAmt + "x WOOD, ";
        }

        if (oreAmt > 0) {
            message += oreAmt + "x ORE, ";
        }

        if (wheatAmt > 0) {
            message += wheatAmt + "x WHEAT, ";
        }

        if (woolAmt > 0) {
            message += woolAmt + "x WOOL, ";
        }

        if (message.length() > 0) {
            message = message.substring(0, message.length() - 2);
        }

        GameState.log(player + " discarded " + message + ".");

        usedPlayers.add(player);

        if (usedPlayers.size() == playersToDiscard.size()) {
            HelloApplication.hideDiscardResourcesMenu();

            brickAmt = 0;
            lumberAmt = 0;
            oreAmt = 0;
            woolAmt = 0;
            wheatAmt = 0;

            GameState.getGameController().showAvailableRobberTiles();
            return;
        }

        player = playersToDiscard.higherKey(player);
        amountToDiscard = playersToDiscard.get(player);

        updateText();
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
        HelloApplication.discardResourcesStage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.discardResourcesStage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.discardResourcesStage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.discardResourcesStage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.discardResourcesStage.setOpacity (1.0f);
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

        titleText.setText("Player "+ player.getId() +",\ndiscard " + amountToDiscard + " resources");
        GameState.log(player + ", discard " + amountToDiscard + " resources.");
    }

    public void setup(TreeMap<Player, Integer> playersToDiscard) {
        this.playersToDiscard = playersToDiscard;
        player = playersToDiscard.firstKey();
        amountToDiscard = playersToDiscard.get(player);

        updateText();
    }

    public void reset() {
        playersToDiscard.clear();
        usedPlayers.clear();

        this.player = null;
        this.amountToDiscard = 0;
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
        HelloApplication.discardResourcesController = this;
    }
}
