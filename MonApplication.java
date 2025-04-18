import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MonApplication extends JComponent {
    int line,column;
    Image gaufreNormal,gaufreMange;
    int [] grille;
    int poisonX,poisonY;
    int widthG,heightG;
    MonApplication(int ligne, int colonne) {
        gaufreNormal= Gaufre.charge("gaufre.png");
        gaufreMange= Gaufre.charge("gaufremange.png");

        this.line=ligne;
        this.column=colonne;
        this.grille=new int[colonne];

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        widthG=getWidth()/column;
        heightG=getHeight()/line;
        grille[1]=3;

        for (int j=0;j<column;j++) {
            for (int i=0; i<grille[j]; i++) {
                g2d.drawImage(gaufreMange,j*widthG,getHeight()-((i+1)*heightG),widthG,heightG,null);
            }
            for (int i=0;i<line-grille[j];i++){
                g2d.drawImage(gaufreNormal,j*widthG,i*heightG,widthG,heightG,null);

            }
        }

    }

    public static void demarre(JFrame frame, int ligne, int colonne) {
        MonApplication app = new MonApplication(ligne, colonne);  // Crée une instance de Examen
        frame.add(app);
    }
}
