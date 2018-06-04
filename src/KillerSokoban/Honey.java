package KillerSokoban;

public class Honey extends Lubricant {

    /**
     * Beállítja a méz képét.
     */
    Honey() {
        fileName = "honeyfield_empty.png";
    }

    /**
     * Visszaadja az anyag ragadósságát.
     * @return a ragadósság
     */
    @Override
    public float getFriction() {
        return 2.0f;
    }
}
