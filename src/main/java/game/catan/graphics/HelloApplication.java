package game.catan.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    public static Stage gameStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 690);

        this.stage = stage;
        stage.setResizable(true);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Settlers of Catan");
        stage.setScene(scene);
        stage.show();
    }

    public static void showSmall() throws IOException {
        stage.hide();
        Stage game = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catan3.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 758);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("gamemenu.css").toExternalForm());
        game.setResizable(false);
        scene.setFill(Color.TRANSPARENT);
        game.setTitle("Settlers of Catan");
        game.setScene(scene);
        game.show();
        gameStage = game;
    }

    public static void showLarge() throws IOException {
        stage.hide();
        Stage game = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catan2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 962);
        game.initStyle(StageStyle.TRANSPARENT);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("gamemenu.css").toExternalForm());
        game.setResizable(false);
        scene.setFill(Color.TRANSPARENT);
        game.setTitle("Settlers of Catan");
        game.setScene(scene);
        game.show();
        gameStage = game;
    }

    public static void main(String[] args) {
        launch();
    }
}