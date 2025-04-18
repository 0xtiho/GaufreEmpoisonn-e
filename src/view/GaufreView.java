import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class GaufreView extends JComponent implements GaufreModel.GaufreModelListener {
    private GaufreModel model;
    private Image gaufreNormal, gaufreMange, poison;
    private int widthG, heightG;

    static Image charge(String nom) {
    try {
        return ImageIO.read(GaufreView.class.getClassLoader().getResourceAsStream("img/" + nom));
    } catch (Exception e) {
        System.err.println("Erreur imprévue : " + e.getMessage());
        System.exit(1);
        return null;
    }
}
    
    public GaufreView(GaufreModel model) {
        this.model = model;
        model.addListener(this);
        
        // Load images
        gaufreNormal = charge("gaufre.png");
        gaufreMange = charge("gaufremange.png");
        poison = charge("poison.png");
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        widthG = getWidth() / model.getColumns();
        heightG = getHeight() / model.getLines();
        
        // Draw poison at position (0,0)
        g2d.drawImage(poison, 0, 0, widthG, heightG, null);
        
        int[] grille = model.getGrille();
        for (int j = 0; j < model.getColumns(); j++) {
            // Draw eaten squares
            for (int i = 0; i < grille[j]; i++) {
                g2d.drawImage(gaufreMange, j * widthG, getHeight() - ((i + 1) * heightG), 
                              widthG, heightG, null);
            }
            
            // Draw normal waffle squares
            for (int i = 0; i < model.getLines() - grille[j]; i++) {
                if (!(i == 0 && j == 0)) {  // Skip poison position
                    g2d.drawImage(gaufreNormal, j * widthG, i * heightG, 
                                  widthG, heightG, null);
                }
            }
        }
    }
    
    @Override
    public void modelChanged() {
        repaint();
    }
    
    // Get cell coordinates from screen position
    public Point getCell(int x, int y) {
        int col = x / widthG;
        int row = y / heightG;
        
        if (col >= 0 && col < model.getColumns() && row >= 0 && row < model.getLines()) {
            return new Point(col, row);
        }
        return null;
    }
}
