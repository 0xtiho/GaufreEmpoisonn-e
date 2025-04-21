package model;

import java.awt.Point;

public class historiqueGrille{
    private Point move;
    private int [] etatGrille;

    public historiqueGrille(Point x, int [] etat){
        this.move = x;
        this.etatGrille = etat.clone();
    }
    public Point getMovement(){
        return move;
    }
    public int[] getGrille(){
        return etatGrille;
    }
}