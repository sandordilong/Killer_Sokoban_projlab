package KillerSokoban;

/**
 * Ha egy GameObject érkezik rá, akkor  azt ha játékos, akkor megöli, ha doboz, eltünteti.
 */
public class HoleField extends Field {

    /**
     * Konstruktor.
     * Beállítja a HoleField képét.
     */
    HoleField() {
        fileName = "holefield.png";
    }

    /**
     * Ha valaki rá akar lépni, akkor meghal.
     * @param go az objektum ami a mezőre rálép
     * @return true
     */
    @Override
    public boolean stepOn(GameObject go) {
        go.die();
        return true;
    }
}
