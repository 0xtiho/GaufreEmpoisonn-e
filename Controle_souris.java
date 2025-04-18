import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Controle_souris implements MouseMotionListener {
    private Gauffre gauffre;
    private Vue vue;

    public Controle_souris(Gauffre g, Vue v) {
        this.gauffre = g;
        this.vue = v;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        gauffre.setMouseXY(new Point(x,y));
        vue.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
