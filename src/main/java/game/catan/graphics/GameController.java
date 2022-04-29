package game.catan.graphics;

import game.catan.simulation.*;
import game.catan.simulation.enums.ResourceType;

import game.catan.simulation.helper.Location;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameController {

    // region Variables
    public ImageView dice1;
    public ImageView dice2;
    public Text diceText;
    public Rectangle diceContainer;
    public ScrollPane actionLogScrollPane;
    public TextArea actionLogText;
    public Text actionLogTitle;
    public ImageView build;
    public ScrollPane cardsScrollPane;
    public Text cardsTitle;
    public Circle dice01;
    public Circle dice02;
    public Circle dice03;
    public Circle dice11;
    public Circle dice12;
    public Circle dice13;
    public Circle dice14;
    public Circle dice21;
    public Circle dice22;
    public Circle dice23;
    public Circle dice24;
    public Circle dice25;
    public Circle dice31;
    public Circle dice32;
    public Circle dice33;
    public Circle dice34;
    public Circle dice41;
    public Circle dice42;
    public Circle dice43;
    public ImageView endTurn;
    public ImageView harbor1;
    public ImageView harbor2;
    public ImageView harbor3;
    public ImageView harbor4;
    public ImageView harbor5;
    public ImageView harbor6;
    public ImageView harbor7;
    public ImageView harbor8;
    public ImageView harbor9;
    public ImageView help;
    public Pane roadPane;
    public VBox root;
    public Pane settlementPane;
    public Text statsTitle;
    public Polygon tile01;
    public Polygon tile02;
    public Polygon tile03;
    public Polygon tile11;
    public Polygon tile12;
    public Polygon tile13;
    public Polygon tile14;
    public Polygon tile21;
    public Polygon tile22;
    public Polygon tile23;
    public Polygon tile24;
    public Polygon tile25;
    public Polygon tile31;
    public Polygon tile32;
    public Polygon tile33;
    public Polygon tile34;
    public Polygon tile41;
    public Polygon tile42;
    public Polygon tile43;
    public Text turnTitle;
    public Polygon water1;
    public Polygon water10;
    public Polygon water11;
    public Polygon water12;
    public Polygon water13;
    public Polygon water14;
    public Polygon water15;
    public Polygon water16;
    public Polygon water17;
    public Polygon water18;
    public Polygon water2;
    public Polygon water3;
    public Polygon water4;
    public Polygon water5;
    public Polygon water6;
    public Polygon water7;
    public Polygon water8;
    public Polygon water9;
    public TextFlow cardsTextFlow;
    public ImageView rollDiceButton;

    public ImageView currentPlayerIcon;
    public Text currentPlayerSettlements;
    public Text currentPlayerCities;
    public Text currentPlayerRoads;
    public Text currentPlayerKnight;
    public Text currentPlayerPoints;
    public Text currentPlayerBricks;
    public Text currentPlayerWool;
    public Text currentPlayerOres;
    public Text currentPlayerWheat;
    public Text currentPlayerLumber;

    public ImageView playerIcon1;
    public Text inventoryTitle1;
    public Text settlementCount1;
    public Text cityCount1;
    public Text roadCount1;
    public Text stockpileCount1;
    public ImageView tradeButton1;
    public ImageView inventoryFrame1;

    public ImageView playerIcon2;
    public Text inventoryTitle2;
    public Text settlementCount2;
    public Text cityCount2;
    public Text roadCount2;
    public Text stockpileCount2;
    public ImageView tradeButton2;
    public ImageView inventoryFrame2;

    public ImageView playerIcon3;
    public Text inventoryTitle3;
    public Text settlementCount3;
    public Text cityCount3;
    public Text roadCount3;
    public Text stockpileCount3;
    public ImageView tradeButton3;
    public ImageView inventoryFrame3;
    // endregion

    private Polygon[] waters;
    public Polygon[][] tilePolygons;
    private Circle[][] circles;
    private Tile[][] tileObjs;
    private ImageView[] harborImages;
    private GameState gameState;
    public boolean buildEnabled;

    @FXML
    public void initialize() throws IOException, InterruptedException {
        Initialize.init(); //Initialize images

        Node[][] nodes = {{playerIcon1, inventoryTitle1, settlementCount1, cityCount1, roadCount1, stockpileCount1, inventoryFrame1, tradeButton1}, {playerIcon2, inventoryTitle2, settlementCount2, cityCount2, roadCount2, stockpileCount2, inventoryFrame2, tradeButton2}, {playerIcon3, inventoryTitle3, settlementCount3, cityCount3, roadCount3, stockpileCount3, inventoryFrame3, tradeButton3}};

        //Initialize tiles
        waters = new Polygon[]{water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12, water13, water14, water15, water16, water17, water18};
        tilePolygons = new Polygon[][]{{tile01, tile02, tile03}, {tile11, tile12, tile13, tile14}, {tile21, tile22, tile23, tile24, tile25}, {tile31, tile32, tile33, tile34}, {tile41, tile42, tile43}};
        circles = new Circle[][]{{dice01, dice02, dice03}, {dice11, dice12, dice13, dice14}, {dice21, dice22, dice23, dice24, dice25}, {dice31, dice32, dice33, dice34}, {dice41, dice42, dice43}};

        GameState gameState = new GameState(4, 123, this); // need to pass in num of players and seed here
        tileObjs = Board.getBoard();

        // Set polygons to corresponding tiles
        for (int r = 0; r < tilePolygons.length; r++) {
            for (int c = 0; c < tilePolygons[r].length; c++) {
                Tile tile = tileObjs[r][c];
                tile.setPolygon(tilePolygons[r][c]);
                tilePolygons[r][c].setFill(Initialize.tilePatterns[tile.getResource().ordinal()]);
            }
        }

        System.out.println(gameState.getBoard());

        // Set number tokens to corresponding tiles
        for (int r = 0; r < circles.length; r++)
            for (int c = 0; c < circles[r].length; c++) {
                if ((tileObjs[r][c].getNumber() > -1))
                    circles[r][c].setFill(tileObjs[r][c].getNumberPattern());
                else
                    circles[r][c].setVisible(false);
            }

        // Set water tiles
        for (Polygon tile : waters)
            tile.setFill(Initialize.waterPattern);

        //Initialize harbors
//        ResourceType[] harbors = {ResourceType.BRICK, ResourceType.WOOL, ResourceType.ORE, ResourceType.WHEAT, ResourceType.WOOD, ResourceType.MISC, ResourceType.MISC, ResourceType.MISC, ResourceType.MISC};
//        List<ResourceType> harborsList = Arrays.stream(harbors).collect(Collectors.toList());
//        Collections.shuffle(harborsList);

        ArrayList<Harbor> harbors = Board.getHarbors();
        actionLogText.appendText("Shuffled harbors: " + harbors + "\n");
        harborImages = new ImageView[]{harbor1, harbor2, harbor3, harbor4, harbor5, harbor6, harbor7, harbor8, harbor9};

        // Set harbor images to corresponding harbors
        for (int i = 0; i < harbors.size(); i++) {
            Harbor harbor = harbors.get(i);
            harborImages[i].setImage(Initialize.harborImages.get(harbor.getResourceType()));
        }

        help.setPickOnBounds(true);
        build.setPickOnBounds(true);
        endTurn.setPickOnBounds(true);

        // Initialize build button
        Image img = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("game/catan/ButtonResources/Build.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(80);
        view.setPreserveRatio(true);

        // Initialize road and settlement buttons BUGS FOUND HERE
        gameState.createRoads(tileObjs, settlementPane);
        gameState.createSettlements(tileObjs, settlementPane);
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);

        root.setBackground(new Background(background_fill));
        actionLogText.setBackground(new Background(background_fill));
        cardsTextFlow.setBackground(new Background(background_fill));

        //structures[0] == top left then go clockwise
        //roads[0] == top left then go clockwise
        if(GameState.getNumOfPlayers() == 3){
            for(Node n: nodes[2]){
                n.setVisible(false);
            }
            for(Node n: nodes[1]){
                n.setLayoutY(n.getLayoutY() + 100);
            }
            for(Node n: nodes[0]){
                n.setLayoutY(n.getLayoutY() + 100);
            }
        }

        gameState.start();

        rollDiceButton.setVisible(false);
        dice1.setVisible(false);
        dice2.setVisible(false);
        diceText.setVisible(false);
        diceContainer.setVisible(false);
        buildEnabled = true;
    }

    private double xoffSet = 0;
    private double yoffSet = 0;

    public void resetDice(){
        rollDiceButton.setVisible(true);
        dice1.setImage(null);
        dice2.setImage(null);
    }

    public void updateDiceGraphic(int roll1, int roll2) {
        rollDiceButton.setVisible(false);
        dice1.setImage(Initialize.diceImages[roll1-1]);
        dice2.setImage(Initialize.diceImages[roll2-1]);
    }

    public void showDice(){
        rollDiceButton.setVisible(true);
        diceContainer.setVisible(true);
        dice1.setVisible(true);
        dice2.setVisible(true);
        diceText.setVisible(true);
    }

    public void updatePlayerStats(){
        Text[][] stats = {{inventoryTitle1,settlementCount1,cityCount1,roadCount1,stockpileCount1},{inventoryTitle2,settlementCount2,cityCount2,roadCount2,stockpileCount2},{inventoryTitle3,settlementCount3,cityCount3,roadCount3,stockpileCount3} };
        ImageView[] icons = {playerIcon1,playerIcon2,playerIcon3};

        currentPlayerBricks.setText(Integer.toString(GameState.getCurrentPlayer().getStockpile().getResourceCount(ResourceType.BRICK)));
        currentPlayerWool.setText(Integer.toString(GameState.getCurrentPlayer().getStockpile().getResourceCount(ResourceType.WOOL)));
        currentPlayerWheat.setText(Integer.toString(GameState.getCurrentPlayer().getStockpile().getResourceCount(ResourceType.WHEAT)));
        currentPlayerLumber.setText(Integer.toString(GameState.getCurrentPlayer().getStockpile().getResourceCount(ResourceType.WOOD)));
        currentPlayerOres.setText(Integer.toString(GameState.getCurrentPlayer().getStockpile().getResourceCount(ResourceType.ORE)));
        currentPlayerIcon.setImage(GameState.getCurrentPlayer().getImages().get("Icon"));
        currentPlayerKnight.setText(Integer.toString(GameState.getCurrentPlayer().getNumOfKnights()));
        currentPlayerPoints.setText(Integer.toString(GameState.getCurrentPlayer().getVictoryPoints()));
        currentPlayerSettlements.setText(Integer.toString(GameState.getCurrentPlayer().getSettlementCount()));
        currentPlayerRoads.setText(Integer.toString(GameState.getCurrentPlayer().getRoads().size()));
        currentPlayerCities.setText(Integer.toString(GameState.getCurrentPlayer().getCityCount()));

        turnTitle.setText("Player " + (GameState.getCurrentPlayer().getId()) + "'s Turn");

        int index = 0;
        for (Player player: GameState.getPlayers()) {
            if (player.equals(GameState.getCurrentPlayer())) continue;

            stats[index][0].setText("Player " + (player.getId()));
            stats[index][1].setText(Integer.toString(player.getSettlementCount()));
            stats[index][2].setText(Integer.toString(player.getCityCount()));
            stats[index][3].setText(Integer.toString(player.getRoads().size()));
            stats[index][4].setText(Integer.toString(player.getStockpile().getTotal()));
            icons[index].setImage(player.getImages().get("Icon"));
            index++;
        }
//
//        for(int i = 0; i < GameState.getPlayers().length; i++){
//            if (!GameState.getPlayers()[i].equals(GameState.getCurrentPlayer())) continue;
//
//
//            // int index = GameState.nextTurnIndex(GameState.getCurrentPlayerIndex()+1);
//            stats[i][0].setText("Player " + (i+1));
//            stats[i][1].setText(Integer.toString(GameState.players[i].getSettlementCount()));
//            stats[i][2].setText(Integer.toString(GameState.players[i].getCityCount()));
//            stats[i][3].setText(Integer.toString(GameState.players[i].getRoads().size()));
//            stats[i][4].setText(Integer.toString(GameState.players[i].getStockpile().getTotal()));
//            icons[i].setImage(GameState.players[i].getImages().get("Icon"));
//        }

    }


    //turn buttons
    @FXML
    void buildClicked(MouseEvent event) {
        if(buildEnabled) HelloApplication.toggleBuildMenu();
    }

    @FXML
    void endTurnClicked(MouseEvent event) {
        actionLogText.appendText("End turn clicked\n");
    }

    @FXML
    void helpClicked(MouseEvent event) {
        actionLogText.appendText("Help clicked\n");
    }


    //top menu related stuffs
    @FXML //for top menu x button
    void closeClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML //for top menu minimize button
    void minimizeClicked(MouseEvent event) {
        HelloApplication.gameStage.setIconified(true);
    }

    //top menu window movement
    @FXML
    void mousePressed(MouseEvent event) {
        xoffSet = event.getSceneX ();
        yoffSet = event.getSceneY ();
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        HelloApplication.gameStage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.gameStage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.gameStage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.gameStage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.gameStage.setOpacity (1.0f);
    }

    @FXML
    void rollClicked(MouseEvent event) {
        System.out.println("roll clicked");
        GameState.rollDice();
    }

    private int domesticTradePlayer = 0;
    public void trade1Clicked(MouseEvent mouseEvent) {
        HelloApplication.showTradeMenu(gameState.getCurrentPlayer());
        domesticTradePlayer = 1;
        domesticTradeController.followup = true;
    }

    public void trade2Clicked(MouseEvent mouseEvent) {
        HelloApplication.showTradeMenu(gameState.getCurrentPlayer());
        domesticTradePlayer = 2;
        domesticTradeController.followup = true;
    }

    public void trade3Clicked(MouseEvent mouseEvent) {
        HelloApplication.showTradeMenu(gameState.getCurrentPlayer());
        domesticTradePlayer = 3;
        domesticTradeController.followup = true;
    }

    public void domesticTradeFollowup(){
        int index = GameState.nextTurnIndex(GameState.getCurrentPlayerIndex() +domesticTradePlayer-1);
        HelloApplication.showTradeMenu(GameState.players[index]);
        domesticTradePlayer = 0;
    }
}