package game.catan.graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.net.URL;
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

    public void threePressed(ActionEvent actionEvent) {
        System.out.println("3");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text1.setFill(javafx.scene.paint.Color.WHITE);
    }
}
