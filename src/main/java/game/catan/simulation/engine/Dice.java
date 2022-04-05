package game.catan.simulation.engine;

public class Dice {
    private int faceValue1;
    private int faceValue2;

    private final int MAX = 6;

    public Dice(int fv1, int fv2) {
        faceValue1 = fv1;
        faceValue2 = fv2;
    }

    public int getFaceValue1() {
        faceValue1 = (int)(Math.random()*MAX) + 1;
        return faceValue1;
    }

    public int getFaceValue2() {
        faceValue2 = (int)(Math.random()*MAX) + 1;
        return faceValue2;
    }

    public int getTotal() {
        return faceValue1 + faceValue2;
    }




}
