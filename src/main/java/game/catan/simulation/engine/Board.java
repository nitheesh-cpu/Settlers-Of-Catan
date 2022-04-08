package game.catan.simulation.engine;

import game.catan.simulation.card.DevelopmentCard;
import game.catan.simulation.structures.Road;
import game.catan.simulation.structures.Structure;
import game.catan.simulation.structures.Tile;

import java.util.Stack;

public class Board {
    private Tile[][] tiles;
    private Structure[][] structures;
    private Road[][] roads;
    private Location robberLocation;
    private Dice dice;
    private Stack<DevelopmentCard> developmentCards;
    private Stockpile stockpile;

}
