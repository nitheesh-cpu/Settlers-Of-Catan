package game.catan.graphics;

import game.catan.simulation.Harbor;
import game.catan.simulation.Player;
import game.catan.simulation.Trade;
import game.catan.simulation.enums.TradeType;
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
    public static Stage domesticTradeStage;
    public static Stage domesticTradeConfirmStage;

    public static Stage maritimeTradeStage;
    public static Stage maritimeTradeStage2;

    public static domesticTradeController domesticTradeController;
    public static maritimeTradeController maritimeTradeController;
    public static maritimeTradeController2 maritimeTradeController2;
    public static domesticTradeConfirmController domesticTradeConfirmController;

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
        tradeStage.setResizable(true);
        scene3.setFill(Color.TRANSPARENT);
        tradeStage.setAlwaysOnTop(true);
        tradeStage.setTitle("Domestic Trade");
        tradeStage.setScene(scene3);
        tradeStage.initStyle(StageStyle.TRANSPARENT);
        domesticTradeStage = tradeStage;

        FXMLLoader fxmlLoader4 = new FXMLLoader(HelloApplication.class.getResource("maritimeTradeMenu.fxml"));
        Scene scene4 = new Scene(fxmlLoader4.load(), 635, 340);
        Stage maritimeStage = new Stage();
        scene4.getStylesheets().add(HelloApplication.class.getResource("gamemenu.css").toExternalForm());
        maritimeStage.initStyle(StageStyle.TRANSPARENT);
        maritimeStage.setResizable(true);
        scene4.setFill(Color.TRANSPARENT);
        maritimeStage.setAlwaysOnTop(true);
        maritimeStage.setTitle("Maritime Trade");
        maritimeStage.setScene(scene4);
        maritimeTradeStage = maritimeStage;

        FXMLLoader fxmlLoader5 = new FXMLLoader(HelloApplication.class.getResource("maritimeTradeMenu2.fxml"));
        Scene scene5 = new Scene(fxmlLoader5.load(), 635, 340);
        Stage maritimeStage2 = new Stage();
        scene5.getStylesheets().add(HelloApplication.class.getResource("gamemenu.css").toExternalForm());
        maritimeStage2.initStyle(StageStyle.TRANSPARENT);
        maritimeStage2.setResizable(true);
        scene4.setFill(Color.TRANSPARENT);
        maritimeStage2.setAlwaysOnTop(true);
        maritimeStage2.setTitle("Maritime Trade");
        maritimeStage2.setScene(scene5);
        maritimeTradeStage2 = maritimeStage2;

        FXMLLoader fxmlLoader6 = new FXMLLoader(HelloApplication.class.getResource("confirmTrade.fxml"));
        Scene scene6 = new Scene(fxmlLoader6.load(), 624, 465);
        Stage confirmStage = new Stage();
        scene6.getStylesheets().add(HelloApplication.class.getResource("gamemenu.css").toExternalForm());
        confirmStage.initStyle(StageStyle.TRANSPARENT);
        confirmStage.setResizable(true);
        scene6.setFill(Color.TRANSPARENT);
        confirmStage.setAlwaysOnTop(true);
        confirmStage.setTitle("Confirm Trade");
        confirmStage.setScene(scene6);
        domesticTradeConfirmStage = confirmStage;
    }

    public static void toggleBuildMenu(){
        if(buildStage.isShowing()){
            buildStage.hide();
        }else{
            buildStage.show();
        }
    }

    // domestic trade
    public static void showDomesticTradeMenu(Player player){
        domesticTradeStage.show();
        domesticTradeController.newTrade(player);
    }

    // only for stockpile trade
    public static void toggleMaritimeTradeMenu(){
        if (maritimeTradeStage.isShowing()) {
            maritimeTradeStage.hide();
            maritimeTradeController.setTradeType(null);
            GameController.actionButtonEnabled = true;

            Trade.resetResources();
        } else {
            maritimeTradeStage.show();
            maritimeTradeController.setTradeType(TradeType.STOCKPILE);
        }
    }

    public static void toggleMaritimeTradeMenu(Harbor harbor) {
        if (maritimeTradeStage.isShowing()) {
            maritimeTradeStage.hide();
            maritimeTradeController.setTradeType(null);
            Trade.resetTrade();

        } else {
            maritimeTradeStage.show();
            maritimeTradeController.setTradeType(TradeType.HARBOR);
            maritimeTradeController.setHarbor(harbor);
        }
    }

    public static void toggleMaritimeTradeMenu2() {
        if (maritimeTradeStage2.isShowing()) {
            maritimeTradeStage2.hide();
        } else {
            maritimeTradeStage2.show();
        }
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