package game.catan.graphics;

import game.catan.simulation.engine.GameState;
import game.catan.simulation.engine.Initialize;
import game.catan.simulation.engine.Location;
import game.catan.simulation.structures.ResourceType;
import game.catan.simulation.structures.Tile;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static game.catan.simulation.engine.Initialize.tilePatterns;
import static game.catan.simulation.engine.Initialize.waterPattern;

public class GameController {
    @FXML
    private ScrollPane actionLogScrollPane;

    @FXML
    private TextArea actionLogText;

    @FXML
    private Text actionLogTitle;

    @FXML
    private ImageView build;

    @FXML
    private ScrollPane cardsScrollPane;

    @FXML
    private Text cardsTitle;

    @FXML
    private Circle dice01;

    @FXML
    private Circle dice02;

    @FXML
    private Circle dice03;

    @FXML
    private Circle dice11;

    @FXML
    private Circle dice12;

    @FXML
    private Circle dice13;

    @FXML
    private Circle dice14;

    @FXML
    private Circle dice21;

    @FXML
    private Circle dice22;

    @FXML
    private Circle dice23;

    @FXML
    private Circle dice24;

    @FXML
    private Circle dice25;

    @FXML
    private Circle dice31;

    @FXML
    private Circle dice32;

    @FXML
    private Circle dice33;

    @FXML
    private Circle dice34;

    @FXML
    private Circle dice41;

    @FXML
    private Circle dice42;

    @FXML
    private Circle dice43;

    @FXML
    private ImageView endTurn;

    @FXML
    private ImageView harbor1;

    @FXML
    private ImageView harbor2;

    @FXML
    private ImageView harbor3;

    @FXML
    private ImageView harbor4;

    @FXML
    private ImageView harbor5;

    @FXML
    private ImageView harbor6;

    @FXML
    private ImageView harbor7;

    @FXML
    private ImageView harbor8;

    @FXML
    private ImageView harbor9;

    @FXML
    private ImageView help;

    @FXML
    private Text inventoryTitle1;

    @FXML
    private Text inventoryTitle2;

    @FXML
    private Text inventoryTitle3;

    @FXML
    private Pane roadPane;

    @FXML
    private VBox root;

    @FXML
    private Pane settlementPane;

    @FXML
    private Text statsTitle;

    @FXML
    private Polygon tile01;

    @FXML
    private Polygon tile02;

    @FXML
    private Polygon tile03;

    @FXML
    private Polygon tile11;

    @FXML
    private Polygon tile12;

    @FXML
    private Polygon tile13;

    @FXML
    private Polygon tile14;

    @FXML
    private Polygon tile21;

    @FXML
    private Polygon tile22;

    @FXML
    private Polygon tile23;

    @FXML
    private Polygon tile24;

    @FXML
    private Polygon tile25;

    @FXML
    private Polygon tile31;

    @FXML
    private Polygon tile32;

    @FXML
    private Polygon tile33;

    @FXML
    private Polygon tile34;

    @FXML
    private Polygon tile41;

    @FXML
    private Polygon tile42;

    @FXML
    private Polygon tile43;

    @FXML
    private Text turnTitle;

    @FXML
    private Polygon water1;

    @FXML
    private Polygon water10;

    @FXML
    private Polygon water11;

    @FXML
    private Polygon water12;

    @FXML
    private Polygon water13;

    @FXML
    private Polygon water14;

    @FXML
    private Polygon water15;

    @FXML
    private Polygon water16;

    @FXML
    private Polygon water17;

    @FXML
    private Polygon water18;

    @FXML
    private Polygon water2;

    @FXML
    private Polygon water3;

    @FXML
    private Polygon water4;

    @FXML
    private Polygon water5;

    @FXML
    private Polygon water6;

    @FXML
    private Polygon water7;

    @FXML
    private Polygon water8;

    @FXML
    private Polygon water9;

    @FXML
    private TextFlow cardsTextFlow;

    private Polygon[] waters;
    public Polygon[][] tilePolygons;
    private Circle[][] circles;
    private Tile[][] tileObjs;
    private ImageView[] harborImages;

    @FXML
    public void initialize() throws IOException {
        Initialize.init(); //Initialize images

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
        GameState gameState = new GameState();
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

        gameState.createRoads(tileObjs, roadPane);
        gameState.createSettlements(tileObjs, settlementPane);
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);

        root.setBackground(new Background(background_fill));
        actionLogText.setBackground(new Background(background_fill));
        cardsTextFlow.setBackground(new Background(background_fill));
        //structures[0] == top left then go clockwise
        //roads[0] == top left then go clockwise

        gameState.start();
    }

    private double xoffSet = 0;
    private double yoffSet = 0;





    //turn buttons
    @FXML
    void buildClicked(MouseEvent event) {
        actionLogText.appendText("Build clicked\n");
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
        HelloApplication.stage.setIconified(true);
    }

    //top menu window movement
    @FXML
    void mousePressed(MouseEvent event) {
        xoffSet = event.getSceneX ();
        yoffSet = event.getSceneY ();
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        HelloApplication.stage.setOpacity (1.0f);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        HelloApplication.stage.setX(event.getScreenX ()- xoffSet);
        HelloApplication.stage.setY (event.getScreenY ()- yoffSet);
        HelloApplication.stage.setOpacity (0.8f);
    }

    @FXML
    void onDragDone(MouseEvent event) {
        HelloApplication.stage.setOpacity (1.0f);
    }

}