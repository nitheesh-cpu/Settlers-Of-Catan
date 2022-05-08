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
        FadeTransition.applyFadeTransition(parent, Duration.seconds(7), (e) ->{
            try {
                Parent fxml = FXMLLoader.load(SplashScreenController.class.getClassLoader().getResource("game/catan/graphics/menu.fxml"));
                fxml.getStylesheets().add(SplashScreenController.class.getClassLoader().getResource("game/catan/graphics/gamemenu.css").toExternalForm());
                parent.getChildren().removeAll();
                parent.getChildren().setAll(fxml);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
