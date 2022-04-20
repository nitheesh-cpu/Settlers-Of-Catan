package game.catan.simulation.engine;
import game.catan.simulation.card.DevelopmentCard;
import game.catan.simulation.structures.ResourceType;
import game.catan.simulation.structures.Structure;
import game.catan.simulation.structures.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private Color color;
    private Stockpile resources;
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

    public int getResource(ResourceType n) {
        switch (n) {
            case BRICK -> {
                return resources.getBricks();
            }
            case ORE -> {
                return resources.getOre();
            }
            case WOOD -> {
                return resources.getWood();
            }
            case WHEAT -> {
                return resources.getWheat();
            }
            case WOOL -> {
                return resources.getWool();

                //don't need to return MISC or desert cuz thats 0 by default
            }


        }
        return -1;


    }

    public int getNumKnights() {
        return numKnights;
    }

    public int getNumSettlements() {
        return numSettlements;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getNumRoads() {
        return numRoads;
    }

    public int getNumCities() {
        return numCities;
    }

    public boolean hasResources(ResourceType n) {
        switch (n) {
            case BRICK -> {
                return resources.getBricks() == 0;
            }
            case ORE -> {
                return resources.getOre() == 0;
            }
            case WOOD -> {
                return resources.getWood() == 0;
            }
            case WOOL -> {
                return resources.getWool() == 0;
            }
            case WHEAT -> {
                return resources.getWheat() == 0;
            }

        }
        return false;
    }


}












}
