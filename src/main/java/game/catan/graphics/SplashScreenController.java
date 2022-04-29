package game.catan.graphics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Application started");
        FadeTransition.applyFadeTransition(parent, Duration.seconds(3), (e) ->{
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("menu.fxml"));
                parent.getChildren().removeAll();
                parent.getChildren().setAll(fxml);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
