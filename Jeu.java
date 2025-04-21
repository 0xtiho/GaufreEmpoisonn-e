import javax.swing.*;
import java.awt.*;

public class Jeu {
    Vue vue;
    Gauffre gauffre;
    contolMouseMotion controleSouris;

    IA ia; 
    private boolean tourJoueur; // true = tour du joueur, false = tour de l'IA

    public Jeu(JFrame frame, int ligne, int colonne){
        Gauffre g = new Gauffre(ligne,colonne);
        this.gauffre =g; 

        vue = new Vue(ligne, colonne,g);  // Crée une instance de Examen
        controleSouris=new contolMouseMotion(g,vue);
        controlSouris controlSouris=new controlSouris(g,vue,this);
        controlRestart restart=new controlRestart(vue,g);
        controlannule annule=new controlannule(vue,g);
        controlrefais refais=new controlrefais(vue,g);

        //  1 pour aléatoire
        ia = new IA(g, 3);
        tourJoueur = true ;// humain qui commence 

        JPanel panel = new JPanel();
        JButton b1=new JButton("RESTART");
        b1.setFocusable(false);
        b1.addActionListener(restart);
        JButton b2=new JButton("<-");
        b2.setFocusable(false);
        b2.addActionListener(annule);
        JButton b3=new JButton("->");
        b3.setFocusable(false);
        b3.addActionListener(refais);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);

        JPanel container=new JPanel();
        container.setLayout(new BorderLayout());
        container.add(panel,BorderLayout.SOUTH);
        vue.addMouseMotionListener(controleSouris);
        vue.addMouseListener(controlSouris);
        container.add(vue);
        System.out.println(frame.getWidth()+' '+frame.getHeight());
        frame.add(container);
    }

    public void jouerTourIA() {
        if (!tourJoueur && gauffre.Perdu == 0) {
            Timer timer = new javax.swing.Timer(1000, e -> {
                ia.jouerCoup();         // L'IA joue
                vue.redessine();        
                tourJoueur = true;      
            });
            timer.setRepeats(false); // !!!! joue une seule fois !!!! 
            timer.start();           
        }
    }


    public boolean isTourJoueur() {
        return tourJoueur;
    }

    public void setTourJoueur(boolean tourJoueur) {
        this.tourJoueur = tourJoueur;
    }

}