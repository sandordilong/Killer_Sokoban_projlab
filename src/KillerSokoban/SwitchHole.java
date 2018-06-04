package KillerSokoban;

/**
 * Két állapota van, vagy Hole-ként vagy sima Field-ként funkcionál, attól függően, hogy épp van-e Box a hozzá tartozó SwitchField-en.
 */
public class SwitchHole extends Field {

    /**
     * Tárolja, hogy épp nyitva vagy csukva van-e a SwitchHole, azaz Hole-ként vagy Field-ként funkcionál.
     */
    private SwitchHoleState state;

    /**
     * A mező konstruktora, a tagváltozókat null-ra állítja.
     */
    SwitchHole() {
        if (state == SwitchHoleState.Open) {
            fileName = "holefield.png";
        } else {
            fileName = "field.png";
        }
    }

    /**
     * A state attribútumot Open-re állítja és ha épp tárol egy GamaObjectet, akkor kitörli azt
     * (meghívja rajta a die() metódusát a GameObject-nek).
     */
    void open() {
        state = SwitchHoleState.Open;
        fileName = "holefield.png";
        if ( getCurrentGameObject() != null ){
            getCurrentGameObject().die();
        }
    }

    /**
     * A state attribútumot Close-ra állítja, innentől Field-ként fog funkcionálni a mező.
     */
    void close() {
        state = SwitchHoleState.Close;
        fileName = "field.png";
    }

    /**
     * Ha valaki rá akar lépni a SwitchHole-ra, akkor ez hívódik.
     * Ha zárva, akkor nincs semmi.
     * Ha nyitva, akkor halál.
     * @param go az objektum ami a mezőre rálép
     * @return
     */
    @Override
    public boolean stepOn(GameObject go) {
        boolean success = false;
        if (state == SwitchHoleState.Open) {
            go.die();
        } else {
            success = super.stepOn(go);
        }
        return success;
    }
}
