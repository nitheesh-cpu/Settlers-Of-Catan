package game.catan.simulation;

import game.catan.simulation.enums.ResourceType;
import game.catan.simulation.helper.Edge;
import game.catan.simulation.helper.Location;
import game.catan.simulation.helper.Vertex;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.Arrays;

public class Tile {
    public static final int NORTHWEST = 0, NORTH = 1, NORTHEAST = 2, SOUTHEAST = 3, SOUTH = 4, SOUTHWEST = 5;

    private final ResourceType resource;
    private int number;

    private Tile[] adjacentTiles;
    private Edge[] edges;
    private Vertex[] vertices;

    private Location location;

    private Polygon polygon;
    private ImagePattern tokenImage;

    public Tile(ResourceType resource){
        this(resource, null);
    }

    public Tile(ResourceType resource, Location location) {
        this.resource = resource;
        number = -1;

        adjacentTiles = new Tile[6];
        edges = new Edge[6];
        vertices = new Vertex[6];

        this.location = location;

        polygon = null;
        tokenImage = null;
    }

    //region Setters
    // region Adjacent Tiles/Edges/Vertices
    public void setAdjacentTiles() {
        Tile[][] board = Board.getBoard();
        int row = location.getRow();
        int col = location.getCol();

        switch (location.getRow()) {
            case 0, 1 -> {
                // NW
                if (Board.isValidCoordinate(row - 1, col - 1)) {
                    adjacentTiles[NORTHWEST] = board[row - 1][col - 1];
                }

                // N
                if (Board.isValidCoordinate(row - 1, col)) {
                    adjacentTiles[NORTH] = board[row - 1][col];
                }

                // NE
                if (Board.isValidCoordinate(row, col + 1)) {
                    adjacentTiles[NORTHEAST] = board[row][col + 1];
                }

                // SE
                if (Board.isValidCoordinate(row + 1, col + 1)) {
                    adjacentTiles[SOUTHEAST] = board[row + 1][col + 1];
                }

                // S
                if (Board.isValidCoordinate(row + 1, col)) {
                    adjacentTiles[SOUTH] = board[row + 1][col];
                }

                // SW
                if (Board.isValidCoordinate(row, col - 1)) {
                    adjacentTiles[SOUTHWEST] = board[row][col - 1];
                }
            }
            case 2 -> {
                // NW
                if (Board.isValidCoordinate(row - 1, col - 1)) {
                    adjacentTiles[NORTHWEST] = board[row - 1][col - 1];
                }

                // N
                if (Board.isValidCoordinate(row - 1, col)) {
                    adjacentTiles[NORTH] = board[row - 1][col];
                }

                // NE
                if (Board.isValidCoordinate(row, col + 1)) {
                    adjacentTiles[NORTHEAST] = board[row][col + 1];
                }

                // SE
                if (Board.isValidCoordinate(row + 1, col)) {
                    adjacentTiles[SOUTHEAST] = board[row + 1][col];
                }

                // S
                if (Board.isValidCoordinate(row + 1, col - 1)) {
                    adjacentTiles[SOUTH] = board[row + 1][col - 1];
                }

                // SW
                if (Board.isValidCoordinate(row, col - 1)) {
                    adjacentTiles[SOUTHWEST] = board[row][col - 1];
                }
            }
            case 3, 4 -> {
                // NW
                if (Board.isValidCoordinate(row - 1, col)) {
                    adjacentTiles[NORTHWEST] = board[row - 1][col];
                }

                // N
                if (Board.isValidCoordinate(row - 1, col + 1)) {
                    adjacentTiles[NORTH] = board[row - 1][col + 1];
                }

                // NE
                if (Board.isValidCoordinate(row, col + 1)) {
                    adjacentTiles[NORTHEAST] = board[row][col + 1];
                }

                // SE
                if (Board.isValidCoordinate(row + 1, col)) {
                    adjacentTiles[SOUTHEAST] = board[row + 1][col];
                }

                // S
                if (Board.isValidCoordinate(row + 1, col - 1)) {
                    adjacentTiles[SOUTH] = board[row + 1][col - 1];
                }

                // SW
                if (Board.isValidCoordinate(row, col - 1)) {
                    adjacentTiles[SOUTHWEST] = board[row][col - 1];
                }
            }
        }
    }

