package game.catan.simulation.card;

import javax.swing.*;

public class SpecialCard {

    private int victoryPoints;
    private ImageIcon image;
    private String specialType;

    public SpecialCard(SpecialCardType special)
    {
        image = null;
        specialType = special.name();

        switch (special)
        {
            case LARGEST_ARMY, LONGEST_ROAD: victoryPoints = 2;
        }
    }

    public enum SpecialCardType{LARGEST_ARMY, LONGEST_ROAD}

    public String getType()
    {
        return specialType;
    }
    public ImageIcon getImage()
    {
        return image;
    }

}
