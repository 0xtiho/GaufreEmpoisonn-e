package control;

import model.Gauffre;
import model.IA;
import view.Vue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class controlSouris implements MouseListener {
    private Gauffre gauffre;
    private Vue vue;
    private IA ia;

    public controlSouris(Gauffre g,Vue v,IA i) {
        this.gauffre = g;
        this.vue = v;
        this.ia = i;
    }

    @Override
    public void mouseClicked(MouseEvent e){ //le cic de souris pris en compte ssi humain
        Point p=vue.pixToCord(new Point(e.getX(),e.getY()));
        if (p.x==0 && p.y==0 ){
            gauffre.Perdu=1;
        }



        gauffre.manger(p.x,p.y,true);
        vue.redessine();

        //desactiver le listener

        if (ia!=null && gauffre.tour==2) {
            Timer timer = new Timer(1000, evt -> {
                vue.removeMouseListener(this);

                ia.jouerCoup();
                vue.redessine();
                vue.addMouseListener(this);
            });
            timer.setRepeats(false);
            timer.start();
        }

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