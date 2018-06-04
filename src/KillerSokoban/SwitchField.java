package KillerSokoban;

/**
 * Sima Field-ként funkcionál, de ha egy Box van rajta, akkor meghívja a hozzá tartozó SwitchHole-on az Open() metódust,
 * ezzel azt éri el, hogy a SwitchHole Hole-ként fog funkcionálni.
 */
public class SwitchField extends Field {

    /**
     * A SwitchField-hez tartozó SwitchHole, ezt irányítja. Lehet open vagy closed
     */
    private SwitchHole switchHole;

    /**
     * Beállítja a paraméterként kapott állapotot a switchHole-nak.
     *
     * @param sh
     */
    SwitchField(SwitchHole sh) {
        fileName = "switchfieeld.png";
        switchHole = sh;
    }

    /**
     * Mikor egy doboz érkezik rá, akkor fog meghívódni ez a metódus. Ha rálépett, akkor kinyitja a lyukat.
     *
     * @param b A Box amelyet szeretnénk a Field-re tolni
     */
    @Override
    public boolean stepOn(Box b) {
        boolean success = super.stepOn(b);
        //Ez azt jelenti hogy sikerült rálépnie.
        if (success) {
            switchHole.open();
        }
        return success;
    }

    /**
     * Eltávolítja az addig rajtalevő Box-ot és meghívja a SwitchField-hez tartozó SwitchHole-on a close() metódust, ezzel bezárva azt.
     *
     * @param b
     */
    @Override
    public void remove(GameObject b) {
        super.remove(b);
        switchHole.close();
    }
}
