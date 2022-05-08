package game.catan.graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.controlsfx.control.ToggleSwitch;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button four;

    @FXML
    private Button helpButton;

    @FXML
    private Button large;

    @FXML
    private Button playButton;

    @FXML
    private Button small;

    @FXML
    private Button three;

    @FXML
    private Text text1;
    public ToggleSwitch darkMode;

    public static int size = -1;
    public static int players = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text1.setFill(javafx.scene.paint.Color.WHITE);
        small.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        large.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        three.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        four.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
    }

    @FXML
    void helpPressed(ActionEvent event) {
        HelloApplication.toggleHelpMenu();
    }


    @FXML
    void playPressed(ActionEvent event) throws IOException {
        if(size == -1 || players == -1) {
            Stage stage = (Stage) playButton.getScene().getWindow();
            Alert.AlertType type = Alert.AlertType.ERROR;
            Alert alert = new Alert (type, "");
            alert.initModality (Modality.APPLICATION_MODAL);
            alert.initOwner (stage);
            alert.getDialogPane().setContentText("Please go back and select a size and number of players");
            alert.getDialogPane().setHeaderText("Missing parameters!"); // you can set header text
            alert.showAndWait();
        } else {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Enter game seed");
            dialog.setHeaderText("Enter a game seed (leave empty for random seed)");
            dialog.setContentText("Seed:");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(playButton.getScene().getWindow());
            Optional<String> result = dialog.showAndWait();

            if (!result.isPresent()) {
                return;
            }

            String seedTemp = result.get().trim();
            int seed;
            try {

                if (!seedTemp.equals("")) {
                    seed = Integer.parseInt(seedTemp);
                } else {
                    seed = new Random().nextInt();
                }

            } catch (NumberFormatException e) {
                Stage stage = (Stage) playButton.getScene().getWindow();
                Alert.AlertType type = Alert.AlertType.ERROR;
                Alert alert = new Alert (type, "");
                alert.initModality (Modality.APPLICATION_MODAL);
                alert.initOwner (stage);
                alert.getDialogPane().setContentText("User entered invalid seed. Must be an integer.");
                alert.getDialogPane().setHeaderText("Invalid seed!"); // you can set header text
                alert.showAndWait();
                return;
            }

            System.out.println("Seed: " + seed);
            GameController.seed = seed;

            if(size == 0) {
                if(darkMode.isSelected()) HelloApplication.showSmallDark();
                else HelloApplication.showSmallDark();
            }else if(size == 1) {
                if (darkMode.isSelected()) HelloApplication.showLargeDark();
                else HelloApplication.showLarge();
            }
        }
    }

    @FXML
    void smallPressed(ActionEvent event) {
        size = 0;
        small.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        large.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
    }

    @FXML
    void largePressed(ActionEvent event) {
        size = 1;
        large.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        small.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
    }

    @FXML
    void threePressed(ActionEvent event) {
        players = 3;
        four.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        three.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
    }

    @FXML
    void fourPressed(ActionEvent event) {
        players = 4;
        four.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        three.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
    }
}
