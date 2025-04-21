package control;

import java.awt.*;
import java.awt.event.*;
import model.GaufreModel;
import view.GaufreView;

public class GaufreController extends MouseAdapter {
    private GaufreModel model;
    private GaufreView view;
    
    public GaufreController(GaufreModel model, GaufreView view) {
        this.model = model;
        this.view = view;
        
        view.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Point cell = view.getCell(e.getX(), e.getY());
        
        if (cell != null && model.isValidMove(cell.x, cell.y)) {
            model.play(cell.x, cell.y);
        }
    }
}
