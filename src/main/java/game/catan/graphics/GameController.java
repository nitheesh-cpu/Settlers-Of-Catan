package game.catan.graphics;

import game.catan.simulation.engine.Initialize;
import game.catan.simulation.structures.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static game.catan.simulation.engine.Initialize.*;

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
        Initialize.init();
        Polygon[] waters = {water1,water2,water3,water4,water5,water6,water7,water8,water9,water10,water11,water12,water13,water14,water15,water16,water17,water18};
        Polygon[][] tiles = {{tile01,tile02,tile03},{tile11,tile12,tile13,tile14},{tile21,tile22,tile23,tile24,tile25}, {tile31,tile32,tile33,tile34},{tile41,tile42,tile43}};
        Circle[][] circles = {{dice01,dice02,dice03},{dice11,dice12,dice13,dice14},{dice21,dice22,dice23,dice24,dice25}, {dice31,dice32,dice33,dice34},{dice41,dice42,dice43}};
        int[] tileRandomizer = {0,0,0,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5};
        List<Integer> tilesList = Arrays.stream(tileRandomizer).boxed().collect(Collectors.toList());
        Collections.shuffle(tilesList);
        int[] diceNumbers = {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
        for(Polygon[] x : tiles){
            for(Polygon tile: x){
                tile.setFill(tilePatterns[tilesList.remove(0)]);
            }
        }
        for(Polygon tile: waters){
            tile.setFill(waterPattern);
        }
//        for(Circle[] x: circles){
//            for(Circle circle: x){
//                int num = (int) (Math.random()*10);
//                circle.setFill(dicePatterns[num]);
//            }
//        }
    }

    public void initializeTileNumbers(){
        int corner = 0;
        int lastCorner = corner+5 % 6;
        int depth = 0;
        int xCoords[] = new int[]{0, 0, 0, 2, 4, 2};
        int yCoords[] = new int[]{0, 2, 4, 4, 2, 0};
        int x = xCoords[corner];
        int y = yCoords[corner];
        int w = 1;
        Tile tiles[][] = null;
        while(depth<4){
            if(corner == 0 || depth > 0){
                if(lastCorner == 0)
                    w++;
                for(int q = 0; q < 2/w; q++){
                    tiles[++y][x].initialize();
                }
                if(lastCorner == 0){
                    tiles[++y][++x].initialize();
                }
            }
            if(corner == 1 || depth > 0){
                if(lastCorner == 1)
                    w++;
                for(int q = 0; q < 2/w; q++){
                    tiles[++y][x].initialize();
                }
                if(lastCorner == 1){
                    tiles[y][++x].initialize();
                }
            }
            if(corner == 2 || depth > 0){
                if(lastCorner == 2)
                    w++;
                for(int q = 0; q < 2/w; q++){
                    tiles[y][++x].initialize();
                }
                if(lastCorner == 2){
                    tiles[--y][++x].initialize();
                }
            }
            if(corner == 3 || depth > 0){
                if(lastCorner == 3)
                    w++;
                for(int q = 0; q < 2/w; q++){
                    tiles[--y][++x].initialize();
                }
                if(lastCorner == 3){
                    tiles[--y][x].initialize();
                }
            }
            if(corner == 4 || depth > 0){
                if(lastCorner == 4)
                    w++;
                for(int q = 0; q < 2/w; q++){
                    tiles[--y][--x].initialize();
                }
                if(lastCorner == 4){
                    tiles[y][--x].initialize();
                }
            }
            if(corner == 5 || depth > 0){
                if(lastCorner == 5)
                    w++;
                for(int q = 0; q < 2/w; q++){
                    tiles[y][--x].initialize();
                }
                if(lastCorner == 5){
                    tiles[++y][x].initialize();
                }
            }
            depth++;
        }
    }
}