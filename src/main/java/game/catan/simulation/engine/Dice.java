package engine;

import java.util.Random;

public class Dice {
    private static Random random;
    private static int faceOne = 0;
    private static int faceTwo = 0;

    public static void init(int seed) {
        random = new Random(seed);
    }

    public static int roll() {
        faceOne = random.nextInt(6) + 1;
        faceTwo = random.nextInt(6) + 1;
        return faceOne + faceTwo;
    }

    public static int getFaceOne() {
        return faceOne;
    }

    public static int getFaceTwo() {
        return faceTwo;
    }

    public static Random getRef() {
        return random;
    }
}
