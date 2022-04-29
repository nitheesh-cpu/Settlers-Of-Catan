package game.catan.simulation;

import game.catan.simulation.enums.ResourceType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Initialize {

    public static ImagePattern[] tilePatterns;
    public static ImagePattern waterPattern;
    //public static ImagePattern[] dicePatterns;
    public static HashMap<Integer, ImagePattern> dicePatterns;
    public static Map<ResourceType, Image> harborImages;
    public static Image[] diceImages;
    public static Image settlementHover;
    public static Image settlementBlank;


    public static void init() {
        settlementHover = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/PlayerResources/hover.jpg"));
        settlementBlank = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/PlayerResources/blank.png"));
        //hexagon tile patterns
        Image brick = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Tile/BrickTile.png"));
        Image desert = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Tile/DesertTileEXP.png"));
        Image lumber = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Tile/GrassTileExp.png"));
        Image sheep = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Tile/PlanesTileExp.png"));
        Image ore = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Tile/StoneTileExp.png"));
        Image wheat = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Tile/WheatTileEx.png"));
        Image water = new Image(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Tile/WaterTileExp.png"));
        ImagePattern brickPattern = new ImagePattern(brick, 63, 57, 130, 116, false);
        ImagePattern desertPattern = new ImagePattern(desert, 69, 62, 137, 125, false);
        ImagePattern lumberPattern = new ImagePattern(lumber, 64, 59, 130, 118, false);
        ImagePattern sheepPattern = new ImagePattern(sheep, 67, 57, 135, 116, false);
        ImagePattern orePattern = new ImagePattern(ore, 71, 57, 145, 116, false);
        ImagePattern wheatPattern = new ImagePattern(wheat, 67, 57, 135, 116, false);
        waterPattern = new ImagePattern(water, 67, 56, 135, 112, false);
        tilePatterns = new ImagePattern[]{lumberPattern, sheepPattern, wheatPattern, brickPattern, orePattern, desertPattern};

        //circle dice marker patterns
        Image two = new Image("game/catan/DiceMarkings/2.png");
        Image three = new Image("game/catan/DiceMarkings/3.png");
        Image four = new Image("game/catan/DiceMarkings/4.png");
        Image five = new Image("game/catan/DiceMarkings/5.png");
        Image six = new Image("game/catan/DiceMarkings/6.png");
        Image eight = new Image("game/catan/DiceMarkings/8.png");
        Image nine = new Image("game/catan/DiceMarkings/9.png");
        Image ten = new Image("game/catan/DiceMarkings/10.png");
        Image eleven = new Image("game/catan/DiceMarkings/11.png");
        Image twelve = new Image("game/catan/DiceMarkings/12.png");
        ImagePattern twoPattern = new ImagePattern(two, 31, 35, 60, 60, false);
        ImagePattern threePattern = new ImagePattern(three, 31, 35, 60, 60, false);
        ImagePattern fourPattern = new ImagePattern(four, 31, 35, 60, 60, false);
        ImagePattern fivePattern = new ImagePattern(five, 31, 35, 60, 60, false);
        ImagePattern sixPattern = new ImagePattern(six, 31, 35, 60, 60, false);
        ImagePattern eightPattern = new ImagePattern(eight, 31, 35, 60, 60, false);
        ImagePattern ninePattern = new ImagePattern(nine, 31, 35, 60, 60, false);
        ImagePattern tenPattern = new ImagePattern(ten, 31, 35, 60, 60, false);
        ImagePattern elevenPattern = new ImagePattern(eleven, 31, 35, 60, 60, false);
        ImagePattern twelvePattern = new ImagePattern(twelve, 31, 35, 60, 60, false);
        // dicePatterns = new ImagePattern[]{twoPattern, threePattern, fourPattern, fivePattern, sixPattern, eightPattern, ninePattern, tenPattern, elevenPattern, twelvePattern};
        dicePatterns = new HashMap<>();
        dicePatterns.put(2, twoPattern);
        dicePatterns.put(3, threePattern);
        dicePatterns.put(4, fourPattern);
        dicePatterns.put(5, fivePattern);
        dicePatterns.put(6, sixPattern);
        dicePatterns.put(8, eightPattern);
        dicePatterns.put(9, ninePattern);
        dicePatterns.put(10, tenPattern);
        dicePatterns.put(11, elevenPattern);
        dicePatterns.put(12, twelvePattern);

        //port images
        harborImages = Map.ofEntries(
                Map.entry(ResourceType.BRICK, new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Ports/Bricks.png")))),
                Map.entry(ResourceType.WOOL, new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Ports/Sheep.png")))),
                Map.entry(ResourceType.ORE, new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Ports/Stone.png")))),
                Map.entry(ResourceType.WHEAT, new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Ports/Wheat.png")))),
                Map.entry(ResourceType.WOOD, new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Ports/Wood.png")))),
                Map.entry(ResourceType.MISC, new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/Ports/Misc.png"))))
        );

        //dice Images
        diceImages = new Image[]{
                new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/DiceResources/1.png"))),
                new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/DiceResources/2.png"))),
                new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/DiceResources/3.png"))),
                new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/DiceResources/4.png"))),
                new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/DiceResources/5.png"))),
                new Image(Objects.requireNonNull(Initialize.class.getClassLoader().getResourceAsStream("game/catan/DiceResources/6.png"))),
        };
    }
}
