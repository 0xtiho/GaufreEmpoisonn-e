import javax.swing.*;
import java.awt.*;

public class MonApplication extends JComponent {
    int line,column;
    Image gaufreNormal,gaufreMange;
    int [][]grille; //0 pour gaufre, 1 pour croque
    int poisonX,poisonY;
    int widthG,heightG;
    MonApplication(int ligne, int colonne) {
        gaufreNormal= Gaufre.charge("gaufre.png");
        gaufreMange= Gaufre.charge("gaufremange.png");

        this.line=ligne;
        this.column=colonne;
        grille=new int[ligne][colonne];
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        widthG=getWidth()/column;
        heightG=getHeight()/line;

        for (int i=0;i<line;i++) {
            for (int j=0;j<column;j++) {
                if (grille[i][j]==0) {
                    g2d.drawImage(gaufreNormal,j*widthG,i*heightG,widthG,heightG,null);

                }else{
                    g2d.drawImage(gaufreMange,j*widthG,i*heightG,widthG,heightG,null);

                }
            }
        }

    }

    public static void demarre(JFrame frame, int ligne, int colonne) {
        MonApplication app = new MonApplication(ligne, colonne);  // Crée une instance de Examen
        frame.add(app);
    }
}
