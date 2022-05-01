package game.catan.graphics;

import game.catan.simulation.GameState;
import game.catan.simulation.Player;
import game.catan.simulation.enums.ResourceType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

    private int brickAmt = 0;
    private int lumberAmt = 0;
    private int oreAmt = 0;
    private int wheatAmt = 0;
    private int woolAmt = 0;
    public Player player;

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
        HelloApplication.domesticTradeStage.hide();
        //TODO: implement trade followup
        if(!followup) {

        }
        if(followup) {
            GameState.gameController.domesticTradeFollowup();
            domesticTradeController.followup = false;
        }
    }

    public void cancelTrade(MouseEvent event) {
        HelloApplication.domesticTradeStage.hide();
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
        int index = 0;
        for(int i = 0; i < GameState.players.length; i++) {
            if (GameState.players[i] == player) index = i;
        }
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
        int i = index+1;
        titleText.setText("Player "+i+", enter # of resources you're trading");
    }

    public void newTrade(Player player) {
        this.player = player;
        updateText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.domesticTradeController = this;
    }
}
