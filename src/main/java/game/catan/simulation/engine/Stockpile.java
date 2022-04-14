package game.catan.simulation.engine;

import game.catan.simulation.structures.ResourceType;

public class Stockpile {
    private int bricks, wheat, wood, ore, wool;

    public Stockpile() {
        this.bricks = 0;
        this.wheat = 0;
        this.wood = 0;
        this.ore = 0;
        this.wool = 0;
    }

    public Stockpile(int bricks, int wheat, int wood, int ore, int wool) {
        this.bricks = bricks;
        this.wheat = wheat;
        this.wood = wood;
        this.ore = ore;
        this.wool = wool;
    }

    public void add(ResourceType resource) {
        switch (resource) {
            case BRICK -> this.bricks++;
            case WHEAT -> this.wheat++;
            case WOOD -> this.wood++;
            case ORE -> this.ore++;
            case WOOL -> this.wool++;
        }
    }

    public void add(ResourceType resource, int count) {
        switch (resource) {
            case BRICK -> this.bricks += count;
            case WHEAT -> this.wheat += count;
            case WOOD -> this.wood += count;
            case ORE -> this.ore += count;
            case WOOL -> this.wool += count;
        }
    }

    public void remove(ResourceType resource) {
        switch (resource) {
            case BRICK -> this.bricks--;
            case WHEAT -> this.wheat--;
            case WOOD -> this.wood--;
            case ORE -> this.ore--;
            case WOOL -> this.wool--;
        }
    }

    public void remove(ResourceType resource, int count) {
        switch (resource) {
            case BRICK -> this.bricks -= count;
            case WHEAT -> this.wheat -= count;
            case WOOD -> this.wood -= count;
            case ORE -> this.ore -= count;
            case WOOL -> this.wool -= count;
        }
    }

    public int getTotal() {
        return this.bricks + this.wheat + this.wood + this.ore + this.wool;
    }

    public int getBricks() {
        return bricks;
    }

    public int getWheat() {
        return wheat;
    }

    public int getWood() {
        return wood;
    }

    public int getOre() {
        return ore;
    }

    public int getWool() {
        return wool;
    }

    public void setBricks(int bricks) {
        this.bricks = bricks;
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public void setWool(int wool) {
        this.wool = wool;
    }
}

