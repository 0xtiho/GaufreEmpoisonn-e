import javax.swing.*;
import java.awt.*;

public class Vue extends JComponent {
    int line,column;
    Image gaufreNormal,gaufreMange,poison,gaufreSelect;
    int [] grille;
    int poisonX,poisonY;
    int widthG,heightG;
    Gauffre gauffre;
    Vue(int ligne, int colonne, Gauffre g) {


        gaufreNormal= MonApplication.charge("gaufre.png");
        gaufreMange= MonApplication.charge("gaufremange.png");
        poison= MonApplication.charge("poison.png");
        gaufreSelect= MonApplication.charge("carreSelection.png");


        this.line=ligne;
        this.column=colonne;
        this.gauffre=g;
        this.grille=g.getGauffre();

    }
    
    Point pixToCord(Point p){
        return new Point(p.x/(getWidth()/column),p.y/(getHeight()/line));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Calcul de la taille des cases
        widthG = getWidth() / column;
        heightG = getHeight() / line;

        // Dessiner la case empoisonnée (0,0)
        g2d.drawImage(poison, 0, 0, widthG, heightG, null);

        // Dessiner les cases de la gaufre (mangées et non mangées)
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < line; i++) {
                if (i == 0 && j == 0) {
                    continue; // Case empoisonnée déjà dessinée
                }
                if (i < line - grille[j]) {
                    // Case non mangée (gaufre normale)
                    g2d.drawImage(gaufreNormal, j * widthG, i * heightG, widthG, heightG, null);
                } else {
                    // Case mangée
                    g2d.drawImage(gaufreMange, j * widthG, i * heightG, widthG, heightG, null);
                }
            }
        }

        // Dessiner les cases sélectionnées
        Point po = gauffre.getMouseXY();
        if (po != null) {
            Point p = pixToCord(po);
            for (int j = p.x; j < column; j++) {
                for (int i = p.y; i < line; i++) {
                    if (i < line - grille[j]) { // Seulement pour les cases non mangées
                        g2d.drawImage(gaufreSelect, j * widthG, i * heightG, widthG, heightG, null);
                    }
                }
            }
        }
    }


}