package model;

import javax.swing.*;
import java.awt.*;
import view.Vue;
import control.*;

public class Jeu {
    Vue vue;
    Gauffre gauffre;
    contolMouseMotion controleSouris;

    IA ia; 
    private boolean tourJoueur; // true = tour du joueur, false = tour de l'IA
    private boolean aiActive = false; // Option pour activer/désactiver l'IA
    private JLabel statusLabel; // Pour afficher l'état du jeu

    public Jeu(JFrame frame, int ligne, int colonne){
        Gauffre g = new Gauffre(ligne,colonne);
        this.gauffre =g; 

        vue = new Vue(ligne, colonne,g);  // Crée une instance de Examen
        controleSouris=new contolMouseMotion(g,vue);
        controlSouris controlSouris=new controlSouris(g,vue,this);
        controlRestart restart=new controlRestart(vue,g,this);
        controlannule annule=new controlannule(vue,g);
        controlrefais refais=new controlrefais(vue,g);

        //  1 pour aléatoire
        ia = new IA(g, 1);
        tourJoueur = true ;// humain qui commence 

        // Status label pour indiquer le tour actuel
        statusLabel = new JLabel("Mode: 2 Joueurs - Tour: Joueur 1");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        updateStatusLabel();

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
        
        // Checkbox pour activer/désactiver l'IA
        JCheckBox aiCheckBox = new JCheckBox("Activer IA");
        aiCheckBox.setFocusable(false);
        aiCheckBox.addActionListener(e -> {
            aiActive = aiCheckBox.isSelected();
            updateStatusLabel();
        });
        
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(aiCheckBox);

        JPanel container=new JPanel();
        container.setLayout(new BorderLayout());
        
        // Ajouter le status label en haut
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(statusLabel, BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        container.add(topPanel, BorderLayout.NORTH);
        
        container.add(panel,BorderLayout.SOUTH);
        vue.addMouseMotionListener(controleSouris);
        vue.addMouseListener(controlSouris);
        container.add(vue, BorderLayout.CENTER);
        System.out.println(frame.getWidth()+' '+frame.getHeight());
        frame.add(container);
    }

    // Méthode pour mettre à jour l'étiquette d'état
    private void updateStatusLabel() {
        String mode = aiActive ? "IA" : "2 Joueurs";
        String tour = tourJoueur ? "Joueur 1" : (aiActive ? "IA" : "Joueur 2");
        statusLabel.setText("Mode: " + mode + " - Tour: " + tour);
    }

    public void jouerTourIA() {
        if (!tourJoueur && aiActive && gauffre.Perdu == 0) {
            Timer timer = new javax.swing.Timer(1000, e -> {
                ia.jouerCoup();         // L'IA joue
                vue.redessine();        
                tourJoueur = true;
                updateStatusLabel();
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
        updateStatusLabel();
    }
    
    public boolean isAiActive() {
        return aiActive;
    }
}