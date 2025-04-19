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
    int Perdu=0;



    public Gauffre(int l, int c) {

        this.colonne = c;
        this.ligne = l;
        gauffre = new int[c];



    }
    public void setGauffre(int ind,int val){
        gauffre[ind]=val;
    }
    public void resetgame(){
        for(int i=0;i<colonne;i++){
            setGauffre(i,0);
        }
        Perdu=0;
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

    public boolean peut_manger(int x ,int y){
        return (ligne-gauffre[x]>y && (x!=0 || y!=0));
    }
    public void manger(int x , int y){
        if(peut_manger(x,y)){
            for (int i=x;i<colonne;i++){
                if(gauffre[i]<ligne-y){
                    gauffre[i]=ligne-y;
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
    public static void main(String[] args) {
        Gauffre gauffre1 = new Gauffre(6,4);
        gauffre1.afficher();
        System.out.println();
        gauffre1.manger(2 ,2);
        gauffre1.afficher();

    }

}
