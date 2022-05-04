package game.catan.graphics;

import game.catan.simulation.*;
import game.catan.simulation.enums.Phase;
import game.catan.simulation.enums.ResourceType;

import game.catan.simulation.helper.Location;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameController {

    // region Variables
    public ImageView dice1;
    public ImageView dice2;
    public Text diceText;
    public Rectangle diceContainer;
    public ScrollPane actionLogScrollPane;
    public TextArea actionLogText;
    public Text actionLogTitle;
    public ImageView actionButton; // build button

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

    @FXML
    private Pane tileListenerPane;

    public ImageView endButton;
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
    public Pane harborPane;
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
    public Text victoryPointCount1;
    public ImageView playerButton1;
    public ImageView inventoryFrame1;

    public ImageView playerIcon2;
    public Text inventoryTitle2;
    public Text settlementCount2;
    public Text cityCount2;
    public Text roadCount2;
    public Text stockpileCount2;
    public Text victoryPointCount2;
    public ImageView playerButton2;
    public ImageView inventoryFrame2;

    public ImageView playerIcon3;
    public Text inventoryTitle3;
    public Text settlementCount3;
    public Text cityCount3;
    public Text roadCount3;
    public Text stockpileCount3;
    public Text victoryPointCount3;
    public ImageView playerButton3;
    public ImageView inventoryFrame3;

    public ImageView tradePort1;
    public ImageView tradePort2;
    public ImageView tradePort3;
    public ImageView tradePort4;
    public ImageView tradePort5;
    public ImageView tradePort6;
    public ImageView tradePort7;
    public ImageView tradePort8;
    public ImageView tradePort9;

    public ImageView[] tradePortImages;
    public Pane cardsPane;
  
    // endregion

    private Polygon[] waters;
    public static Polygon[][] tilePolygons;
    private Circle[][] circles;
    private ImageView[][] tileClickListeners;
    private Tile[][] tileObjs;
    private ImageView[] harborImages;
    private static GameState gameState;
    public static boolean actionButtonEnabled;
    public ImageView robberImage;

    @FXML
    public void initialize() throws IOException, InterruptedException {
        Initialize.init(); //Initialize images

        Node[][] nodes = {{playerIcon1, inventoryTitle1, settlementCount1, cityCount1, roadCount1, stockpileCount1, inventoryFrame1, playerButton1, victoryPointCount1}, {playerIcon2, inventoryTitle2, settlementCount2, cityCount2, roadCount2, stockpileCount2, inventoryFrame2, playerButton2, victoryPointCount2}, {playerIcon3, inventoryTitle3, settlementCount3, cityCount3, roadCount3, stockpileCount3, inventoryFrame3, victoryPointCount3}};

        //Initialize tiles
        waters = new Polygon[]{water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12, water13, water14, water15, water16, water17, water18};
        tilePolygons = new Polygon[][]{{tile01, tile02, tile03}, {tile11, tile12, tile13, tile14}, {tile21, tile22, tile23, tile24, tile25}, {tile31, tile32, tile33, tile34}, {tile41, tile42, tile43}};
        circles = new Circle[][]{{dice01, dice02, dice03}, {dice11, dice12, dice13, dice14}, {dice21, dice22, dice23, dice24, dice25}, {dice31, dice32, dice33, dice34}, {dice41, dice42, dice43}};
        // tileClickListeners = new ImageView[][]{{tileClick1, tileClick2, tileClick3}, {tileClick4, tileClick5, tileClick6, tileClick7}, {tileClick8, tileClick9, tileClick10, tileClick11, tileClick12}, {tileClick13, tileClick14, tileClick15, tileClick16}, {tileClick17, tileClick18, tileClick19}};
        tileClickListeners = new ImageView[][]{{null, null, null}, {null, null, null, null}, {null, null, null, null, null}, {null, null, null, null}, {null, null, null}};

        gameState = new GameState(MenuController.players, 123, this); // need to pass in num of players and seed here
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

        // Set robber image
        robberImage = new ImageView();
        robberImage.setImage(Initialize.robber);
        settlementPane.getChildren().add(robberImage);

        // Set number tokens to corresponding tiles
        for (int r = 0; r < circles.length; r++)
            for (int c = 0; c < circles[r].length; c++) {
                if ((tileObjs[r][c].getNumber() > -1)) {
                    circles[r][c].setFill(tileObjs[r][c].getNumberPattern());
                }
                else {
                    circles[r][c].setOpacity(0.0);
                    updateRobberLocation(Board.getRobberLocation(), null);
                }
            }

        // Set water tiles
        for (Polygon tile : waters) {
            tile.setFill(Initialize.waterPattern);
        }

        ArrayList<Harbor> harbors = Board.getHarbors();

        harborImages = new ImageView[]{harbor1, harbor2, harbor3, harbor4, harbor5, harbor6, harbor7, harbor8, harbor9};
        tradePortImages = new ImageView[]{tradePort1, tradePort2, tradePort3, tradePort4, tradePort5, tradePort6, tradePort7, tradePort8, tradePort9};

        // Set harbor images to corresponding harbors
        for (int i = 0; i < harbors.size(); i++) {
            Harbor harbor = harbors.get(i);

            harborImages[i].setImage(Initialize.harborImages.get(harbor.getResourceType()));

            tradePortImages[i].setImage(Initialize.tradeHands);
            tradePortImages[i].setVisible(false);
        }

        help.setPickOnBounds(true);
        actionButton.setPickOnBounds(true);
        endButton.setPickOnBounds(true);

        // Initialize build button
        Image img = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("game/catan/ButtonResources/Build.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(80);
        view.setPreserveRatio(true);

        // Initialize road and settlement buttons
        gameState.createRoads(tileObjs, settlementPane);
        gameState.createSettlements(tileObjs, settlementPane);
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);

        root.setBackground(new Background(background_fill));
        actionLogText.setBackground(new Background(background_fill));
        cardsPane.setBackground(new Background(background_fill));

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
        updatePlayerStats();
        updatePlayerCards();
        disableTrade();
        showDice();
        actionButtonEnabled = false;

        createTileClickListeners();
    }

    public void log(String message){
        actionLogText.appendText("> " + message + "\n");
    }

    private double xoffSet = 0;
    private double yoffSet = 0;

    public void resetDice(){
        rollDiceButton.setVisible(true);
        dice1.setImage(null);
        dice2.setImage(null);
    }

    public void updateDiceGraphic(int roll1, int roll2) {
        rollDiceButton.getStyleClass().remove("hover");
        rollDiceButton.setVisible(false);

        dice1.setImage(Initialize.diceImages[roll1-1]);
        dice2.setImage(Initialize.diceImages[roll2-1]);
    }

    public void showDice(){
        rollDiceButton.getStyleClass().add("hover");
        rollDiceButton.setVisible(true);
        diceContainer.setVisible(true);
        dice1.setVisible(true);
        dice2.setVisible(true);
        diceText.setVisible(true);
    }

    private void showHarbors() {
        ArrayList<Harbor> availableHarbors = GameState.getCurrentPlayer().getHarbors();
        ArrayList<Harbor> harbors = Board.getHarbors();

        for(int i = 0; i < harbors.size(); i++) {
            if (availableHarbors.contains(harbors.get(i))) {
                ImageView tradePort = tradePortImages[i];

                tradePort.setVisible(true);

                tradePort.getStyleClass().add("hover");
                tradePort.setOpacity(0.0);

                int finalI = i;

                tradePortImages[i].setOnMouseEntered(e -> {
                    tradePortImages[finalI].setOpacity(1.0);
                    harborImages[finalI].setVisible(false);
                });

                tradePortImages[i].setOnMouseExited(e -> {
                    tradePortImages[finalI].setOpacity(0.0);
                    harborImages[finalI].setVisible(true);
                });

                tradePortImages[i].setOnMouseClicked(e -> {
                   HelloApplication.toggleMaritimeTradeMenu(harbors.get(finalI));
                });
            }
        }
    }

    public void showAvailableRobberTiles() {
        log("Move robber to a new tile.");

        disableButtons();

        for (int r = 0; r < tileClickListeners.length; r++) {
            for (int c = 0; c < tileClickListeners[r].length; c++) {
                int finalR = r;
                int finalC = c;

                if (Board.getTile(r, c).equals(Board.getTile(Board.getRobberLocation()))) continue;

                tileClickListeners[r][c].setVisible(true);

                tileClickListeners[r][c].setOnMouseClicked(e -> {
                    gameState.moveRobber(new Location(finalR, finalC));
                    disableAvailableRobberTile();
                    showSteal();
                });

                tileClickListeners[r][c].setOnMouseEntered(e -> {
                   circles[finalR][finalC].setVisible(false);
                   tileClickListeners[finalR][finalC].setOpacity(1.0);
                });

                tileClickListeners[r][c].setOnMouseExited(e -> {
                   circles[finalR][finalC].setVisible(true);
                   tileClickListeners[finalR][finalC].setOpacity(0.0);
                });

                tileClickListeners[r][c].getStyleClass().add("hover");
            }
        }
    }

    public void disableAvailableRobberTile() {
        // loop through tileClickListeners
        for (int r = 0; r < tileClickListeners.length; r++) {
            for (int c = 0; c < tileClickListeners[r].length; c++) {
                tileClickListeners[r][c].setVisible(false);
                tileClickListeners[r][c].setOpacity(0.0);
                tileClickListeners[r][c].getStyleClass().remove("hover");
            }
        }
    }

    public void createTileClickListeners() {
        for (int r = 0; r < tileClickListeners.length; r++) {
            for (int c = 0; c < tileClickListeners[r].length; c++) {
                tileClickListeners[r][c] = new ImageView();
                Circle circle = circles[r][c];
                tileClickListeners[r][c].setLayoutX(circle.getLayoutX() - (robberImage.getImage().getWidth() / 2));
                tileClickListeners[r][c].setLayoutY(circle.getLayoutY() - (robberImage.getImage().getHeight() / 2));

                tileClickListeners[r][c].setImage(Initialize.robber);
                tileClickListeners[r][c].setOpacity(0.0);
                tileClickListeners[r][c].setVisible(false);

                tileListenerPane.getChildren().add(tileClickListeners[r][c]);
            }
        }
    }

    private void disableHarbors() {
        for (int i = 0; i < Board.getHarbors().size(); i++) {
            tradePortImages[i].setOpacity(0.0);
            harborImages[i].getStyleClass().remove("hover");
            tradePortImages[i].setVisible(false);
        }
    }

    public void disableTrade() {
        playerButton1.getStyleClass().remove("hover");
        playerButton2.getStyleClass().remove("hover");
        playerButton3.getStyleClass().remove("hover");

        playerButton1.setVisible(false);
        playerButton2.setVisible(false);
        playerButton3.setVisible(false);

        disableHarbors();
    }

    public void disableSteal(){
        GameState.isStealing = false;

        if (GameState.getPhase() == Phase.TRADE) {
            showTrade();
        } else {
            enableButtons();

            playerButton1.getStyleClass().remove("hover");
            playerButton2.getStyleClass().remove("hover");
            playerButton3.getStyleClass().remove("hover");

            playerButton1.setVisible(false);
            playerButton2.setVisible(false);
            playerButton3.setVisible(false);
        }
    }

    public void disableButtons() {
        disableTrade();
        disablePlayerCards();

        rollDiceButton.setVisible(false);
        endButton.setDisable(true);
        actionButtonEnabled = false;
    }

    public void enableButtons() {
        if (GameState.getPhase() == Phase.TRADE) {
            showTrade();
        } else if (GameState.getPhase() == Phase.RESOURCE_PRODUCTION) {
            rollDiceButton.setVisible(true);
        }

        enablePlayerCards();
        endButton.setDisable(false);
        actionButtonEnabled = true;
    }

    public void showTrade() {
        playerButton1.getStyleClass().add("hover");
        playerButton2.getStyleClass().add("hover");
        if (GameState.getNumOfPlayers() == 4) playerButton3.getStyleClass().add("hover");

        playerButton1.setImage(Initialize.tradeButton);
        playerButton2.setImage(Initialize.tradeButton);

        playerButton1.setVisible(true);
        playerButton2.setVisible(true);

        if (GameState.getNumOfPlayers() == 4) {
            playerButton3.setImage(Initialize.tradeButton);
            playerButton3.setVisible(true);
        }

        showHarbors();
    }

    public void showSteal() {
        GameState.isStealing = true;

        ArrayList<Player> availablePlayersToStealFrom = GameState.getBoard().getAvailablePlayersToStealFrom();
        ArrayList<Integer> indices = new ArrayList<>();

        if (availablePlayersToStealFrom.size() == 0) {
            log("No players to steal from.");
            disableSteal();
            return;
        }

        for (int i = 1; i <= 3; i++) {
            Player correspondingPlayer = getCorrespondingPlayer(i);

            if (availablePlayersToStealFrom.contains(correspondingPlayer)) {
                indices.add(i);
            }
        }

        for (Integer index: indices) {
            switch (index) {
                case 1 -> {
                    playerButton1.getStyleClass().add("hover");
                    playerButton1.setImage(Initialize.stealButton);
                    playerButton1.setVisible(true);
                }
                case 2 -> {
                    playerButton2.getStyleClass().add("hover");
                    playerButton2.setImage(Initialize.stealButton);
                    playerButton2.setVisible(true);
                }
                case 3 -> {
                    playerButton3.getStyleClass().add("hover");
                    playerButton3.setImage(Initialize.stealButton);
                    playerButton3.setVisible(true);
                }
            }
        }
    }

    public void showDiscard(TreeMap<Player, Integer> playersToDiscard) {
        HelloApplication.showDiscardResourcesMenu(playersToDiscard);
    }

    public void updateButtons() {
        switch (GameState.getPhase()) {
            case TRADE -> {
                actionButton.setImage(Initialize.tradeIcon);
                endButton.setImage(Initialize.endTradeButton);
            }
            case BUY -> {
                actionButton.setImage(Initialize.buildButton);
                endButton.setImage(Initialize.endTurnButton);
            }
        }
    }

    public void updatePlayerStats(){
        Text[][] stats = {{inventoryTitle1,settlementCount1,cityCount1,roadCount1,stockpileCount1, victoryPointCount1},{inventoryTitle2,settlementCount2,cityCount2,roadCount2,stockpileCount2, victoryPointCount2},{inventoryTitle3,settlementCount3,cityCount3,roadCount3,stockpileCount3, victoryPointCount3} };
        ImageView[] icons = {playerIcon1,playerIcon2,playerIcon3};
        statsTitle.setText("Player Stats");

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
            stats[index][5].setText(Integer.toString(player.getVictoryPoints()));

            icons[index].setImage(player.getImages().get("Icon"));
            index++;
        }
    }

    public void updatePlayerCards(){
        cardsPane.getChildren().clear();

        AtomicInteger count = new AtomicInteger();

        if (GameState.getCurrentPlayer().equals(Board.getLargestArmyHolder())) {
            ImageView imageView = new ImageView(new Image(GameController.class.getResourceAsStream("game/catan/Cards/SpecialCards/LARGEST_ARMY.png")));

            if(HelloApplication.isSmall){
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(250);
                imageView.setX(2+(150*count.get()));
                imageView.setY(2);
                cardsPane.getChildren().add(imageView);
                count.getAndIncrement();
                System.out.println("Small");
            }
            else{
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(260);
                imageView.setX(2+(150*count.get()));
                imageView.setY(3);
                cardsPane.getChildren().add(imageView);
                count.getAndIncrement();
                System.out.println("Big");
            }
        }

        if (GameState.getCurrentPlayer().equals(Board.getLongestRoadHolder())) {
            ImageView imageView = new ImageView(new Image(GameController.class.getResourceAsStream("game/catan/Cards/SpecialCards/LONGEST_ROAD.png")));

            if(HelloApplication.isSmall){
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(250);
                imageView.setX(2+(150*count.get()));
                imageView.setY(2);
                cardsPane.getChildren().add(imageView);
                count.getAndIncrement();
                System.out.println("Small");
            }
            else{
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(260);
                imageView.setX(2+(150*count.get()));
                imageView.setY(3);
                cardsPane.getChildren().add(imageView);
                count.getAndIncrement();
                System.out.println("Big");
            }
        }

        GameState.getCurrentPlayer().getDevelopmentCards().forEach(card -> {
            ImageView imageView = new ImageView();

            switch (card.getType()) {
                case KNIGHT -> {
                    imageView.setImage(new Image(GameController.class.getResourceAsStream("game/catan/Cards/KnightCard/KNIGHT.png")));
                    imageView.getStyleClass().add("hover");

                    imageView.setOnMouseClicked(event -> {
                        log(GameState.getCurrentPlayer() + " played a Knight card.");

                        GameState.getCurrentPlayer().addKnight();
                        GameState.getCurrentPlayer().getDevelopmentCards().remove(card);
                        updatePlayerCards();
                        showAvailableRobberTiles();
                    });
                }
                case PROGRESS -> {
                    switch (card.getName()) {
                        case "Monopoly" -> {
                            imageView.setImage(new Image(GameController.class.getResourceAsStream("game/catan/Cards/ProgressCard/Monopoly.png")));
                            imageView.setOnMouseClicked(event -> {
                                gameState.useMonopolyCard();

                                GameState.getCurrentPlayer().getDevelopmentCards().remove(card);
                                updatePlayerCards();
                            });
                        }
                        case "Road Building" -> {
                            imageView.setImage(new Image(GameController.class.getResourceAsStream("game/catan/Cards/ProgressCard/RoadBuilding.png")));
                            imageView.setOnMouseClicked(event -> {
                                gameState.useRoadBuildingCard();

                                GameState.getCurrentPlayer().removeDevelopmentCard(card);
                                updatePlayerCards();
                                disableButtons();
                            });
                        }
                        case "Year of Plenty" -> {
                            imageView.setImage(new Image(GameController.class.getResourceAsStream("game/catan/Cards/ProgressCard/YearOfPlenty.png")));
                            imageView.setOnMouseClicked(event -> {
                                log("Played a Year of Plenty Card");
                                gameState.useYearOfPlentyCard();

                                GameState.getCurrentPlayer().removeDevelopmentCard(card);
                                updatePlayerCards();
                            });
                        }
                    }

                    imageView.getStyleClass().add("hover");
                }
                case VICTORY_POINT -> {
                    String name = card.getName().toUpperCase().replaceAll(" ", "_");
                    imageView.setImage(new Image(GameController.class.getResourceAsStream("game/catan/Cards/VictoryPointCard/" + name + ".png")));
                }
            }

            if(HelloApplication.isSmall){
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(250);
                imageView.setX(2+(150*count.get()));
                imageView.setY(2);
                cardsPane.getChildren().add(imageView);
                count.getAndIncrement();
                System.out.println("Small");
            }
            else{
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(260);
                imageView.setX(2+(150*count.get()));
                imageView.setY(3);
                cardsPane.getChildren().add(imageView);
                count.getAndIncrement();
                System.out.println("Big");
            }
        });
    }

    public void enablePlayerCards() {
        cardsPane.setDisable(false);
    }

    public void disablePlayerCards() {
        cardsPane.setDisable(true);
    }

    public void updateRobberLocation(Location location, Location previousLocation) {
        if (previousLocation != null)
            circles[previousLocation.getX()][previousLocation.getY()].setVisible(true);

        Circle circle = circles[location.getX()][location.getY()];
        circle.setVisible(false);
        robberImage.setLayoutX(circle.getLayoutX() - (robberImage.getImage().getWidth() / 2));
        robberImage.setLayoutY(circle.getLayoutY() - (robberImage.getImage().getHeight() / 2));
    }

    //turn buttons
    @FXML
    void actionButtonClicked(MouseEvent event) {
        System.out.println("Action button clicked");
        if (!actionButtonEnabled) return;

        switch (GameState.getPhase()) {
            case TRADE -> HelloApplication.toggleMaritimeTradeMenu();
            case BUY -> HelloApplication.toggleBuildMenu();
        }
    }

    @FXML
    void endButtonClicked(MouseEvent event) {
        switch (GameState.getPhase()) {
            case TRADE -> gameState.buyPhase();
            case BUY -> gameState.nextTurn();
        }
    }

    @FXML
    void helpClicked(MouseEvent event) {
        actionLogText.appendText("Help clicked\n");
        HelloApplication.toggleHelpMenu();
    }


    //top menu related stuffs
    @FXML //for top menu x button
    void closeClicked(MouseEvent event) {
        for(Stage s : HelloApplication.stages){
            try {
                s.close();
            } catch (NullPointerException ignored) {

            }
        }
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

    // dice clicked
    @FXML
    void rollClicked(MouseEvent event) {
        System.out.println("Roll clicked");
        gameState.rollDice();
    }

    private Player domesticTradePlayer = null;

    public void playerButton1Clicked(MouseEvent mouseEvent) {
        Player correspondingPlayer = getCorrespondingPlayer(1);

        if (GameState.isStealing) {
            gameState.stealFromPlayer(correspondingPlayer);
            disableSteal();
            return;
        }

        HelloApplication.showDomesticTradeMenu(gameState.getCurrentPlayer());

        domesticTradePlayer = correspondingPlayer;
        domesticTradeController.followup = true;
    }

    public void playerButton2Clicked(MouseEvent mouseEvent) {
        Player correspondingPlayer = getCorrespondingPlayer(2);

        if (GameState.isStealing) {
            gameState.stealFromPlayer(correspondingPlayer);
            disableSteal();
            return;
        }

        HelloApplication.showDomesticTradeMenu(gameState.getCurrentPlayer());
        domesticTradePlayer = correspondingPlayer;
        domesticTradeController.followup = true;
    }

    public void playerButton3Clicked(MouseEvent mouseEvent) {
        Player correspondingPlayer = getCorrespondingPlayer(3);

        if (GameState.isStealing) {
            gameState.stealFromPlayer(correspondingPlayer);
            disableSteal();
            return;
        }

        HelloApplication.showDomesticTradeMenu(gameState.getCurrentPlayer());
        domesticTradePlayer = correspondingPlayer;
        domesticTradeController.followup = true;
    }

    public void domesticTradeFollowup(){
        HelloApplication.showDomesticTradeMenu(domesticTradePlayer);
        domesticTradePlayer = null;
    }

    public Player getCorrespondingPlayer(int index) {
        int count = 1;

        for (Player player: GameState.getPlayers()) {
            if (player.equals(GameState.getCurrentPlayer())) continue;

            if (count == index) {
                System.out.println("Corresponding player: " + player.getId());
                return player;
            }

            count++;
        }

        return null;
    }

    public static GameState getGameState() {
        return gameState;
    }

}