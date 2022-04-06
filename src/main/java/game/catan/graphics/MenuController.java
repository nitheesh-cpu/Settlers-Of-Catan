package game.catan.graphics;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MenuController {

    @FXML
    private StackPane pane;

    @FXML
    public void initialize() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("game/catan/background/menu_bg.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);


        pane.setBackground(new Background(backgroundImage));
    }
}
