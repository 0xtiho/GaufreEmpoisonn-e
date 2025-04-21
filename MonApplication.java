import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MonApplication implements Runnable {
    static Image charge(String nom) {
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            return ImageIO.read(cl.getResourceAsStream(nom));
        } catch (Exception e) {
            System.err.println("Erreur imprévue");
            System.exit(1);
            return null;
        }
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Miam Miam");
        Jeu jeu = new Jeu(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new MonApplication());
    }
}
