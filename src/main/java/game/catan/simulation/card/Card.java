package game.catan.simulation.card;

import javax.swing.*;

public class Card {
    private ImageIcon image;
    private String type;

    public Card(ImageIcon i, String str)
    {
        image=i;
        type=str;
    }
    public ImageIcon getImage()
    {
        return image;
    }

    public String getName()
    {
        return type;
    }
}
