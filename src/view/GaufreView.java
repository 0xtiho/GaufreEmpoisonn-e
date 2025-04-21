package view;

import java.awt.*;
import javax.swing.*;
import model.GaufreModel;
import model.MonApplication;

public class GaufreView extends JPanel {
    private GaufreModel model;
    private int cellWidth;
    private int cellHeight;
    private Image gaufreNormal, gaufreMange, poison, gaufreSelect;

    public GaufreView(GaufreModel model) {
        this.model = model;
        
        // Chargement des images
        gaufreNormal = MonApplication.charge("gaufre.png");
        gaufreMange = MonApplication.charge("gaufremange.png");
        poison = MonApplication.charge("poison.png");
        gaufreSelect = MonApplication.charge("carreSelection.png");
        
        setPreferredSize(new Dimension(500, 500));
    }
    
    public Point getCell(int x, int y) {
        if (cellWidth <= 0 || cellHeight <= 0) {
            return null;
        }
        
        int cellX = x / cellWidth;
        int cellY = y / cellHeight;
        
        if (cellX >= 0 && cellX < model.getlines() && cellY >= 0 && cellY < model.getcolumns()) {
            return new Point(cellX, cellY);
        }
        
        return null;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        int width = getWidth();
        int height = getHeight();
        
        cellWidth = width / model.getlines();
        cellHeight = height / model.getcolumns();
        
        // Dessiner la grille
        for (int y = 0; y < model.getcolumns(); y++) {
            for (int x = 0; x < model.getlines(); x++) {
                int cellState = model.getCellState(x, y);
                Image img;
                
                if (x == 0 && y == 0) {
                    img = poison;
                } else if (cellState == GaufreModel.CELL_EATEN) {
                    img = gaufreMange;
                } else {
                    img = gaufreNormal;
                }
                
                g2d.drawImage(img, x * cellWidth, y * cellHeight, cellWidth, cellHeight, null);
            }
        }
        
        // Dessiner la sélection si nécessaire
        Point selection = model.getSelection();
        if (selection != null) {
            // Afficher la sélection
            for (int y = selection.y; y < model.getcolumns(); y++) {
                for (int x = selection.x; x < model.getlines(); x++) {
                    if (model.isValidMove(x, y)) {
                        g2d.drawImage(gaufreSelect, x * cellWidth, y * cellHeight, cellWidth, cellHeight, null);
                    }
                }
            }
        }
        
        // Afficher un message si le jeu est terminé
        if (model.isGameOver()) {
            // Fond semi-transparent
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, width, height);
            
            // Message de fin
            String message = "Partie terminée!";
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            
            FontMetrics metrics = g2d.getFontMetrics();
            int x = (width - metrics.stringWidth(message)) / 2;
            int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
            
            g2d.drawString(message, x, y);
        }
    }
}
