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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catan2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 992);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        this.stage = stage;
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Settlers of Catan");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}