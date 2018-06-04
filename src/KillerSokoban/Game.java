package KillerSokoban;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Ez az osztaly felelos a jatek kezeleseert es mukodeseert.
 */
class Game {

    /**
     * Ebben a listaban tarolja a jatekosokat.
     */
    private ArrayList<Player> players;
    private Field fields[][];
    private boolean start;
    private boolean scored;
    private GameForm gameForm;

    /**
     * Singleton osztály itt taroljuk el az egyetlen peldanyt az osztalybol.
     */
    private static Game ourInstance = new Game();

    /**
     * Visszaadja az osztalyunk egyetlen peldanyat.
     *
     * @return a peldany
     */
    static Game getInstance() {
        return ourInstance;
    }

    void setGameForm(GameForm _gameForm) {
        gameForm = _gameForm;
    }

    ArrayList<Player> getPalyers() {
        return players;
    }

    Field[][] getFields() {
        return fields;
    }

    /**
     * Konstruktor
     */
    private Game() {
        players = new ArrayList<>();
        fields = new Field[20][20];
        start = false;
        scored = false;
    }

    /**
     * A pálya betolteseert es felepiteseert felel.
     * @param filename a file neve amibol beolvassa a palyat.
     */
    void loadAndBuildMap(String filename) {
        BuildMap tm = new BuildMap(filename);
        try {
            tm.mukodj();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fields = tm.getMap();
        players = tm.getPlayers();
        gameForm.rePaint();
    }

    /**
     * A FinishField-ek hivjak, ha egy dobozt tolnak rajuk, ekkor a jatekos pontot szerez.
     */
    void scored() {
        scored = true;
    }

    /**
     * Elinditja a jatekot.
     */
    void startGame() {
        start = true;
        gameForm.rePaint();
    }

    /**
     * Megallitja a jatekot
     */
    void endGame() {
        start = false;

        if (players.size() > 1) {
            gameForm.setWinner(players.get(0).getScore() > players.get(1).getScore() ? 0 : players.get(0).getScore() < players.get(1).getScore() ? 1 : 2);
        }

        if (gameForm != null) {
            gameForm.endGame();
        }
    }

    /**
     * Eltavolitja a parameterkent kapott jatekost a jatekosok listaja kozul, ha csak egy jatekos maradt akkor veget vet a jateknak azaz meghivja az endGame() fuggvenyt.
     *
     * @param p
     */
    void removePlayer(Player p) {
        gameForm.setWinner(players.get(0).getScore() > players.get(1).getScore() ? 0 : players.get(0).getScore() < players.get(1).getScore() ? 1 : Math.abs(players.indexOf(p) - 1));

        players.remove(p);
        start = false; //mivel mar csak egy jatekos maradt
    }

    /**
     * Kezeli a felhasznalok billentyunyomasait.
     */
    void keyAction(KeyEvent e) {

        if (!start)
            return;

        /**
         * WASD & EQ --> player1
         * W - előre
         * A - balra
         * S - le
         * D - jobbra
         * E - méz
         * Q - olaj
         *
         * IJKL & UO --> player2
         * I - előre
         * J - balra
         * K - le
         * L - jobbra
         * O - méz
         * U - olaj
         */

        switch (e.getKeyChar()) {

            //player0
            case 'w': {
                players.get(0).move(Direction.Up);
                break;
            }
            case 'a': {
                players.get(0).move(Direction.Left);
                break;
            }
            case 's': {
                players.get(0).move(Direction.Down);
                break;
            }
            case 'd': {
                players.get(0).move(Direction.Right);
                break;
            }
            case 'q': {
                players.get(0).setOil();
                break;
            }
            case 'e': {
                players.get(0).setHoney();
                break;
            }

            //player1
            case 'i': {
                players.get(1).move(Direction.Up);
                break;
            }
            case 'j': {
                players.get(1).move(Direction.Left);
                break;
            }
            case 'k': {
                players.get(1).move(Direction.Down);
                break;
            }
            case 'l': {
                players.get(1).move(Direction.Right);
                break;
            }
            case 'u': {
                players.get(1).setOil();
                break;
            }
            case 'o': {
                players.get(1).setHoney();
                break;
            }

            default: {
                System.out.println("NE\n");
            }

        }

        if (scored) {
            if (e.getKeyChar() >= 'i' && e.getKeyChar() <= 'l') {
                players.get(1).incrementScore();
            } else {
                players.get(0).incrementScore();
            }
            scored = false;
        }

        if (gameForm != null)
            gameForm.rePaint();

        if (!start) {
            endGame();
        }
    }
}
