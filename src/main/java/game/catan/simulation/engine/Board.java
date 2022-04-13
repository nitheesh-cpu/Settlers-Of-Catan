package game.catan.simulation.engine;

import game.catan.simulation.card.DevelopmentCard;
import game.catan.simulation.card.DevelopmentCardType;
import game.catan.simulation.structures.ResourceType;
import game.catan.simulation.structures.Road;
import game.catan.simulation.structures.Structure;
import game.catan.simulation.structures.Tile;

import java.util.Collections;
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
        developmentCards = new Stack<>();
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
        int numOfProgress = 3; // 6 in total
        int numOfVictory = 5;

        String[] victoryCards = new String[]{"Chapel", "Great Hall", "Library", "Market", "University"};
        String[] progressCards = new String[]{"Monopoly", "Road Building", "Year of Plenty"};

        for (int i = 0; i < numOfKnight; i++) {
            developmentCards.push(new DevelopmentCard("Knight", DevelopmentCardType.KNIGHT));
        }

        for (int i = 0; i < numOfProgress; i++) {
            developmentCards.push(new DevelopmentCard(progressCards[i], DevelopmentCardType.PROGRESS));
            developmentCards.push(new DevelopmentCard(progressCards[i], DevelopmentCardType.PROGRESS));
        }

        for (int i = 0; i < numOfVictory; i++) {
            developmentCards.push(new DevelopmentCard(victoryCards[i], DevelopmentCardType.VICTORY_POINT));
        }

        Collections.shuffle(developmentCards);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void print() {
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                System.out.print(value.getResource() + " " + value.getTileNumber() + "\n");
            }
        }
    }

}
