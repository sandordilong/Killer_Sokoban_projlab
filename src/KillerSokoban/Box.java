package KillerSokoban;

/**
 * A játékosok ezeket tologatják, ha a helyükre érnek, onnantól oszlopként funkcionálnak, ha egy lyukba esnek, akkor eltűnnek, ha egy kapcsolón állnak, kinyitnak egy SwitchField-et.
 */
public class Box extends GameObject {

    /**
     * A doboz konstruktora. Tagváltozókat inicalizál.
     */
    Box() {
        fileName = "box.png";
        mass = 10;
        forceApplied = 0; //FORCE APPLIED NULLÁZÁSA
    }

    /**
     * Ha valaki el akarja tolni a dobozt, akkor hívódik ez a fuggvény és megkisérli őt eltolni.
     * @param d a tolás iránya
     * @param force a tolás ereje
     */
    @Override
    public void push(Direction d, float force) {
        currentDirection = d;
        float friction = currentField.getFrictionForce();
        if (friction <= force) {
            forceApplied = force - friction;
            Field nextField = currentField.getNeighbour(d);
            nextField.stepOn(this);
        }
        forceApplied = 0;
    }
}
