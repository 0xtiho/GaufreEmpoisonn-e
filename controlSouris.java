import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class controlSouris implements MouseListener {
    private Gauffre gauffre;
    private Vue vue;
    private Jeu jeu; //dans le but dacceder a la variable tourJoueur

    public controlSouris(Gauffre g,Vue v, Jeu jeu){
        this.gauffre = g;
        this.vue = v;
        this.jeu = jeu;
    }

    @Override
    public void mouseClicked(MouseEvent e){ //le cic de souris pris en compte ssi humain
        if (jeu.isTourJoueur()) { //si tour du joueur
            Point p=vue.pixToCord(new Point(e.getX(),e.getY()));
            if (p.x==0 && p.y==0 ){
                gauffre.Perdu=1;
            }
            gauffre.manger(p.x,p.y);
            if (!(gauffre.peut_manger(0,1)||gauffre.peut_manger(1,0) )){
                gauffre.Perdu=1;
            }

            vue.repaint();
            // Passe le tour à l'IA
            if (gauffre.Perdu == 0) {
                jeu.setTourJoueur(false);
                jeu.jouerTourIA();
            }
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