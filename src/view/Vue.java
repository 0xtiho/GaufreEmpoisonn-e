package view;

import javax.swing.*;
import java.awt.*;
import model.Gauffre;
import model.MonApplication;

public class Vue extends JComponent {
    int line, column;
    Image gaufreNormal, gaufreMange, poison, gaufreSelect;
    int poisonX, poisonY;
    int widthG, heightG;
    Gauffre gauffre;
    public JLabel turn ;
    public Vue(int ligne, int colonne, Gauffre g) {


        gaufreNormal = MonApplication.charge("gaufre.png");
        gaufreMange = MonApplication.charge("gaufremange.png");
        poison = MonApplication.charge("poison.png");
        gaufreSelect = MonApplication.charge("carreSelection.png");


        this.line = ligne;
        this.column = colonne;
        this.gauffre = g;
        this.turn=new JLabel("Tour Joueur "+ gauffre.tour);


    }

    public void redessine(){
        repaint();
    }

    public Point pixToCord(Point p) {
        return new Point(p.x / (getWidth() / column), p.y / (getHeight() / line));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (gauffre.Perdu == 0) {
            turn.setText("Tour Joueur "+(String.valueOf(gauffre.tour)));
            turn.setFont(new Font("Arial", Font.BOLD, 16));
            widthG = getWidth() / column;
            heightG = getHeight() / line;
            g2d.drawImage(poison, 0, 0, widthG, heightG, null);
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < line; i++) {
                    if (i == 0 && j == 0) continue; 
                    if (i < line - gauffre.getGauffre()[j]) {
                        // Case non mangée
                        g2d.drawImage(gaufreNormal, j * widthG, i * heightG, widthG, heightG, null);
                    } else {
                        // Case mangée
                        g2d.drawImage(gaufreMange, j * widthG, i * heightG, widthG, heightG, null);
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
            turn.setText("");
            // Fond semi-transparent
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());

// Rectangle du message
            int rectWidth = 600;  // Légèrement plus large
            int rectHeight = 180;
            int rectX = (getWidth() - rectWidth) / 2;
            int rectY = (getHeight() - rectHeight) / 2;

// Dégradé rouge amélioré
            GradientPaint gradient = new GradientPaint(
                    rectX, rectY, new Color(220, 60, 60),
                    rectX, rectY + rectHeight, new Color(170, 40, 40));
            g2d.setPaint(gradient);
            g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);

// Bordure plus visible
            g2d.setColor(new Color(120, 30, 30));
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);

// --- TEXTE PRINCIPAL ---
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

// Ombre du texte
            g2d.setColor(new Color(0, 0, 0, 120));
            Font font = new Font("Arial", Font.BOLD, 28);
            g2d.setFont(font);
            int joueur =(gauffre.tour%2)+1;
            String mainText = "Mister Joueur " + joueur + " vous avez gagné !";
            int mainTextWidth = g2d.getFontMetrics().stringWidth(mainText);
            g2d.drawString(mainText, rectX + (rectWidth - mainTextWidth)/2 + 2, rectY + 60 + 2);

// Texte principal (blanc)
            g2d.setColor(Color.WHITE);
            g2d.drawString(mainText, rectX + (rectWidth - mainTextWidth)/2, rectY + 60);

// --- SOUS-TEXTE ---
            Font subFont = new Font("Arial", Font.PLAIN, 18);
            g2d.setFont(subFont);
            String subText = "Cliquez pour rejouer";
            int subTextWidth = g2d.getFontMetrics().stringWidth(subText);

// Ombre
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.drawString(subText, rectX + (rectWidth - subTextWidth)/2 + 1, rectY + 110 + 1);

// Texte (gris clair)
            g2d.setColor(new Color(230, 230, 230));
            g2d.drawString(subText, rectX + (rectWidth - subTextWidth)/2, rectY + 110);

// Icône (optionnel)
            int imgSize = 70;
            if (poison != null) {
                g2d.drawImage(poison,
                        (getWidth() - (2*imgSize))/2,
                        rectY - 2*imgSize - 5,
                        2*imgSize, 2*imgSize, null);
            }
        }


    }
}