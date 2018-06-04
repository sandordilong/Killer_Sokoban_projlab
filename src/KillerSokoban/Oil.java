package KillerSokoban;

/**
 * Olaj.
 * Csúszósabbá teszi a padlót.
 */
public class Oil extends Lubricant {

    /**
     * Konstruktor.
     * Beállítja az olaj képét.
     */
    Oil() {
        fileName = "oilfield_empty.png";
    }

    /**
     * Visszaadja az olaj csúszósságát.
     * @return a csúszósság
     */
    @Override
    public float getFriction() {
        return 0.5f;
    }
}
