package game.catan.simulation.card;

import javax.swing.*;

public class Card {
    private ImageIcon image;
    private DevelopmentCard.DevelopmentCardType type;

    public Card(ImageIcon i, DevelopmentCard.DevelopmentCardType str)
    {
        image=i;
        type=str;
    }
    public ImageIcon getImage()
    {
        return image;
    }

    public DevelopmentCard.DevelopmentCardType getName()
    {
        return type;
    }
}
