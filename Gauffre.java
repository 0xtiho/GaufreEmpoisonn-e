import java.awt.*;
import java.util.ArrayList;

public class Gauffre {

    private int[] gauffre;
    private int ligne;
    private int colonne ;
    public final int POISON = 2;
    public final int VIDE = 1;
    public final int CHOCOLAT = 0;
    private Point mouseXY;



    public Gauffre(int l, int c) {

        this.colonne = c;
        this.ligne = l;
        gauffre = new int[c];



    }

    public void setMouseXY(Point mouseXY) {
        this.mouseXY = mouseXY;
    }

    public Point getMouseXY() {
        return mouseXY;
    }

    public int[] getGauffre() {
        return gauffre;
    }


    public int get_ligne(){
        return ligne;
    }
    public int get_colonne(){
        return colonne;
    }

    private boolean peut_manger(int l ,int c){
        return (gauffre[c]<l);
    }
    public void manger(int l , int c){
        if(peut_manger(l,c)){
            for (int i=c;i<colonne;i++){
                if(gauffre[i]<ligne-l){
                    gauffre[i]=ligne-l;
                }
            }
        }
    }


    public void afficher() {
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                if (i == 0 && j == 0) {
                    System.out.print(POISON + " ");  // Case poison en (0,0)
                } else if (i < ligne - gauffre[j]) {  // Condition corrigée
                    System.out.print(CHOCOLAT + " ");  // Case disponible
                } else {
                    System.out.print(VIDE + " ");  // Case mangée
                }
            }
            System.out.println();
        }
    }

    public void RESTART() {
        for (int i = 0; i < colonne; i++) {
            gauffre[i] = 0;//CHOCOLAT
        }
        mouseXY = null; //reinitialise position de souris 
    }

    public static void main(String[] args) {
        Gauffre gauffre1 = new Gauffre(6,4);
        gauffre1.afficher();
        System.out.println();
        gauffre1.manger(2 ,2);
        gauffre1.afficher();

    }

}
