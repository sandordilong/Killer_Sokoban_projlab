package KillerSokoban;

/**
 * Egy játékos egy Player-t irányít.
 * A Player képes Box-okat és másik Player-eket eltolni, valamint beleesni egy Hole-ba vagy egy nyitott SwitchField-be.
 * A Player minden helyére tolt doboz után pontot kap, az a Player nyer, akinek több pontja van.
 */
public class Player extends GameObject {

    /**
     * A Player pontszáma.
     */
    private int score;

    /**
     * Egyel megnöveli a játékos pontszámát.
     */
    void incrementScore() {
        score += 1000;
    }

    /**
     * A játékos konstruktora, inicializálja a tagváltozókat.
     */
    Player(int i) {
        fileName = "player" + i + "_30.png";
        score = 0;
        mass = 10;
        forceApplied = 0;
    }

    /**
     * A Player ezen függvény meghívásával próbál mozogni a paraméterként kapott irányba.
     *
     * @param d ebbe az irányba próbál elmozdulni
     */
    void move(Direction d) {
        currentDirection = d;
        forceApplied = 40;
        Field nextField = currentField.getNeighbour(d);
        nextField.stepOn(this);
        forceApplied = 0;
    }

    /**
     * A játékos mezőjére mézet helyez.
     */
    void setHoney() {
        currentField.setLubricant(new Honey());
    }

    /**
     * A játékos mezőjére olajat helyez.
     */
    void setOil() {
        currentField.setLubricant(new Oil());
    }

    /**
     * Akkor hívódik, amikor meghal a játékos.
     * Szól a Game-nek, hogy halál történt.
     */
    @Override
    public void die() {
        super.die();
        Game.getInstance().removePlayer(this);
    }

    /**
     * Egy objektum eltolja a játékost.
     *
     * @param d
     * @param force
     */
    @Override
    public void push(Direction d, float force) {
        currentDirection = d;
        float friction = currentField.getFrictionForce();
        if (friction <= force) {
            forceApplied = force - friction;
            Field nextField = currentField.getNeighbour(d);
            if (!nextField.stepOn(this))
                die();
        }
    }

    /**
     * Visszaadja a játékos pontszámát.
     * @return a pontszám
     */
    int getScore() {
        return score;
    }
}
