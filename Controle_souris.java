import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent; 

public class Controle_souris implements MouseMotionListener, MouseListener{
    private Gauffre gauffre;
    private Vue vue;

    public Controle_souris(Gauffre g, Vue v) {
        this.gauffre = g;
        this.vue = v;

        //ici on ajoute eouteur de mouvement et clic a la vue mouvement 
        //puis gestion se fais par controleSouris
        this.vue.addMouseMotionListener(this);
        this.vue.addMouseListener(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        gauffre.setMouseXY(new Point(x,y));
        vue.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        Point clicPoint = new Point(e.getX(), e.getY());
        Point grillePoint = vue.pixToCord(clicPoint);
        int col = grillePoint.x;
        int lig = grillePoint.y;

        if (col >= 0 && col < gauffre.get_colonne() && lig >= 0 && lig < gauffre.get_ligne()) {
            // manger toutes les cases rouges selectionnee
            int[] grille = gauffre.getGauffre();
            for (int j = col; j < gauffre.get_colonne(); j++) {
                int casesRestantes = gauffre.get_ligne() - grille[j]; // Nb cases non mange
                if (casesRestantes > lig) {
                    grille[j] = gauffre.get_ligne() - lig;
                }
            }
            // Reinitialiser la position de la souris pour supprimer la surbrillance rouge
            gauffre.setMouseXY(null);
            vue.repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
