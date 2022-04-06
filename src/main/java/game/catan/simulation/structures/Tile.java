package game.catan.simulation.structures;

import game.catan.simulation.engine.GameState;
import game.catan.simulation.engine.Initialize;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Tile {
    private String resource;
    private Tile[] roads;
    HashMap<String, Integer> structures;
    private int tileNumber;
    private ImagePattern numberPattern;
    public static List<Integer> numbers;


    public Tile(String r){
        roads = new Tile[6];
        resource = r;
        tileNumber = -1;
        int[] tileNumbers = {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
        numbers = Arrays.stream(tileNumbers).boxed().collect(Collectors.toList());
    }

    public void initialize(){
        if(tileNumber != -1) return;
        if(resource.equals("Desert")){
            numberPattern = null;
            tileNumber = -1;
            return;
        }
        tileNumber = numbers.remove(0);
        int[] num = {0,0,0,1,2,3,4,0,5,6,7,8,9};
        numberPattern = Initialize.dicePatterns[num[tileNumber]];
    }

    public String getResource() {
        return resource;
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public ImagePattern getNumberPattern() {
        return numberPattern;
    }

    public Tile[] getRoads() {
        return roads;
    }

    public void setRoads(Tile[] roads) {
        this.roads = roads;
    }

    //create static Stack which holds tile numbers
    //create initialize() method which gives tile a tile number from the stack
}


// reduce y value by 6 when upgrading settlement to city