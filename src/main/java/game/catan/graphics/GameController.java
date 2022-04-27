package game.catan.graphics;

import game.catan.simulation.GameState;
import game.catan.simulation.Tile;
import game.catan.simulation.enums.ResourceType;
import game.catan.simulation.Initialize;

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

        Node[][] nodes = {{playerIcon1,inventoryTitle1,settlementCount1,cityCount1,roadCount1,stockpileCount1,inventoryFrame1,tradeButton1},{playerIcon2,inventoryTitle2,settlementCount2,cityCount2,roadCount2,stockpileCount2,inventoryFrame2,tradeButton2},{playerIcon3,inventoryTitle3,settlementCount3,cityCount3,roadCount3,stockpileCount3,inventoryFrame3,tradeButton3}};

        //Initialize tiles
        waters = new Polygon[]{water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12, water13, water14, water15, water16, water17, water18};
        tilePolygons = new Polygon[][]{{tile01, tile02, tile03}, {tile11, tile12, tile13, tile14}, {tile21, tile22, tile23, tile24, tile25}, {tile31, tile32, tile33, tile34}, {tile41, tile42, tile43}};
        circles = new Circle[][]{{dice01, dice02, dice03}, {dice11, dice12, dice13, dice14}, {dice21, dice22, dice23, dice24, dice25}, {dice31, dice32, dice33, dice34}, {dice41, dice42, dice43}};

        int[] tileRandomizer = {0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5};
        ResourceType[] resourceNames = {ResourceType.BRICK, ResourceType.ORE, ResourceType.WHEAT, ResourceType.WOOL, ResourceType.WOOD, ResourceType.DESERT};
        List<Integer> tilesList = Arrays.stream(tileRandomizer).boxed().collect(Collectors.toList());
        Collections.shuffle(tilesList);

        tileObjs = new Tile[][]{{null, null, null}, {null, null, null, null}, {null, null, null, null, null}, {null, null, null, null}, {null, null, null}};
        ArrayList<Tile> tiles = new ArrayList<>();
        gameState = new GameState(this);
        for (int r = 0; r < tilePolygons.length; r++)
            for (int c = 0; c < tilePolygons[r].length; c++) {
                tileObjs[r][c] = (new Tile(resourceNames[tilesList.get(0)], new Location(r, c)));
                tileObjs[r][c].setPolygon(tilePolygons[r][c]);
                tilePolygons[r][c].setFill(tilePatterns[tilesList.remove(0)]);
            }


        gameState.setTiles(tileObjs);
        gameState.initializeTileNumbers();
        gameState.getBoard().print();

        for (int r = 0; r < circles.length; r++)
            for (int c = 0; c < circles[r].length; c++) {
                if ((tileObjs[r][c].getTileNumber() > -1))
                    circles[r][c].setFill(tileObjs[r][c].getNumberPattern());
                else
                    circles[r][c].setVisible(false);
            }
        for (Polygon tile : waters)
            tile.setFill(waterPattern);

        //Initialize harbors
        ResourceType[] harbors = {ResourceType.BRICK, ResourceType.WOOL, ResourceType.ORE, ResourceType.WHEAT, ResourceType.WOOD, ResourceType.MISC, ResourceType.MISC, ResourceType.MISC, ResourceType.MISC};
        List<ResourceType> harborsList = Arrays.stream(harbors).collect(Collectors.toList());
        Collections.shuffle(harborsList);
        actionLogText.appendText("Shuffled harbors: " + harborsList + "\n");
        harborImages = new ImageView[]{harbor1, harbor2, harbor3, harbor4, harbor5, harbor6, harbor7, harbor8, harbor9};
        for (ImageView harbor : harborImages) {
            harbor.setImage(Initialize.harborImages.get(harborsList.remove(0)));
        }
        help.setPickOnBounds(true);
        build.setPickOnBounds(true);
        endTurn.setPickOnBounds(true);


        Image img = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("game/catan/ButtonResources/Build.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(80);
        view.setPreserveRatio(true);

        gameState.createRoads(tileObjs, settlementPane);
        gameState.createSettlements(tileObjs, settlementPane);
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);

        root.setBackground(new Background(background_fill));
        actionLogText.setBackground(new Background(background_fill));
        cardsTextFlow.setBackground(new Background(background_fill));

        //structures[0] == top left then go clockwise
        //roads[0] == top left then go clockwise
        if(GameState.numPlayers == 3){
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

    public void initGame() {
        Node[][] nodes = {{playerIcon1,inventoryTitle1,settlementCount1,cityCount1,roadCount1,stockpileCount1,inventoryFrame1,tradeButton1},{playerIcon2,inventoryTitle2,settlementCount2,cityCount2,roadCount2,stockpileCount2,inventoryFrame2,tradeButton2},{playerIcon3,inventoryTitle3,settlementCount3,cityCount3,roadCount3,stockpileCount3,inventoryFrame3,tradeButton3}};

        //Initialize tiles in fxml
        waters = new Polygon[]{water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12, water13, water14, water15, water16, water17, water18};
        tilePolygons = new Polygon[][]{{tile01, tile02, tile03}, {tile11, tile12, tile13, tile14}, {tile21, tile22, tile23, tile24, tile25}, {tile31, tile32, tile33, tile34}, {tile41, tile42, tile43}};
        circles = new Circle[][]{{dice01, dice02, dice03}, {dice11, dice12, dice13, dice14}, {dice21, dice22, dice23, dice24, dice25}, {dice31, dice32, dice33, dice34}, {dice41, dice42, dice43}};

        Board board = new Board();

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
        dice1.setImage(diceImages[roll1-1]);
        dice2.setImage(diceImages[roll2-1]);
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
        currentPlayerBricks.setText(Integer.toString(gameState.getCurrentPlayer().getResource(ResourceType.BRICK)));
        currentPlayerWool.setText(Integer.toString(gameState.getCurrentPlayer().getResource(ResourceType.WOOL)));
        currentPlayerWheat.setText(Integer.toString(gameState.getCurrentPlayer().getResource(ResourceType.WHEAT)));
        currentPlayerLumber.setText(Integer.toString(gameState.getCurrentPlayer().getResource(ResourceType.WOOD)));
        currentPlayerOres.setText(Integer.toString(gameState.getCurrentPlayer().getResource(ResourceType.ORE)));
        currentPlayerIcon.setImage(gameState.getCurrentPlayer().getImages().get("Icon"));
        currentPlayerKnight.setText(Integer.toString(gameState.getCurrentPlayer().getNumKnights()));
        currentPlayerPoints.setText(Integer.toString(gameState.getCurrentPlayer().getVictoryPoints()));
        currentPlayerSettlements.setText(Integer.toString(gameState.getCurrentPlayer().getAmtSettlements()));
        currentPlayerRoads.setText(Integer.toString(gameState.getCurrentPlayer().getAmtRoads()));
        currentPlayerCities.setText(Integer.toString(gameState.getCurrentPlayer().getAmtCities()));
        turnTitle.setText("Player " + (GameState.currentPlayerIndex+1) + "'s Turn");
        for(int i = 0; i < GameState.numPlayers-1; i++){
            int index = GameState.nextTurnIndex(GameState.currentPlayerIndex+i);
            stats[i][0].setText("Player " + (index+1));
            stats[i][1].setText(Integer.toString(GameState.players[index].getAmtSettlements()));
            stats[i][2].setText(Integer.toString(GameState.players[index].getAmtCities()));
            stats[i][3].setText(Integer.toString(GameState.players[index].getAmtRoads()));
            stats[i][4].setText(Integer.toString(GameState.players[index].totalResources()));
            icons[i].setImage(GameState.players[index].getImages().get("Icon"));
        }

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

}