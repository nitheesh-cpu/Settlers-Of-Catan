package game.catan.simulation.card;

import javax.swing.*;

public class SpecialCard {

    private ImageIcon image;
    private SpecialCardType type;
    //private Player owner;

    public SpecialCard(SpecialCardType type/*Player p*/) {

        this.type = type;
        //owner = p;
        //TO DO: add images to special cards

            switch (type) {
                case LONGEST_ROAD -> image = new ImageIcon("src/main/resources/game/catan/Cards/SpecialCards/LONGEST_ROAD.png");
                case LARGEST_ARMY -> image = new ImageIcon("src/main/resources/game/catan/Cards/SpecialCards/LARGEST_ARMY.png");
            }
        }


    public ImageIcon getImage()
    {
        return image;
    }

    public SpecialCardType getType()
    {
        return type;
    }

    /*public Player getPlayer()
    {
        return owner;
    }*/
}
