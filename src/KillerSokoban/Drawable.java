package KillerSokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Minden olyan osztálynak az őse, amit ki lehet rajzolni.
 */
public class Drawable {
    protected String fileName;

    /**
     * Visszaadja az objektum képét.
     * @return a kép
     */
    BufferedImage draw() {
        ImageIcon imageIcon = new ImageIcon( "assets" + File.separator + fileName );
        BufferedImage bi = new BufferedImage(
                imageIcon.getIconWidth(),
                imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        imageIcon.paintIcon(null, g, 0, 0);
        g.dispose();
        return bi;
    }
}
