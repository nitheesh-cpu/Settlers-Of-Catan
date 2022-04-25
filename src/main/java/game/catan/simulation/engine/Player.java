package game.catan.simulation.engine;
import game.catan.simulation.card.DevelopmentCard;
import game.catan.simulation.structures.ResourceType;
import game.catan.simulation.structures.Structure;
import game.catan.simulation.structures.Tile;

import java.awt.*;
import java.util.ArrayList;

import static game.catan.simulation.structures.Structure.StructureType.CITY;

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
    public static int numCities = 0;

    private boolean hasLargestArmy;
    private boolean hasLargestRoad;
    private Boolean[] ports;
    //private ArrayList<Tile> tiles;    -for some reason it shows an error with the type Tile

    private Stockpile resources;

    public Player() {
        resources = new Stockpile();
        victoryPoints = 0;
    }

    public Stockpile getResources() {
        return resources;
    }


    // TODO: will just return tiles variable
    public Tile[] getAdjacentTiles() {
        return null;
    }

    public Color getColor(Color c) {
        return c;
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

    //adding resources
    public void addResources(ResourceType n, int count, Stockpile stockpile)
    {
        resources.add(n,stockpile.remove(n,count));
    }

    //total resources
    public int totalResources()
    {
        return resources.getBricks()+resources.getWool()+resources.getWheat()+resources.getWood() + resources.getOre();
    }

    //removing resources
    public void removeResource(ResourceType n, int count, Stockpile stockpile)
    {
        stockpile.add(n,resources.remove(n,count));
    }

    //when robber is activated and player has more than seven resources
    public void removeHalfResources(int b, int o, int w, int wl, int wh, Stockpile stockpile)
    {
        stockpile.add(ResourceType.BRICK,resources.remove(ResourceType.BRICK,b));
        stockpile.add(ResourceType.ORE,resources.remove(ResourceType.ORE,o));
        stockpile.add(ResourceType.WOOD,resources.remove(ResourceType.WOOD,w));
        stockpile.add(ResourceType.WOOL,resources.remove(ResourceType.WOOL,wl));
        stockpile.add(ResourceType.WHEAT,resources.remove(ResourceType.WHEAT,wh));

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

    public boolean hasMoreThanSeven() {
        if(totalResources() > 7) {
            return true;
        }
        return false;
    }

    public void addSettlements() {
        numSettlements++;
    }

    public void addCity(Structure settlement)
    {
        //three ore, two wheat, and one settlment are needed for a city
        if(resources.getOre()==3&&resources.getWheat()==2&&settlement.getType()== Structure.StructureType.SETTLEMENT&&settlement.getLocation()!=null)
        {
             settlement.setType(CITY);
             numCities++;
             victoryPoints+=2;
        }
    }





}

