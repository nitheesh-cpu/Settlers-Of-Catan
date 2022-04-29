package game.catan.simulation;

import java.util.Scanner;

public class GameRunner {
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numPlayers = sc.nextInt();
        System.out.print("Enter game seed: ");
        int seed = sc.nextInt();
        sc.nextLine();

        // new GameState(sc, numPlayers, seed);
    }
}