    public void setAdjacentEdges() {
        setAdjacentEdge(Edge.NORTHWEST);
        setAdjacentEdge(Edge.NORTH);
        setAdjacentEdge(Edge.NORTHEAST);
        setAdjacentEdge(Edge.SOUTHEAST);
        setAdjacentEdge(Edge.SOUTH);
        setAdjacentEdge(Edge.SOUTHWEST);

        System.out.println(this + " adjacent edges: " + Arrays.toString(edges));
    }

    private void setAdjacentEdge(int edgeOrientation) {
        if (edges[edgeOrientation] != null) {
            return;
        }

        Tile adjacentTile = adjacentTiles[edgeOrientation];

        // Tile is on the edge of board
        if (adjacentTile == null) {
            edges[edgeOrientation] = new Edge();
            Board.addEdge(edges[edgeOrientation]);
            return;
        }

        int tileEdgeOrientation = -1;

        switch (edgeOrientation) {
            case Edge.NORTHWEST -> tileEdgeOrientation = Edge.SOUTHEAST;
            case Edge.NORTH -> tileEdgeOrientation = Edge.SOUTH;
            case Edge.NORTHEAST -> tileEdgeOrientation = Edge.SOUTHWEST;
            case Edge.SOUTHEAST -> tileEdgeOrientation = Edge.NORTHWEST;
            case Edge.SOUTH -> tileEdgeOrientation = Edge.NORTH;
            case Edge.SOUTHWEST -> tileEdgeOrientation = Edge.NORTHEAST;
        }

        Edge temp = adjacentTile.getEdge(tileEdgeOrientation);

        // Adjacent tile already has an edge in the correct orientation
        if (temp != null) {
            edges[edgeOrientation] = temp;
        } else {
            Edge e = new Edge();

            edges[edgeOrientation] = e;
            adjacentTile.setEdge(e, tileEdgeOrientation);
            Board.addEdge(e);
        }

    }

    public void setAdjacentVertices() {
        setAdjacentVertex(Vertex.NORTHWEST);
        setAdjacentVertex(Vertex.NORTHEAST);
        setAdjacentVertex(Vertex.EAST);
        setAdjacentVertex(Vertex.SOUTHEAST);
        setAdjacentVertex(Vertex.SOUTHWEST);
        setAdjacentVertex(Vertex.WEST);

        System.out.println(this + " adjacent vertices: " + Arrays.toString(vertices));
    }

