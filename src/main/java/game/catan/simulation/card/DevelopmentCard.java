package game.catan.simulation.card;

import javax.swing.*;

public class DevelopmentCard extends Card{

    private int victoryPoints;


    public DevelopmentCard(ImageIcon i, DevelopmentCardType type)
    {
        super(i,type);

        switch (type) {
            case CHAPEL, GREAT_HALL, LIBRARY, MARKET, UNIVERSITY -> victoryPoints = 1;
            default -> victoryPoints = 0;
        }
    }

    public enum DevelopmentCardType {CHAPEL, GREAT_HALL, LIBRARY,
    MARKET, UNIVERSITY, KNIGHT, ROAD_BUILDING, MONOPOLY,
    YEAR_OF_PLENTY}

   public int getVictoryPoints()
   {
       return victoryPoints;
   }

   public void pickCard()
   {
       //should randomly pick a card in the enum and send it back to the constructor
   }
}
