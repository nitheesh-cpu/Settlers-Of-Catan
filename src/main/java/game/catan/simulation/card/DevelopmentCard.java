package game.catan.simulation.card;

import javax.swing.*;

public class DevelopmentCard {

    private ImageIcon image;
    private DevelopmentCardType type;
    private String name;
    //private Player owner;

    public DevelopmentCard(String name, DevelopmentCardType type/*Player p*/)
    {
        this.name = name;
        this.type = type;
        //owner = p;

        // TO DO: add images to dev cards
        switch (type) {
            case VICTORY_POINT -> {
                switch (name) {
                    case "Chapel" -> image = new ImageIcon("src/main/resources/game/catan/Cards/VictoryPointCards/CHAPEL.png");
                    case "Great Hall" -> image = new ImageIcon("src/main/resources/game/catan/Cards/VictoryPointCards/GREAT_HALL.png");
                    case "Library" -> image = new ImageIcon("src/main/resources/game/catan/Cards/VictoryPointCards/LIBRARY.png");
                    case "Market" -> image = new ImageIcon("src/main/resources/game/catan/Cards/VictoryPointCards/MARKET.png");
                    case "University" -> image = new ImageIcon("src/main/resources/game/catan/Cards/VictoryPointCards/UNIVERSITY.png");
                }
            }
            case KNIGHT -> image = new ImageIcon("src/main/resources/game/catan/Cards/KnightCard/KNIGHT.png");
            case PROGRESS -> {
                switch (name) {
                    case "Monopoly" -> image = new ImageIcon("src/main/resources/game/catan/Cards/ProgressCards/MONOPOLY.png");
                    case "Road Building" -> image = new ImageIcon("src/main/resources/game/catan/Cards/ProgressCards/ROAD_BUILDING.png");
                    case "Year of Plenty" -> image = new ImageIcon("src/main/resources/game/catan/Cards/ProgressCards/YEAR_OF_PLENTY.png");
                }
            }
        }
    }

   public ImageIcon getImage()
   {
       return image;
   }

   public String getName() {
       return name;
   }

   public DevelopmentCardType getType()
   {
       return type;
   }
   /*public Player getOwner()
   {
       return owner;
   }*/
}

