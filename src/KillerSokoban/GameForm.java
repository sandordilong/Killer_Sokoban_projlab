package KillerSokoban;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * A játék grafikus megjelenítéséért felelős osztály.
 */
class GameForm extends JFrame {
    private JPanel MainPanel;
    private JPanel GamePanel;
    private JPanel MenuPanel;
    private JPanel ScorePanel;
    private JButton NewGameButton;
    private JButton StopButton;
    private JButton StartButton;
    private JLabel Player1ScoreLabel;
    private JLabel Player2ScoreLabel;
    private JLabel Player1PictureLabel;
    private JLabel Player2PictureLabel;
    private int winner = -1;

    /**
     * Konstruktor.
     * Beállítja a gombokat.
     * Beállítja az objektumok elhelyezését.
     * Beállítja a Listenereket.
     */
    GameForm() {

        this.setFocusable(true);
        this.requestFocus();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("assets" + File.separator + "player2.png").getImage());
        this.setTitle("Killer Sokoban");
        this.setSize(new Dimension(800, 650));
        MainPanel.setBackground(new Color(69, 69, 69));
        MenuPanel.setBackground(new Color(69, 69, 69));
        ScorePanel.setBackground(new Color(69, 69, 69));
        this.add(MainPanel);
        this.setBackground(new Color(69, 69, 69));
        this.setVisible(true);
        this.setResizable(false);

        Player1PictureLabel.setIcon(new ImageIcon("assets" + File.separator + "player1.png"));
        Player2PictureLabel.setIcon(new ImageIcon("assets" + File.separator + "player2.png"));

        ImageIcon newGameIcon = new ImageIcon("assets" + File.separator + "ng.png");
        NewGameButton.setIcon(newGameIcon);
        NewGameButton.setBorder(BorderFactory.createEmptyBorder());
        NewGameButton.setContentAreaFilled(false);
        ImageIcon StartIcon = new ImageIcon("assets" + File.separator + "start.png");
        StartButton.setIcon(StartIcon);
        StartButton.setBorder(BorderFactory.createEmptyBorder());
        StartButton.setContentAreaFilled(false);
        ImageIcon StopIcon = new ImageIcon("assets" + File.separator + "stop.png");
        StopButton.setIcon(StopIcon);
        StopButton.setBorder(BorderFactory.createEmptyBorder());
        StopButton.setContentAreaFilled(false);

        StartButton.addActionListener(e -> {
            Game.getInstance().startGame();
            requestFocus();
        });

        NewGameButton.addActionListener(e -> Game.getInstance().loadAndBuildMap("map.txt"));

        StopButton.addActionListener(e -> Game.getInstance().endGame());

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Game.getInstance().keyAction(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    /**
     * Újrarajzolja a pályát.
     */
    void rePaint() {
        Image dbImage;
        Graphics dbg;
        //buffer inicializálása
        dbImage = createImage(GamePanel.getSize().width, GamePanel.getSize().height);
        dbg = dbImage.getGraphics();
        //kepernyő tisztítása a háttérben
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, GamePanel.getSize().width, GamePanel.getSize().height);
        //újrarajzolás a háttérben
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                dbg.setColor(getForeground());
                dbg.drawImage(Game.getInstance().getFields()[i][j].draw(), j * 30, i * 30, null);

                if (Game.getInstance().getFields()[i][j].getLubricant() != null)
                    dbg.drawImage(Game.getInstance().getFields()[i][j].getLubricant().draw(), j * 30, i * 30, null);

                if (Game.getInstance().getFields()[i][j].getCurrentGameObject() != null)
                    dbg.drawImage(Game.getInstance().getFields()[i][j].getCurrentGameObject().draw(), j * 30, i * 30, null);

            }
        }
        //kepernyore rajzolas
        GamePanel.getGraphics().drawImage(dbImage, 0, 0, this);

        if (Game.getInstance().getPalyers().size() > 0) {
            if (Game.getInstance().getPalyers().size() == 2) {
                Player1ScoreLabel.setText(Integer.toString(Game.getInstance().getPalyers().get(0).getScore()));
                Player2ScoreLabel.setText(Integer.toString(Game.getInstance().getPalyers().get(1).getScore()));
            }
        }
    }

    /**
     * Ha vége van a játéknak, akkor ez a függvény írja ki a végeredményt.
     */
    void endGame() {
        ImageIcon icon = new ImageIcon("assets" + File.separator + "player" + (winner + 1) + ".png");
        if (winner == 0)
            JOptionPane.showMessageDialog(MainPanel, "Player 1 win!", "Winner", JOptionPane.INFORMATION_MESSAGE, icon);
        else if (winner == 1)
            JOptionPane.showMessageDialog(MainPanel, "Player 2 win!", "Winner", JOptionPane.INFORMATION_MESSAGE, icon);
        else
            JOptionPane.showMessageDialog(MainPanel, "Draw!", "Winner", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    /**
     * Beállítja a győztest.
     * 0 - kék player
     * 1 - sárga player
     * @param i
     */
    void setWinner(int i) {
        winner = i;
    }

}
