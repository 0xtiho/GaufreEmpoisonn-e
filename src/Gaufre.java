import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Gaufre implements Runnable {

    

    @Override
    public void run() {
        JFrame frame = new JFrame("Jeu de Gaufre");
                GaufreModel model = new GaufreModel(6, 7);
        GaufreView view = new GaufreView(model);
        GaufreController controller = new GaufreController(model, view);
                frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Gaufre());
    }
}
