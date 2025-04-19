import javax.swing.*;
import java.awt.*;

public class Jeu {
    Vue vue;
    Gauffre gauffre;
    contolMouseMotion controleSouris;

    public Jeu(JFrame frame, int ligne, int colonne){
        Gauffre g = new Gauffre(ligne,colonne);
        vue = new Vue(ligne, colonne,g);  // Crée une instance de Examen
        controleSouris=new contolMouseMotion(g,vue);
        controlSouris controlSouris=new controlSouris(g,vue);
        controlRestart restart=new controlRestart(vue,g);
        JPanel panel = new JPanel();
        JButton b1=new JButton("RESTART");
        b1.setFocusable(false);
        b1.addActionListener(restart);
        JButton b2=new JButton("<-");
        b2.setFocusable(false);
        JButton b3=new JButton("->");
        b3.setFocusable(false);
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

}
