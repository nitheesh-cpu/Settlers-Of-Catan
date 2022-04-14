package game.catan.simulation.structures;

import game.catan.simulation.engine.Location;

public class Vertex {

    private Structure structure;
    private Location location;

    public Vertex(Structure s, Location loc)
    {
        structure = s;
        location = loc;
    }
    public void setStructure(Structure s)
    {
        structure = s;
    }

    public Structure getStructure()
    {
        return structure;
    }

    public boolean hasStructure()
    {
        if(structure!=null)
            return true;
        return false;
    }

    public Location getLocation()
    {
        return location;
    }
}
