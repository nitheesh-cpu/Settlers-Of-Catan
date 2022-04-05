package game.catan.simulation.card;

import javax.swing.*;

public class DevelopmentCard extends Card{

    private int victoryPoints;


    public DevelopmentCard(ImageIcon i, String str)
    {
        super(i,str);

        if(str.equals("DEVELOPMENT_CHAPEL")||str.equals("DEVELOPMENT_GREAT_HALL")||str.equals("DEVELOPMENT_LIBRARY")||
        str.equals("DEVELOPMENT_MARKET")||str.equals("DEVELOPMENT_UNIVERSITY"))
            victoryPoints=1;
        else
            victoryPoints=0;
    }

    public enum DevelopmentCardType {DEVELOPMENT_CHAPEL, DEVELOPMENT_GREAT_HALL, DEVELOPMENT_LIBRARY,
    DEVELOPMENT_MARKET, DEVELOPMENT_UNIVERSITY, DEVELOPMENT_KNIGHT, DEVELOPMENT_ROAD_BUILDING, DEVELOPMENT_MONOPOLY,
    DEVELOPMENT_YEAR_OF_PLENTY}

   public int getVictoryPoints()
   {
       return victoryPoints;
   }

   public void pickCard()
   {
       //should randomly pick a card in the enum and send it back to the constructor
   }
}
