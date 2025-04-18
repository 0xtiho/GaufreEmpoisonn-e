import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MonApplication extends JComponent {
    int line,column;
    Image gaufreNormal,gaufreMange,poison;
    int [] grille;
    int poisonX,poisonY;
    int widthG,heightG;
    MonApplication(int ligne, int colonne) {
        gaufreNormal= Gaufre.charge("gaufre.png");
        gaufreMange= Gaufre.charge("gaufremange.png");
        poison= Gaufre.charge("poison.png");

        this.line=ligne;
        this.column=colonne;
        this.grille=new int[colonne];

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        widthG=getWidth()/column;
        heightG=getHeight()/line;
        g2d.drawImage(poison,0,0,widthG,heightG,null);
        for (int j=0;j<column;j++) {
            for (int i=0; i<grille[j]; i++) {

                g2d.drawImage(gaufreMange,j*widthG,getHeight()-((i+1)*heightG),widthG,heightG,null);
            }
            for (int i=0;i<line-grille[j];i++){
                if(!(i==0 && j==0)){
                    g2d.drawImage(gaufreNormal,j*widthG,i*heightG,widthG,heightG,null);}
            }
        }
    }

    public static void demarre(JFrame frame, int ligne, int colonne) {
        MonApplication app = new MonApplication(ligne, colonne);  // Cree une instance de Examen
        frame.add(app);
    }
}
