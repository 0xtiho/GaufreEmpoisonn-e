import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class controlSouris implements MouseListener {
    private Gauffre gauffre;
    private Vue vue;

    public controlSouris(Gauffre g,Vue v){
        this.gauffre = g;
        this.vue = v;
    }

    @Override
    public void mouseClicked(MouseEvent e){ //le cic de souris pris en compte ssi humain
            Point p=vue.pixToCord(new Point(e.getX(),e.getY()));
            if (p.x==0 && p.y==0 ){
                gauffre.Perdu=1;
            }



            gauffre.manger(p.x,p.y);
            vue.repaint();
    }
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}

}