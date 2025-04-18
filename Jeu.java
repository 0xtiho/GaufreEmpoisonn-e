import javax.swing.*;
import java.awt.*;

public class Jeu {
    Vue vue;
    Gauffre gauffre;
    Controle_souris controleSouris;

    public Jeu(JFrame frame, int ligne, int colonne){
        Gauffre g = new Gauffre(ligne,colonne);
        vue = new Vue(ligne, colonne,g);  // Crée une instance de Examen
        controleSouris=new Controle_souris(g,vue);
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
        vue.setLayout(new BorderLayout());
        vue.addMouseMotionListener(controleSouris);
        vue.add(panel,BorderLayout.SOUTH);
        System.out.println(frame.getWidth()+' '+frame.getHeight());
        frame.add(vue);
    }

}
