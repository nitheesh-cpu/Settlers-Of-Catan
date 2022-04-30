package game.catan.graphics;

import game.catan.simulation.Player;
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
    public static Stage buildStage;
    public static Stage tradeStage;
    public static domesticTradeController tradeController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 690);
        this.stage = stage;
        stage.setResizable(true);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Settlers of Catan");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("buildMenu.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 500, 300);
        Stage buildStage = new Stage();
        scene2.getStylesheets().add(HelloApplication.class.getResource("gamemenu.css").toExternalForm());
        buildStage.initStyle(StageStyle.TRANSPARENT);
        this.buildStage = buildStage;
        buildStage.setResizable(true);
        scene2.setFill(Color.TRANSPARENT);
        buildStage.setTitle("Settlers of Catan");
        buildStage.setScene(scene2);

        FXMLLoader fxmlLoader3 = new FXMLLoader(HelloApplication.class.getResource("domesticTrade.fxml"));
        Scene scene3 = new Scene(fxmlLoader3.load(), 600, 400);
        Stage tradeStage = new Stage();
        scene3.getStylesheets().add(HelloApplication.class.getResource("gamemenu.css").toExternalForm());
        tradeStage.initStyle(StageStyle.TRANSPARENT);
        this.tradeStage = tradeStage;
        tradeStage.setResizable(true);
        scene3.setFill(Color.TRANSPARENT);
        tradeStage.setTitle("Trade");
        tradeStage.setScene(scene3);
    }

    public static void toggleBuildMenu(){
        if(buildStage.isShowing()){
            buildStage.hide();
        }else{
            buildStage.show();
        }

    }

    public static void showTradeMenu(Player player){
        tradeStage.show();
        tradeController.newTrade(player);
    }

    public static void showSmall() throws IOException {
        stage.hide();
        Stage game = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catanS.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catanL.fxml"));
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