    private void setAdjacentVertex(int vertexOrientation) {
        if (vertices[vertexOrientation] != null) { // if vertex already exists
            return;
        }

        Tile adjacentTileOne = null;
        Tile adjacentTileTwo = null;

        // finding the two adjacent tiles that share the same vertex given the vertex orientation
        switch (vertexOrientation) {
            case Vertex.NORTHWEST -> {
                adjacentTileOne = adjacentTiles[NORTHWEST];
                adjacentTileTwo = adjacentTiles[NORTH];
            }
            case Vertex.NORTHEAST -> {
                adjacentTileOne = adjacentTiles[NORTH];
                adjacentTileTwo = adjacentTiles[NORTHEAST];
            }
            case Vertex.EAST -> {
                adjacentTileOne = adjacentTiles[NORTHEAST];
                adjacentTileTwo = adjacentTiles[SOUTHEAST];
            }
            case Vertex.SOUTHEAST -> {
                adjacentTileOne = adjacentTiles[SOUTHEAST];
                adjacentTileTwo = adjacentTiles[SOUTH];
            }
            case Vertex.SOUTHWEST -> {
                adjacentTileOne = adjacentTiles[SOUTH];
                adjacentTileTwo = adjacentTiles[SOUTHWEST];
            }
            case Vertex.WEST -> {
                adjacentTileOne = adjacentTiles[SOUTHWEST];
                adjacentTileTwo = adjacentTiles[NORTHWEST];
            }
        }

        boolean tileOneExists = adjacentTileOne != null;
        boolean tileTwoExists = adjacentTileTwo != null;

        boolean vertexExists = false; // flag if the vertex has already been initialized

        int tileOneVertexOrientation = -1;
        int tileTwoVertexOrientation = -1;

        // finding the corresponding vertex orientation for the two adjacent tiles in relation to the current tile's vertex orientation
        switch (vertexOrientation) {
            case Vertex.NORTHWEST -> {
                tileOneVertexOrientation = Vertex.EAST;
                tileTwoVertexOrientation = Vertex.SOUTHWEST;
            }
            case Vertex.NORTHEAST -> {
                tileOneVertexOrientation = Vertex.SOUTHEAST;
                tileTwoVertexOrientation = Vertex.WEST;
            }
            case Vertex.EAST -> {
                tileOneVertexOrientation = Vertex.SOUTHWEST;
                tileTwoVertexOrientation = Vertex.NORTHWEST;
            }
            case Vertex.SOUTHEAST -> {
                tileOneVertexOrientation = Vertex.WEST;
                tileTwoVertexOrientation = Vertex.NORTHEAST;
            }
            case Vertex.SOUTHWEST -> {
                tileOneVertexOrientation = Vertex.NORTHWEST;
                tileTwoVertexOrientation = Vertex.EAST;
            }
            case Vertex.WEST -> {
                tileOneVertexOrientation = Vertex.NORTHEAST;
                tileTwoVertexOrientation = Vertex.SOUTHEAST;
            }
        }

        // if the one of the two adjacent tiles exists, we check if the corresponding vertex in the adjacent tile already exists
        // if the corresponding vertex does not exist, we create it
        if (tileOneExists) {
            Vertex temp = adjacentTileOne.getVertex(tileOneVertexOrientation); // get the adjacent tile's vertex with the corresponding orientation

            // if the adjacent tile's vertex exists
            if (temp != null) {
                vertices[vertexOrientation] = temp; // set this tile's vertex to the adjacent tile's vertex

                // set the same vertex reference to the other adjacent tile
                if (tileTwoExists) {
                    adjacentTileTwo.setVertex(temp, tileTwoVertexOrientation);
                }

                vertexExists = true;
            }
        }

        if (tileTwoExists && !vertexExists) {
            Vertex temp = adjacentTileTwo.getVertex(tileTwoVertexOrientation);

            if (temp != null) {
                vertices[vertexOrientation] = temp;

                // set the same vertex reference to the other adjacent tile
                if (tileOneExists) {
                    adjacentTileOne.setVertex(temp, tileOneVertexOrientation);
                }

                vertexExists = true;
            }
        }

        // we have checked the two adjacent tiles and found no corresponding vertex that exists
        // create a new vertex and set it in the tile and the two adjacent tiles
        // all three tiles will store the same vertex reference
        if (!vertexExists) {
            Vertex v = new Vertex();
            Board.addVertex(v);

            vertices[vertexOrientation] = v;

            if (tileOneExists) {
                adjacentTileOne.setVertex(v, tileOneVertexOrientation);
            }

            if (tileTwoExists) {
                adjacentTileTwo.setVertex(v, tileTwoVertexOrientation);
            }
        }

    }

