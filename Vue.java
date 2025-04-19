import javax.swing.*;
import java.awt.*;

public class Vue extends JComponent {
    int line, column;
    Image gaufreNormal, gaufreMange, poison, gaufreSelect;
    int poisonX, poisonY;
    int widthG, heightG;
    Gauffre gauffre;

    Vue(int ligne, int colonne, Gauffre g) {


        gaufreNormal = MonApplication.charge("gaufre.png");
        gaufreMange = MonApplication.charge("gaufremange.png");
        poison = MonApplication.charge("poison.png");
        gaufreSelect = MonApplication.charge("carreSelection.png");


        this.line = ligne;
        this.column = colonne;
        this.gauffre = g;


    }

    public void redessine(){
        repaint();
    }

    Point pixToCord(Point p) {
        return new Point(p.x / (getWidth() / column), p.y / (getHeight() / line));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (gauffre.Perdu == 0) {
            widthG = getWidth() / column;
            heightG = getHeight() / line;
            g2d.drawImage(poison, 0, 0, widthG, heightG, null);
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < gauffre.getGauffre()[j]; i++) {
                    g2d.drawImage(gaufreMange, j * widthG, getHeight() - ((i + 1) * heightG), widthG, heightG, null);
                }
                for (int i = 0; i < line - gauffre.getGauffre()[j]; i++) {
                    if (!(i == 0 && j == 0)) {
                        g2d.drawImage(gaufreNormal, j * widthG, i * heightG, widthG, heightG, null);
                    }
                }
            }
            Point po = gauffre.getMouseXY();
            if (po != null) {
                Point p = pixToCord(po);
                for (int j = p.x; j < column; j++) {
                    for (int i = p.y; i < line; i++) {
                        if (gauffre.peut_manger(j, i)) {
                            g2d.drawImage(gaufreSelect, j * widthG, (i * heightG), widthG, heightG, null);
                        }
                    }
                }
            }
        }
        else {
            // Fond semi-transparent sombre
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Rectangle pour le message
            int rectWidth = 300;
            int rectHeight = 150;
            int rectX = (getWidth() - rectWidth) / 2;
            int rectY = (getHeight() - rectHeight) / 2;

            // Dégradé rouge pour le fond du message
            GradientPaint gradient = new GradientPaint(
                    rectX, rectY, new Color(200, 50, 50),
                    rectX, rectY + rectHeight, new Color(150, 30, 30));
            g2d.setPaint(gradient);
            g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);

            // Bordure du rectangle
            g2d.setColor(new Color(100, 20, 20));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);

            // Texte "Vous avez perdu !"
            g2d.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, 24);
            g2d.setFont(font);

            String text = "Vous avez perdu !";
            int textWidth = g2d.getFontMetrics().stringWidth(text);
            int textX = rectX + (rectWidth - textWidth) / 2;
            int textY = rectY + 50;
            g2d.drawString(text, textX, textY);

            // Message supplémentaire
            font = new Font("Arial", Font.PLAIN, 16);
            g2d.setFont(font);
            text = "Cliquez pour rejouer";
            textWidth = g2d.getFontMetrics().stringWidth(text);
            textX = rectX + (rectWidth - textWidth) / 2;
            textY = rectY + 90;
            g2d.drawString(text, textX, textY);

            // Vous pouvez aussi ajouter une image de gaufre empoisonnée
            int imgSize = 60;
            g2d.drawImage(poison,
                    (getWidth() - imgSize) / 2,
                    rectY - imgSize - 10,
                    imgSize, imgSize, null);
        }


    }
}