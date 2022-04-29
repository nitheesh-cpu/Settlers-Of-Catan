package game.catan.simulation;

import game.catan.simulation.enums.ResourceType;
import game.catan.simulation.helper.Vertex;
import javafx.scene.image.ImageView;

public class Harbor {

    private static int numHarbors = 0;

    private Vertex[] vertices;
    private final ResourceType resourceType;
    private final int ratio;
    private final int id;
    private ImageView image;

    public Harbor(ResourceType type) {
        vertices = new Vertex[2];

        this.resourceType = type;
        this.id = numHarbors++;

        if (type == ResourceType.MISC) {
            ratio = 3;
        } else {
            ratio = 2;
        }


    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getRatio() {
        return ratio;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Harbor " + id + ": " + resourceType;
    }
}