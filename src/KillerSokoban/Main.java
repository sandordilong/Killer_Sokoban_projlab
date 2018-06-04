package KillerSokoban;

/**
 * A Main.
 * Induláskor ez indul.
 */
public class Main {

    /**
     * Elindítja a programot.
     * @param args parancssori argumentumok
     */
    public static void main(String[] args) {
        GameForm gf = new GameForm();
        Game.getInstance().setGameForm(gf);
        Game.getInstance().loadAndBuildMap("map.txt");
    }
}
