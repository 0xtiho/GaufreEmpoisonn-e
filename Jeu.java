import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jeu {
    Vue vue;
    Gauffre gauffre;
    Controle_souris controleSouris;

    public Jeu(JFrame frame, int ligne, int colonne){
        Gauffre g = new Gauffre(ligne,colonne);
        vue = new Vue(ligne, colonne,g); 
        controleSouris=new Controle_souris(g,vue);

        //ici jai fixer taille gauffre 
        vue.setPreferredSize(new Dimension(500, 460));

        JPanel panel = new JPanel();
        JButton b1=new JButton("RESTART");
        b1.setFocusable(false);
        JButton b2=new JButton("<-");
        b2.setFocusable(false);
        JButton b3=new JButton("->");
        b3.setFocusable(false);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);

        //jai fais ca pour respecter taille frame total de 500x500 
        panel.setPreferredSize(new Dimension(500, 40));
        

        //ajout de l'ActionListener au bouton RESTART
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.RESTART();
                vue.repaint();
            }
        });


        frame.setLayout(new BorderLayout());
        vue.setLayout(new BorderLayout());

        //jl enlever car toute gestion de souris est dans controleSouris et c'est lui qui gere le clic+mouvement souris
        //vue.addMouseMotionListener(controleSouris);
        
        // et la on dois mettre la vue au centre et panel en bas
        frame.add(vue, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        
    }

}
