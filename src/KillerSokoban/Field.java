package KillerSokoban;

import java.util.HashMap;

/**
 * El tud tárolni egy GameObject-et. Ő valósítja meg az “egyszerű padlót”,
 * amin a Player-ek és a Box-ok tudnak mozogni. Ezen kívül belőle származik minden speciális mező.
 */
public class Field extends Drawable {

    /**
     * Eltárolja, hogy épp ki áll rajta.
     */
    private GameObject currentGameObj;

    /**
     * Ebben a tömbben tárolja a Field a szomszédes Field-eket irányuk szerint.
     */
    private HashMap<Direction, Field> neighbours = new HashMap<>();

    /**
     * Visszadja , hogy a paraméterként megkapott irányban milyen szomszédja van az adott mezőnek
     *
     * @param d Irány, amely felé vizsgálunk
     * @return a szomszéd Field tipusával
     */
    Field getNeighbour(Direction d) {
        return neighbours.get(d);
    }

    /**
     * Beállítja a paraméterként kapott Field-et a paraméterként kapott irányba a neighbours tagváltozóba.
     *
     * @param d Melyik irányban
     * @param f Melyik Field
     */
    void setNeighbour(Direction d, Field f) {
        neighbours.put(d, f);
    }

    /**
     * Beallitja a szomszedokat egy hasmapból.
     *
     * @param _neighbours
     */
    void setNeighbours(HashMap<Direction, Field> _neighbours) {
        this.neighbours = _neighbours;
        for (Direction key : neighbours.keySet()) {
            if (key == Direction.Right) {
                this.neighbours.get(key).setNeighbour(Direction.Left, this);
            } else if (key == Direction.Left) {
                this.neighbours.get(key).setNeighbour(Direction.Right, this);
            } else if (key == Direction.Up) {
                this.neighbours.get(key).setNeighbour(Direction.Down, this);
            } else { //down
                this.neighbours.get(key).setNeighbour(Direction.Up, this);
            }
        }
    }

    /**
     * Lekéri a mező szomszédjait egy hasmapba.
     *
     * @return
     */
    HashMap<Direction, Field> getNeighbours() {
        return neighbours;
    }

    /**
     * Beállítja a paraméterként kaapott GameObjectet a rajta lévőnek.
     *
     * @param go GameObject,amely a Fielden van
     */
    void setCurrentGameObj(GameObject go) {
        currentGameObj = go;
    }

    GameObject getCurrentGameObject() {
        return currentGameObj;
    }

    /**
     * A mező konstruktora, a tagváltozókat null-ra állítja.
     */
    public Field() {
        fileName = "field.png";
        currentGameObj = null;
        lubricant = null;
    }

    /**
     * Egy objektum rálép a mezőre. Két eset van: üres a mező, vagy pedig van egy objektum a mezőn.
     *
     * @param go az objektum ami a mezőre rálép
     * @return sikerült e a lépés
     */
    public boolean stepOn(GameObject go) {
        if (currentGameObj == null) { //A mező üres
            Field goField = go.getCurrentField();
            goField.remove(go);
            accept(go);
            return true;
        } else {
            Field goField = go.getCurrentField();
            go.blockedBy(currentGameObj);
            if (currentGameObj == null) {
                goField.remove(go);
                accept(go);
                return true;
            }
        }
        return false;
    }


    /**
     * Játékos akar lépni a mezőre.
     *
     * @param player a játékos
     */
    public boolean stepOn(Player player) {
        return stepOn((GameObject) player);
    }

    /**
     * Doboz akar lépni a mezőre.
     *
     * @param box a doboz
     */
    public boolean stepOn(Box box) {
        return stepOn((GameObject) box);
    }

    /**
     * Beállítja az új objektumot a mezőnek.
     *
     * @param newGameObject az új objektum
     */
    private void accept(GameObject newGameObject) {
        currentGameObj = newGameObject;
        newGameObject.setCurrentField(this);
    }

    /**
     * Kitörli az objektumot a mezőről.
     *
     * @param go a kitörlendő objektum
     */
    public void remove(GameObject go) {
        if (currentGameObj == go) {
            currentGameObj = null;
            go.setCurrentField(null);
        }
    }

    /**
     * Visszaadja a mező súrlódási együtthatóját, ami a mezőn lévő kenőanyagtól függ.
     *
     * @return a súrlódási együttható
     */
    float getFrictionForce() {
        float lubricantCoefficient = 1.0f;
        if (lubricant != null) {
            lubricantCoefficient = lubricant.getFriction();
        }
        return lubricantCoefficient * currentGameObj.getMass();
    }

    private Lubricant lubricant;

    /**
     * Beállítja a kenőanyagot.
     *
     * @param l az új kenőanyag
     */
    void setLubricant(Lubricant l) {
        lubricant = l;
    }

    /**
     * Visszaadja, a kenőanyagot, ami a padlón van.
     * @return a kenyőanyag
     */
    Lubricant getLubricant() {
        return lubricant;
    }
}