    public void setAdjacentEdgesToVertices() {
        for (int i = 0; i < vertices.length; i++) {
            Vertex v = vertices[i];
            Edge[] adjacentEdges = new Edge[3];

            switch (i) {
                case Vertex.NORTHWEST -> {
                    adjacentEdges[0] = edges[Edge.NORTHWEST];
                    adjacentEdges[1] = edges[Edge.NORTH];

                    // if northern tile exists
                    if (adjacentTiles[Tile.NORTH] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.NORTH].getEdge(Edge.SOUTHWEST);
                    } else if (adjacentTiles[Tile.NORTHWEST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.NORTHWEST].getEdge(Edge.NORTHEAST);
                    }
                }
                case Vertex.NORTHEAST -> {
                    adjacentEdges[0] = edges[Edge.NORTH];
                    adjacentEdges[1] = edges[Edge.NORTHEAST];

                    if (adjacentTiles[Tile.NORTH] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.NORTH].getEdge(Edge.SOUTHEAST);
                    } else if (adjacentTiles[Tile.NORTHWEST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.NORTHWEST].getEdge(Edge.NORTHWEST);
                    }

                    System.out.println(Arrays.toString(adjacentEdges));
                    System.out.println("LOOK! " + adjacentEdges[2]);
                }
                case Vertex.EAST -> {
                    adjacentEdges[0] = edges[Edge.NORTHEAST];
                    adjacentEdges[1] = edges[Edge.SOUTHEAST];

                    if (adjacentTiles[Tile.NORTHEAST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.NORTHEAST].getEdge(Edge.SOUTH);
                    } else if (adjacentTiles[Tile.SOUTHEAST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.SOUTHEAST].getEdge(Tile.NORTH);
                    }

                }
                case Vertex.SOUTHEAST -> {
                    adjacentEdges[0] = edges[Edge.SOUTHEAST];
                    adjacentEdges[1] = edges[Edge.SOUTH];

                    if (adjacentTiles[Tile.SOUTHEAST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.SOUTHEAST].getEdge(Edge.SOUTHWEST);
                    } else if (adjacentTiles[Tile.SOUTH] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.SOUTH].getEdge(Edge.NORTHEAST);
                    }
                }
                case Vertex.SOUTHWEST -> {
                    adjacentEdges[0] = edges[Edge.SOUTH];
                    adjacentEdges[1] = edges[Edge.SOUTHWEST];

                    if (adjacentTiles[Tile.SOUTH] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.SOUTH].getEdge(Edge.NORTHWEST);
                    } else if (adjacentTiles[Tile.SOUTHWEST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.SOUTHWEST].getEdge(Edge.SOUTHEAST);
                    }
                }
                case Vertex.WEST -> {
                    adjacentEdges[0] = edges[Edge.SOUTHWEST];
                    adjacentEdges[1] = edges[Edge.NORTHWEST];

                    if (adjacentTiles[Tile.SOUTHWEST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.SOUTHWEST].getEdge(Edge.NORTH);
                    } else if (adjacentTiles[Tile.NORTHWEST] != null) {
                        adjacentEdges[2] = adjacentTiles[Tile.NORTHWEST].getEdge(Edge.SOUTH);
                    }
                }
            }

            v.setAdjacentEdges(adjacentEdges);
        }
    }

    public void setAdjacentVerticesToEdges() {
        for (int i = 0; i < edges.length; i++) {
            Edge e = edges[i];
            Vertex[] adjacentVertices = new Vertex[2];

            switch (i) {
                case Edge.NORTHWEST -> {
                    adjacentVertices[0] = vertices[Vertex.WEST];
                    adjacentVertices[1] = vertices[Vertex.NORTHWEST];
                }
                case Edge.NORTH -> {
                    adjacentVertices[0] = vertices[Vertex.NORTHWEST];
                    adjacentVertices[1] = vertices[Vertex.NORTHEAST];
                }
                case Edge.NORTHEAST -> {
                    adjacentVertices[0] = vertices[Vertex.NORTHEAST];
                    adjacentVertices[1] = vertices[Vertex.EAST];
                }
                case Edge.SOUTHEAST -> {
                    adjacentVertices[0] = vertices[Vertex.EAST];
                    adjacentVertices[1] = vertices[Vertex.SOUTHEAST];
                }
                case Edge.SOUTH -> {
                    adjacentVertices[0] = vertices[Vertex.SOUTHEAST];
                    adjacentVertices[1] = vertices[Vertex.SOUTHWEST];
                }
                case Edge.SOUTHWEST -> {
                    adjacentVertices[0] = vertices[Vertex.SOUTHWEST];
                    adjacentVertices[1] = vertices[Vertex.WEST];
                }
            }

            e.setAdjacentVertices(adjacentVertices);
        }
    }

    public void setAdjacentTilesToVertices() {
        for (int i = 0; i < vertices.length; i++) {
            Vertex v = vertices[i];
            Tile[] adjacentTilesToVertex = new Tile[3];

            switch (i) {
                case Vertex.NORTHWEST -> {
                    adjacentTilesToVertex[0] = adjacentTiles[Tile.NORTHWEST];
                    adjacentTilesToVertex[1] = adjacentTiles[Tile.NORTH];
                    adjacentTilesToVertex[2] = this;
                }
                case Vertex.NORTHEAST -> {
                    adjacentTilesToVertex[0] = adjacentTiles[Tile.NORTHEAST];
                    adjacentTilesToVertex[1] = adjacentTiles[Tile.NORTH];
                    adjacentTilesToVertex[2] = this;
                }
                case Vertex.EAST -> {
                    adjacentTilesToVertex[0] = adjacentTiles[Tile.NORTHEAST];
                    adjacentTilesToVertex[1] = adjacentTiles[Tile.SOUTHEAST];
                    adjacentTilesToVertex[2] = this;
                }
                case Vertex.SOUTHEAST -> {
                    adjacentTilesToVertex[0] = adjacentTiles[Tile.SOUTHEAST];
                    adjacentTilesToVertex[1] = adjacentTiles[Tile.SOUTH];
                    adjacentTilesToVertex[2] = this;
                }
                case Vertex.SOUTHWEST -> {
                    adjacentTilesToVertex[0] = adjacentTiles[Tile.SOUTHWEST];
                    adjacentTilesToVertex[1] = adjacentTiles[Tile.SOUTH];
                    adjacentTilesToVertex[2] = this;
                }
                case Vertex.WEST -> {
                    adjacentTilesToVertex[0] = adjacentTiles[Tile.SOUTHWEST];
                    adjacentTilesToVertex[1] = adjacentTiles[Tile.NORTHWEST];
                    adjacentTilesToVertex[2] = this;
                }
            }

            v.setAdjacentTiles(adjacentTilesToVertex);
        }
    }
    // endregion

    public void setEdge(Edge edge, int orientation) {
        edges[orientation] = edge;
    }

    public void setNumber(int number) {
        this.number = number;

        if (number > -1 && number != 7)
            tokenImage = Initialize.dicePatterns.get(number);
    }

    public void setVertex(Vertex vertex, int orientation) {
        vertices[orientation] = vertex;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    //endregion

    //region Getters
    public Tile getAdjacentTile(int orientation) {
        return adjacentTiles[orientation];
    }

    public Tile[] getAdjacentTiles() {
        return adjacentTiles;
    }

    public Vertex getVertex(int orientation) {
        return vertices[orientation];
    }

    public Edge getEdge(int orientation) {
        return edges[orientation];
    }

    public Edge[] getEdges() {
        return edges;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int getNumber() {
        return number;
    }

    public ResourceType getResource() {
        return resource;
    }

    public Location getLocation() {
        return location;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public ImagePattern getNumberPattern() {
        return tokenImage;
    }

    //endregion

    public String toString(){
        return resource.toString() +  " " + number;
    }

    public String printAdjacentTiles() {
        String s = "";

        for(int i = 0; i < 6; i++) {
            if (adjacentTiles[i] != null) {
                s += " " + adjacentTiles[i].getLocation().toString();
            } else {
                s += " null";
            }
        }

        return s;
    }
}
