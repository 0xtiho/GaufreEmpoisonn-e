import java.util.ArrayList;
import java.util.List;

public class GaufreModel {
    private int lines;
    private int columns;
    private int[] grille;
    
    private List<GaufreModelListener> listeners = new ArrayList<>();
    
    public GaufreModel(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.grille = new int[columns];
    }
    
    public int getLines() {
        return lines;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public int[] getGrille() {
        return grille;
    }
    
    public void play(int x, int y) {
        int col = x;
        
        for (int j = 0; j <= col; j++) {
            grille[j] = Math.max(grille[j], lines - y);
        }
        
        notifyListeners();
    }
    
    public boolean isValidMove(int x, int y) {
        if (x < 0 || x >= columns || y < 0 || y >= lines) {
            return false;
        }
        return y < lines - grille[x];
    }
    
    public interface GaufreModelListener {
        void modelChanged();
    }
    
    public void addListener(GaufreModelListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(GaufreModelListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyListeners() {
        for (GaufreModelListener listener : listeners) {
            listener.modelChanged();
        }
    }
}
