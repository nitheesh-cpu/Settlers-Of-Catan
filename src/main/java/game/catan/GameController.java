package game.catan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameController {
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
    public void initialize() throws FileNotFoundException {
        Polygon[] waters = {water1,water2,water3,water4,water5,water6,water7,water8,water9,water10,water11,water12,water13,water14,water15,water16,water17,water18};
        Polygon[][] tiles = {{tile01,tile02,tile03},{tile11,tile12,tile13,tile14},{tile21,tile22,tile23,tile24,tile25}, {tile31,tile32,tile33,tile34},{tile41,tile42,tile43}};
        Circle[][] circles = {{dice01,dice02,dice03},{dice11,dice12,dice13,dice14},{dice21,dice22,dice23,dice24,dice25}, {dice31,dice32,dice33,dice34},{dice41,dice42,dice43}};
        Image brick = new Image("game/catan/Tile/BrickTile.png");
        Image desert = new Image("game/catan/Tile/DesertTileEXP.png");
        Image grass = new Image("game/catan/Tile/GrassTileExp.png");
        Image plains = new Image("game/catan/Tile/PlanesTileExp.png");
        Image stone = new Image("game/catan/Tile/StoneTileExp.png");
        Image wheat = new Image("game/catan/Tile/WheatTileEx.png");
        Image water = new Image("game/catan/Tile/waterTileExp.png");
        ImagePattern brickPattern = new ImagePattern(brick, 63, 57, 130, 116, false);
        ImagePattern desertPattern = new ImagePattern(desert, 69, 62, 137, 125, false);
        ImagePattern grassPattern = new ImagePattern(grass, 64, 59, 130, 118, false);
        ImagePattern plainsPattern = new ImagePattern(plains, 67, 57, 135, 116, false);
        ImagePattern stonePattern = new ImagePattern(stone, 71, 57, 145, 116, false);
        ImagePattern wheatPattern = new ImagePattern(wheat, 67, 57, 135, 116, false);
        ImagePattern waterPattern = new ImagePattern(water, 67, 56, 135, 112, false);
        ImagePattern[] tilePatterns = {brickPattern,desertPattern,grassPattern,plainsPattern,stonePattern,wheatPattern};

        Image two = new Image("game/catan/DiceMarkings/2.png");
        Image three = new Image("game/catan/DiceMarkings/3.png");
        Image four = new Image("game/catan/DiceMarkings/4.png");
        Image five = new Image("game/catan/DiceMarkings/5.png");
        Image six = new Image("game/catan/DiceMarkings/6.png");
        Image eight = new Image("game/catan/DiceMarkings/8.png");
        Image nine = new Image("game/catan/DiceMarkings/9.png");
        Image ten = new Image("game/catan/DiceMarkings/10.png");
        Image eleven = new Image("game/catan/DiceMarkings/11.png");
        Image twelve = new Image("game/catan/DiceMarkings/12.png");
        ImagePattern twoPattern = new ImagePattern(two,31,35,60,60,false);
        ImagePattern threePattern = new ImagePattern(three,31,35,60,60,false);
        ImagePattern fourPattern = new ImagePattern(four,31,35,60,60,false);
        ImagePattern fivePattern = new ImagePattern(five,31,35,60,60,false);
        ImagePattern sixPattern = new ImagePattern(six,31,35,60,60,false);
        ImagePattern eightPattern = new ImagePattern(eight,31,35,60,60,false);
        ImagePattern ninePattern = new ImagePattern(nine,31,35,60,60,false);
        ImagePattern tenPattern = new ImagePattern(ten,31,35,60,60,false);
        ImagePattern elevenPattern = new ImagePattern(eleven,31,35,60,60,false);
        ImagePattern twelvePattern = new ImagePattern(twelve,31,35,60,60,false);
        ImagePattern[] dicePatterns = {twoPattern,threePattern,fourPattern,fivePattern,sixPattern,eightPattern,ninePattern,tenPattern,elevenPattern,twelvePattern};


        for(Polygon[] x : tiles){
            for(Polygon tile: x){
                int num = (int) (Math.random()*6);
                tile.setFill(tilePatterns[num]);
            }
        }
        for(Polygon tile: waters){
            tile.setFill(waterPattern);
        }
        for(Circle[] x: circles){
            for(Circle circle: x){
                int num = (int) (Math.random()*10);
                circle.setFill(dicePatterns[num]);
            }
        }
    }
}