package game.catan.graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    public ImageView knight;
    public ImageView monopoly;
    public ImageView roadBuilding;
    public ImageView yearOfPlenty;
    public ImageView largestArmy;
    public ImageView longestRoad;
    public ImageView chapel;
    public ImageView greatHall;
    public ImageView library;
    public ImageView market;
    public ImageView university;
    public Button download;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tooltip.install(knight, new Tooltip("Knight: Move the robber. Steal one resource from the owner of a settlement or city adjacent to the robber’s new hex"));
        Tooltip.install(monopoly, new Tooltip("Monopoly: When you play this card, announce one type of resource. \nAll other players must give you all of their resource of that type"));
        Tooltip.install(roadBuilding, new Tooltip("Road Building: Place two roads as if you had just built them"));
        Tooltip.install(yearOfPlenty, new Tooltip("Year of Plenty: When you play this card, you can immediately select any 2 resource cards from the bank."));
        Tooltip.install(largestArmy, new Tooltip("Largest Army: The first player to play 3 Knight Cards gets this card. \nAnother player who plays more Knight Cards takes this card. \n2 Victory Points are added to the player who takes this card."));
        Tooltip.install(longestRoad, new Tooltip("Longest Road: This card goes to the player with the longest road of at least 5 segments. \nAnother player who builds a longer road takes this card. \n2 Victory Points are added to the player who takes this card."));
        Tooltip.install(chapel, new Tooltip("Chapel: Reveal this card on your turn if, with it, \nyou reach the number of points required for victory"));
        Tooltip.install(greatHall, new Tooltip("Great Hall: Reveal this card on your turn if, with it, \nyou reach the number of points required for victory"));
        Tooltip.install(library, new Tooltip("Library: Reveal this card on your turn if, with it, \nyou reach the number of points required for victory"));
        Tooltip.install(market, new Tooltip("Market: Reveal this card on your turn if, with it, \nyou reach the number of points required for victory"));
        Tooltip.install(university, new Tooltip("University: Reveal this card on your turn if, with it, \nyou reach the number of points required for victory"));
        Tooltip.install(download, new Tooltip("Click this button to download a set of the official rules"));
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

    @FXML
    void download(ActionEvent event) throws URISyntaxException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("instructions.pdf");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        String fileLocationPath = fileChooser.showSaveDialog(HelloApplication.helpStage).getAbsolutePath();
        File fileLocation = new File(HelpController.class.getClassLoader().getResource("game/catan/HelpMenu/instructions.pdf").toURI());
        File fileLocation1 = new File(fileLocationPath);
        FileUtils.copyFile(fileLocation, fileLocation1);
    }
}
