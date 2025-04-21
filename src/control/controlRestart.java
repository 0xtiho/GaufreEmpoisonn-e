package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Gauffre;
import model.Jeu;
import view.Vue;

public class controlRestart implements ActionListener {
    private Vue vue;
    private Gauffre g;
    private Jeu jeu;
    
    public controlRestart(Vue v, Gauffre g, Jeu jeu){
        this.vue = v;
        this.g = g;
        this.jeu = jeu;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        g.resetgame();
        // Remettre le tour au joueur 1
        jeu.setTourJoueur(true);
        vue.redessine();
    }
}
