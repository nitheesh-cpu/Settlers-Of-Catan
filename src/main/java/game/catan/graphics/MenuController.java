package game.catan.graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.controlsfx.control.ToggleSwitch;
import javax.swing.*;
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
    public Rectangle block;
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
                else HelloApplication.showSmall();
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
        if (darkMode.isSelected()){
            large.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        }
        else large.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        if(darkMode.isSelected())large.setTextFill(Color.WHITE);
        else large.setTextFill(Color.BLACK);
        small.setTextFill(Color.BLACK);
    }

    @FXML
    void largePressed(ActionEvent event) {
        size = 1;
        large.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        if (darkMode.isSelected()) small.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        else small.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        large.setTextFill(Color.BLACK);
        if(darkMode.isSelected())small.setTextFill(Color.WHITE);
        else small.setTextFill(Color.BLACK);
    }

    @FXML
    void threePressed(ActionEvent event) {
        players = 3;
        if (darkMode.isSelected()) four.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        else four.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        three.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        if(darkMode.isSelected()) four.setTextFill(Color.WHITE);
        else four.setTextFill(Color.BLACK);
        three.setTextFill(Color.BLACK);
    }

    @FXML
    void fourPressed(ActionEvent event) {
        players = 4;
        four.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        if (darkMode.isSelected()) three.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        else three.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        four.setTextFill(Color.BLACK);
        if(darkMode.isSelected()) three.setTextFill(Color.WHITE);
        else three.setTextFill(Color.BLACK);
    }

    @FXML
    void toggleChanged(MouseEvent event) {
        if(darkMode.isSelected()) {
            block.setFill(Color.web("#434C5E"));
            playButton.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 15;-fx-cursor: hand;");
            playButton.setTextFill(Color.web("#FFFFFF"));
            helpButton.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 15;-fx-cursor: hand;");
            helpButton.setTextFill(Color.web("#FFFFFF"));
            four.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            three.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            four.setTextFill(Color.web("#FFFFFF"));
            three.setTextFill(Color.web("#FFFFFF"));
            small.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            large.setStyle("-fx-background-color: #434C5E;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            small.setTextFill(Color.web("#FFFFFF"));
            large.setTextFill(Color.web("#FFFFFF"));
            if(size == 0){
                small.setTextFill(Color.web("#000000"));
                small.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            }
            else if(size == 1){
                large.setTextFill(Color.web("#000000"));
                large.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            }
            if(players == 3){
                three.setTextFill(Color.web("#000000"));
                three.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            }
            else if(players == 4){
                four.setTextFill(Color.web("#000000"));
                four.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            }
        }
        else {
            block.setFill(Color.web("#FFFFFF"));
            playButton.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 15;-fx-cursor: hand;");
            playButton.setTextFill(Color.web("#000000"));
            helpButton.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 15;-fx-cursor: hand;");
            helpButton.setTextFill(Color.web("#000000"));
            four.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            three.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            four.setTextFill(Color.web("#000000"));
            three.setTextFill(Color.web("#000000"));
            small.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            large.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            small.setTextFill(Color.web("#000000"));
            large.setTextFill(Color.web("#000000"));
            if(size == 0) small.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            else if(size == 1) large.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            if(players == 3) three.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
            else if(players == 4) four.setStyle("-fx-background-color: #00ff00;-fx-background-radius: 25;-fx-border-style: solid;-fx-border-width: 1;-fx-border-radius: 25;-fx-border-insets: -1;");
        }
    }
}
