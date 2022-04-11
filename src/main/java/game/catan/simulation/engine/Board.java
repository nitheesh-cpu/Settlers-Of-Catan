package game.catan.simulation.engine;

import game.catan.simulation.card.DevelopmentCard;
import game.catan.simulation.structures.ResourceType;
import game.catan.simulation.structures.Road;
import game.catan.simulation.structures.Structure;
import game.catan.simulation.structures.Tile;

import java.util.Stack;

public class Board {
    private Tile[][] tiles;
    private Structure[][] structures;
    private Road[][] roads;
    private Tile robberTile;
    private Dice dice;
    private Stack<DevelopmentCard> developmentCards;
    private Stockpile stockpile;

    public Board(Tile[][] tiles) {
        // starts with 19 resource cards for each resource
        stockpile = new Stockpile(19, 19, 19, 19, 19);
        this.tiles = tiles;
    }

    public void initializeRobber() {
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                if (value.getResource() == ResourceType.DESERT) {
                    robberTile = value;
                    break;
                }
            }
        }
    }

    public void createCards() {
        int numOfKnight = 14;
        int numOfProgress = 6;
        int numOfVictory = 5;

        // TO DO: create development cards
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void print() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                System.out.print(tiles[i][j].getResource() + " " + tiles[i][j].getTileNumber() + "\n");
            }
        }
    }

}
