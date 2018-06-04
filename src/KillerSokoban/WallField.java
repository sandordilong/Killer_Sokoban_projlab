package KillerSokoban;

/**
 * Nem lehet elmozdítani semmivel.
 * Előre meghatározott helyeken állnak, azért vannak, hogy akadályozzák a mozgást.
 * Ezek jelölik ki a pálya széleit, valamint az oszlopokat is ezek valósítják meg. Nem lehet rálépni.
 * Amennyiben egy Field valamilyen okból a továbbiakban oszlopként fog funcionálni, az azt jelenti, hogy ilyen WallField lesz belőle.
 */
public class WallField extends Field {
    /**
     * A mező konstruktora, a tagváltozókat null-ra állítja.
     */
    WallField() {
        fileName = "wallfield.png";
    }

    /**
     * Hiába próbál egy objektum rálépni a mezőre, semmi se fog történni.
     *
     * @param go az objektum ami a mezőre rálép
     */
    @Override
    public boolean stepOn(GameObject go) {
        return false;
    }
}
