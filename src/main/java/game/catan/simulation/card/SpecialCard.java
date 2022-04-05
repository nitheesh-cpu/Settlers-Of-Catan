package game.catan.simulation.card;

import javax.swing.*;

public class SpecialCard extends Card{

//    private Player owner;
    private int victoryPoints;

    public SpecialCard(ImageIcon i, String str)
    {
        super(i,str);
        victoryPoints = 2;
    }

    public enum SpecialCardType{LARGEST_ARMY, LONGEST_ROAD}

    public void pickCard()
    {
        //should randomly pick a card in the enum
    }
}
