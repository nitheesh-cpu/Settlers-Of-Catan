package game.catan.simulation.cards;

import game.catan.simulation.enums.DevelopmentCardType;

public class DevelopmentCard {

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
}
