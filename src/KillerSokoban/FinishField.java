package KillerSokoban;

/**
 * Ezek a Field-ek képesek arra, hogy befogadjanak egy Box-ot. Azaz, ha egy Player egy ilyen típusú Field-re tol egy Box-ot, azzal pontot szerez.
 * Amikor egy Box egy ilyen Field-re érkezik, akkor onnan már nem tud elmozdulni, oszlopként fog funkcionálni,
 * mert a FinishField, nem engedi, hogy bárki más jöjjön be a már meglévő doboza helyére.
 */
public class FinishField extends Field {

    /**
     * A FinishField állapota attól függően, hogy van-e rajta doboz (Occupied), vagy nincs(Free).
     */
    private FinishFieldState state = FinishFieldState.Free;

    /**
     * A mező konstruktora, a tagváltozókat null-ra állítja.
     */
    FinishField() {
        fileName = "finishfield.png";
    }

    /**
     * Ha a mező szabad, akkor elfogadja a Box-ot, majd értesíti a Game-t, hogy pontszerzés történt,
     * a state-et Occupied-ra állítja.
     *
     * @param box A Box, amelyet a FinishField-re akarunk tolni
     * @return Ha rá lehet tolni: ture, különben false
     */
    @Override
    public boolean stepOn(Box box) {
        boolean success;
        if (state == FinishFieldState.Free) {
            super.stepOn(box);
            box.fileName = "celbaertdoboz.png";
            Game.getInstance().scored();
            state = FinishFieldState.Occupied;
            success = true;
        } else {
            // OCCUPIED: NEM LÉP RÁ
            success = false;
        }
        return success;
    }

    /**
     * Ha rá akar lépni valaki, akkor ez hívódik meg.
     * @param player a játékos
     * @return sikerült-e a lépés
     */
    @Override
    public boolean stepOn(Player player) {
        boolean success;
        if (state == FinishFieldState.Free) {
            success = super.stepOn(player);
        } else {
            success = false;
        }
        return success;
    }
}
