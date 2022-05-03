package game.catan.graphics;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HelpController {



    private double xoffSet = 0;
    private double yoffSet = 0;

    @FXML
    void mousePressed(MouseEvent event) {
        xoffSet = event.getSceneX ();
        yoffSet = event.getSceneY ();
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        HelloApplication.helpStage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.helpStage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.helpStage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.helpStage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.helpStage.setOpacity (1.0f);
    }

    @FXML //for top menu x button
    void closeClicked(MouseEvent event) {
        System.out.println("close clicked");
        HelloApplication.helpStage.hide();
    }

    @FXML //for top menu minimize button
    void minimizeClicked(MouseEvent event) {
        HelloApplication.helpStage.setIconified(true);
    }
}
