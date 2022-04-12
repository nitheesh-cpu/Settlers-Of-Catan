package game.catan.simulation.card;

import javax.swing.*;

public class DevelopmentCard {

    private int victoryPoints;
    private ImageIcon image;
    private String type;


    public DevelopmentCard(ImageIcon i, DevelopmentCardType type)
    {
        switch (type) {
            case CHAPEL, GREAT_HALL, LIBRARY, MARKET, UNIVERSITY -> victoryPoints = 1;
            default -> victoryPoints = 0;
        }
        this.type = type.name();
        image = i;
    }

    public enum DevelopmentCardType {CHAPEL, GREAT_HALL, LIBRARY,
    MARKET, UNIVERSITY, KNIGHT, ROAD_BUILDING, MONOPOLY,
    YEAR_OF_PLENTY}

   public int getVictoryPoints()
   {
       return victoryPoints;
   }

   public ImageIcon getImage()
   {
       return image;
   }
   public String getType()
   {
       return type;
   }
}
