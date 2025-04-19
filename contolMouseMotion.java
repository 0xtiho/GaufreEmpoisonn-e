import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class contolMouseMotion implements MouseMotionListener {
    private Gauffre gauffre;
    private Vue vue;

    public contolMouseMotion(Gauffre g, Vue v) {
        this.gauffre = g;
        this.vue = v;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        gauffre.setMouseXY(new Point(x,y));
        vue.redessine();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
