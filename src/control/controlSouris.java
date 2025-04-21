package control;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.Gauffre;
import model.Jeu;
import view.Vue;

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
            
            // Passe le tour au joueur suivant seulement si la partie n'est pas terminée
            if (gauffre.Perdu == 0) {
                jeu.setTourJoueur(false); // Soit à l'IA, soit au joueur 2
                
                if (jeu.isAiActive()) {
                    // Mode IA: déclencher le tour de l'IA
                    jeu.jouerTourIA();
                }
                // Sinon c'est le tour du joueur 2, on attend son clic
            }
        } else if (!jeu.isAiActive()) {
            // En mode 2 joueurs, gérer le clic du joueur 2
            Point p=vue.pixToCord(new Point(e.getX(),e.getY()));
            if (p.x==0 && p.y==0 ){
                gauffre.Perdu=1;
            }
            gauffre.manger(p.x,p.y);
            if (!(gauffre.peut_manger(0,1)||gauffre.peut_manger(1,0) )){
                gauffre.Perdu=1;
            }

            vue.repaint();
            
            // Repasser le tour au joueur 1 si la partie continue
            if (gauffre.Perdu == 0) {
                jeu.setTourJoueur(true);
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