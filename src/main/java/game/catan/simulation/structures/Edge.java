package game.catan.simulation.structures;

import game.catan.simulation.engine.Location;

public class Edge {
    private Road road;
    private Location location;

    public Edge(Road road, Location loc)
    {
        this.road = road;
        this.location = loc;
    }

    public void setRoad(Road r)
    {
        road = r;
    }

    public Road getRoad()
    {
        return road;
    }

    public boolean hasRoad()
    {
        if(road!=null)
            return true;
        return false;
    }

    public Location getLocation()
    {
        return location;
    }

}
