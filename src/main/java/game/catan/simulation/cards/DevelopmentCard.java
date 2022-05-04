package game.catan.simulation.cards;

import game.catan.simulation.enums.DevelopmentCardType;

import java.util.Comparator;

public class DevelopmentCard implements Comparable<DevelopmentCard> {

    private final String name;
    private final DevelopmentCardType type;

    public DevelopmentCard(String name, DevelopmentCardType type) {
        this.name = name;
        this.type = type;
    }

    public DevelopmentCardType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " - " + type;
    }

    @Override
    public int compareTo(DevelopmentCard o) {
        return this.type.compareTo(o.type);
    }
}
