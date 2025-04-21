package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle du jeu de la Gaufre Empoisonnée
 * Gère la logique du jeu et les données
 */
public class GaufreModel {
    public static final int CELL_NORMAL = 0;
    public static final int CELL_EATEN = 1;
    public static final int CELL_POISON = 2;
    
    private int lines;
    private int columns;
    private int[][] grid;
    private Point selection;
    private boolean gameOver;
    private List<Point> moveHistory; // historique des coups joués
    
    public GaufreModel(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.grid = new int[lines][columns];
        this.gameOver = false;
        this.moveHistory = new ArrayList<>();
        
        // initialiser la grille
        // cellule (0,0) empoisonnée
        for (int x = 0; x < lines; x++) {
            for (int y = 0; y < columns; y++) {
                grid[x][y] = CELL_NORMAL;
            }
        }
        grid[0][0] = CELL_POISON;
    }
    
    public int getlines() {
        return lines;
    }
    
    public int getcolumns() {
        return columns;
    }
    
    public int getCellState(int x, int y) {
        return grid[x][y];
    }
    
    public Point getSelection() {
        return selection;
    }
    
    public void setSelection(Point selection) {
        this.selection = selection;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isValidMove(int x, int y) {
        // Vérifier si on peut jouer à cette position
        if (x < 0 || x >= lines || y < 0 || y >= columns) {
            return false; 
        }
        
        if (grid[x][y] == CELL_EATEN) {
            return false; // déjà mangé
        }
        
        if (x == 0 && y == 0) {
            // manger le poison termine la partie
            return true;
        }
        
        return true;
    }
    
    public void play(int x, int y) {
        if (gameOver || !isValidMove(x, y)) {
            return;
        }
        
        if (x == 0 && y == 0) {
            // le joueur a mangé le poison
            gameOver = true;
            return;
        }
        
        // enregistrer le mouvement dans l'historique
        moveHistory.add(new Point(x, y));
        
        // manger toutes les cases en bas à droite
        for (int i = x; i < lines; i++) {
            for (int j = y; j < columns; j++) {
                grid[i][j] = CELL_EATEN;
            }
        }
        
        // vérifier si le jeu est terminé (seulement le poison reste)
        checkGameOver();
    }
    
    private void checkGameOver() {
        // le jeu est terminé si seule la case poison reste
        boolean onlyPoisonLeft = true;
        
        for (int x = 0; x < lines; x++) {
            for (int y = 0; y < columns; y++) {
                if (x == 0 && y == 0) {
                    continue; // Ignorer la case poison
                }
                if (grid[x][y] == CELL_NORMAL) {
                    onlyPoisonLeft = false;
                    break;
                }
            }
            if (!onlyPoisonLeft) break;
        }
        
        gameOver = onlyPoisonLeft;
    }
    
    public void restart() {
        // Réinitialiser la grille
        for (int x = 0; x < lines; x++) {
            for (int y = 0; y < columns; y++) {
                grid[x][y] = CELL_NORMAL;
            }
        }
        grid[0][0] = CELL_POISON;
        
        gameOver = false;
        moveHistory.clear();
    }
}
