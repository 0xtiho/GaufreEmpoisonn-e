import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Gaufre implements Runnable{

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
        JFrame frame = new JFrame("Puissance 4" );
        MonApplication.demarre(frame, 6, 7);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Gaufre());
    }
}
