package KillerSokoban;

/**
 * A Player és a Box közös absztrakt ősosztálya.
 */
public abstract class GameObject extends Drawable {

    /**
     * Az aktuális Field, amely a gameObject-et tartalmazza.
     */
    Field currentField;

    /**
     * A GameObject aktuális haladási iránya.
     */
    Direction currentDirection;

    /**
     * Visszaadja az aktuális Field-jét a GameObject-nek.
     *
     * @return aktuális Field
     */
    Field getCurrentField() {
        return currentField;
    }

    /**
     * Beállítja az aktuális Field-jét a GameObject-nek.
     *
     * @param f az aktuális Field
     */
    void setCurrentField(Field f) {
        currentField = f;
    }

    float mass;
    float forceApplied = 0;

    /**
     * Ha valaki el meg akarja lökni az objektumot, akkor ez hívódik meg.
     * @param d a lökés iránya
     * @param force a lökés ereje
     */
    public abstract void push(Direction d, float force);

    /**
     * Visszaadja az objektum súlyát.
     * @return a súly
     */
    float getMass() {
        return mass;
    }

    /**
     * Ha el akar lépni egy GameObject egy adott irányban, de ott áll egy másik, akkor ez a függvény hívódik meg az ellépni kívánó GameObject-en.
     *
     * @param g Az útban álló GameObject.
     */
    void blockedBy(GameObject g) {
        g.push(currentDirection, forceApplied);
    }

    /**
     * Ha egy Gameobject lyukba esik, vagy WallField-nél meghal, akkor ez a függvény kitörli őt a pályáról és értesíti a Game-et az esetről.
     */
    public void die() {
        currentField.remove(this);
    }
}
