package game.catan.simulation.structures;

import game.catan.simulation.engine.GameState;
import game.catan.simulation.engine.Initialize;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Tile {
    private ResourceType resource;
    private Road[] roads;
    private Polygon polygon;
    private Structure[] structures;
    private int tileNumber;
    private ImagePattern numberPattern;
    public static List<Integer> numbers;


    public Tile(ResourceType r){
        structures = new Structure[6];
        roads = new Road[6];
        resource = r;
        tileNumber = -1;
        int[] tileNumbers = {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
        numbers = Arrays.stream(tileNumbers).boxed().collect(Collectors.toList());
    }

    public void initialize(){
        if(tileNumber != -1) return;

        if(resource == ResourceType.DESERT){
            numberPattern = null;
            return;
        }

        tileNumber = numbers.remove(0);
        int[] num = {0,0,0,1,2,3,4,0,5,6,7,8,9};
        numberPattern = Initialize.dicePatterns[num[tileNumber]];
    }

    public ResourceType getResource() {
        return resource;
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public ImagePattern getNumberPattern() {
        return numberPattern;
    }

    public Road[] getRoads() {
        return roads;
    }

    public void setRoads(Road[] roads) {
        this.roads = roads;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Structure[] getStructures() {
        return structures;
    }

    public void setStructures(Structure[] structures) {
        this.structures = structures;
    }


    //create static Stack which holds tile numbers
    //create initialize() method which gives tile a tile number from the stack
}


// reduce y value by 6 when upgrading settlement to city