package game.catan.simulation;

import game.catan.simulation.buildings.Road;
import game.catan.simulation.buildings.Structure;
import game.catan.simulation.cards.DevelopmentCard;
import game.catan.simulation.enums.Color;
import game.catan.simulation.enums.DevelopmentCardType;
import game.catan.simulation.helper.Edge;
import game.catan.simulation.helper.Vertex;

import java.util.ArrayList;
import java.util.HashSet;

public class Player {
    private static int number = 1;

    private int id;
    private Color color;
    private Stockpile stockpile;

    private ArrayList<DevelopmentCard> developmentCards;
    private ArrayList<Structure> structures;
    private ArrayList<Road> roads;

    private ArrayList<Harbor> harbors;

    private int numOfKnights;
    private int lengthOfLongestRoad;
    private int victoryPoints;

    public Player(Color color) {
        this.id = number++;
        this.color = color;
        stockpile = new Stockpile();

        developmentCards = new ArrayList<>();
        structures = new ArrayList<>();
        roads = new ArrayList<>();

        harbors = new ArrayList<>();

        numOfKnights = 0;
        lengthOfLongestRoad = 0;
        victoryPoints = 0;
    }

    // TODO: remember to add victory points
    public void addStructure(Structure structure) {
        structures.add(structure);

        // Update harbors player can trade with
        outer:
        for (Harbor harbor : Board.getHarbors()) {
            if (harbors.contains(harbor)) continue;

            for (Vertex harborVertex : harbor.getVertices()) {
                if (harborVertex.equals(structure.getVertex())) {
                    harbors.add(harbor);
                    break outer;
                }
            }
        }
    }

    public void addRoad(Road road) {
        roads.add(road);

        longestRoad();
    }

    public void addDevelopmentCard(DevelopmentCard card) {
        developmentCards.add(card);
    }

    public void useDevelopmentCard(DevelopmentCardType type) {
        // TODO: need to implement

        if (!hasTypeOfDevelopmentCard(type)) return;
    }

//    // specifically for progress cards
//    public void useDevelopmentCard(DevelopmentCardType type, String name) {
//        if (type != DevelopmentCardType.PROGRESS) {
//            useDevelopmentCard(type);
//            return;
//        }
//
//        if (!hasTypeOfDevelopmentCard(name)) return;
//
//        switch (name.toUpperCase()) {
//            case "MONOPOLY":
//                Board.
//                break;
//            case "YEAR OF PLENTY":
//                break;
//            case "ROAD BUILDING":
//                break;
//
//        }
//    }

    public int longestRoad() {
        ArrayList<Edge> endpointEdges = getEndpointEdges();

        // use any edge if no endpoint edges found
        if (endpointEdges.size() == 0 && !roads.isEmpty()) endpointEdges.add(roads.get(0).getEdge());

        int longestRoad = 0;

        for (Edge edge: endpointEdges) {
            Vertex[] adjacentVertices = edge.getAdjacentVertices(); // get the two vertices of the edge

            for (Vertex vertex: adjacentVertices) {
                Edge[] adjacentEdges = vertex.getAdjacentEdges(); // get the edges adjacent to the vertex

                // branch out from all edges adjacent to the vertex to find the longest road
                for (Edge adjacentEdge: adjacentEdges) {
                    longestRoad = Math.max(longestRoad, longestRoad(vertex, adjacentEdge, new HashSet<>()));
                }
            }
        }

        if (longestRoad > lengthOfLongestRoad) {
            lengthOfLongestRoad = longestRoad;
        }

        return longestRoad;
    }

    private int longestRoad(Vertex startingVertex, Edge edge, HashSet<Road> roadsVisited) {
        if (edge == null || edge.getRoad() == null || roadsVisited.contains(edge.getRoad()) || !edge.getRoad().getOwner().equals(this)) return 0;

        roadsVisited.add(edge.getRoad());

        // get the vertex not equal to the startingVertex
        Vertex otherVertex = edge.getAdjacentVertices()[0].equals(startingVertex) ? edge.getAdjacentVertices()[1] : edge.getAdjacentVertices()[0];

        return 1 + Math.max(longestRoad(otherVertex, otherVertex.getAdjacentEdges()[0], new HashSet<>(roadsVisited)), Math.max(longestRoad(otherVertex, otherVertex.getAdjacentEdges()[1], new HashSet<>(roadsVisited)), longestRoad(otherVertex, otherVertex.getAdjacentEdges()[2], new HashSet<>(roadsVisited))));
    }

    // Finds all edges that only connect to one road that is owned by this player
    private ArrayList<Edge> getEndpointEdges() {
        ArrayList<Edge> endpointEdges = new ArrayList<>();

        for (Road road: roads) {
            Edge edge = road.getEdge();
            Vertex[] adjacentVertices = edge.getAdjacentVertices();

            // loop through each road's vertices
            for (Vertex vertex: adjacentVertices) {
                Edge[] vertexEdges = vertex.getAdjacentEdges();
                ArrayList<Edge> otherEdges = new ArrayList<>();

                // loop through all the adjacent edges of the vertex
                // find which edges are not the same as the road
                for (Edge vertexEdge: vertexEdges) {
                    if (vertexEdge != null && vertexEdge.equals(edge)) continue;
                    otherEdges.add(vertexEdge);
                }

                // loop through the other edges not the same as the road's edge
                // if the edge is null or the road is not the same as the edge's owner, add the edge to the list
                boolean flag = true;
                for (Edge otherEdge: otherEdges) {
                    if (otherEdge != null && otherEdge.getRoad() != null && otherEdge.getRoad().getOwner().equals(this)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) endpointEdges.add(road.getEdge());
            }
        }

        return endpointEdges;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public int getLongestRoad() {
        return lengthOfLongestRoad;
    }

    public ArrayList<Harbor> getHarbors() {
        return harbors;
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }

    public boolean hasTypeOfDevelopmentCard(DevelopmentCardType type) {
        for (DevelopmentCard card: developmentCards) {
            if (card.getType().equals(type)) return true;
        }

        return false;
    }

    public boolean hasTypeOfDevelopmentCard(String name) {
        for (DevelopmentCard card: developmentCards) {
            if (card.getName().equalsIgnoreCase(name)) return true;
        }

        return false;
    }

    public Stockpile getStockpile() {
        return stockpile;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Player " + id + ": " + color;
    }
}