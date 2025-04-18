import javax.swing.*;
import java.awt.*;

public class MonApplication extends JComponent {
    int line,column;
    Image image;
    int [][]grille; //0 pour gaufre, 1 pour croque
    int poisonX,poisonY;
    int sizeX=80,sizeY=80;
    MonApplication(int ligne, int colonne) {
        image= Gaufre.charge("gaufre.png");
        this.line=ligne;
        this.column=colonne;
        grille=new int[ligne][colonne];
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i=0;i<line;i++) {
            for (int j=0;j<column;j++) {
                g2d.drawImage(image,i*sizeX,j*sizeY,sizeX,sizeY,null);
            }
        }

    }

    public static void demarre(JFrame frame, int ligne, int colonne) {
        MonApplication app = new MonApplication(ligne, colonne);  // Crée une instance de Examen
        frame.add(app);
    }
}
