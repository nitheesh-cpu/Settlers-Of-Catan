package game.catan.graphics;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
