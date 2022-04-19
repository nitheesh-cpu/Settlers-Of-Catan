package game.catan.simulation.engine;
import game.catan.simulation.card.DevelopmentCard;
import game.catan.simulation.structures.Structure;
import game.catan.simulation.structures.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private Color color;
    //public static Stockpile resources;
    private ArrayList<DevelopmentCard> devCards;
    private ArrayList<Structure> structures;
    //private ArrayList<Road> roads;    -for some reason it shows an error with the type Road
    private int numKnights;
    public static int numSettlements = 5;
    private int victoryPoints;
    public static int numRoads = 15;
    public static int numCities = 4;

    private boolean hasLargestArmy;
    private boolean hasLargestRoad;
    private Boolean[] ports;
    //private ArrayList<Tile> tiles;    -for some reason it shows an error with the type Tile

    private Stockpile resources;

    public Player() {
        resources = new Stockpile();
    }

    public Stockpile getResources() {
        return resources;
    }


    // TODO: will just return tiles variable
    public Tile[] getAdjacentTiles() {
        return null;
    }

    public Color getColor() {
        return color;
        // How do I pass the enum thing onto here
    }

    public int getResource(Stockpile n) {

        return n.getBricks() + n.getOre() + n.getWheat() + n.getWood() + n.getWool();
    }







}
