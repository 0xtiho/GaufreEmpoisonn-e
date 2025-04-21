package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Gauffre {

    private int[] gauffre;
    private int ligne;
    private int colonne;
    Historique historique;
    public final int POISON = 2;
    public final int VIDE = 1;
    public final int CHOCOLAT = 0;
    private Point mouseXY;
    public int Perdu=0;
    public boolean perduParAbandon = false; // flag pour savoir si la partie est perdue par abandon

    public Gauffre(int l, int c) {
        this.colonne = c;
        this.ligne = l;
        gauffre = new int[c];
        historique = new Historique();
    }

    public void affichergrille(){
        for (int i = 0; i < gauffre.length; i++) {
            System.out.print(gauffre[i]+" ");
        }
        System.out.println();
    }

    public void annule(){
        EtatGauffre etat = historique.annule();
        if(etat != null){
            Map<Integer, Integer> changements = etat.getChangements();
            for (Map.Entry<Integer, Integer> entry : changements.entrySet()) {
                int colonne = entry.getKey();
                int ancienneValeur = entry.getValue();
                gauffre[colonne] = ancienneValeur;
            }
            verifierPerdu();
        }
    }

    public void refait(){
        EtatGauffre etat = historique.refait();
        if (etat != null){
            Point p = etat.getMouvement();
            remange(p.x, p.y);
        }
    }

    public void setGauffre(int ind, int val){
        gauffre[ind] = val;
    }
    
    public void resetgame(){
        gauffre = new int[colonne];
        historique = new Historique();
        Perdu = 0;
        perduParAbandon = false; // Réinitialiser le flag d'abandon lors d'un restart
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

    public boolean peut_manger(int x, int y){
        return (ligne-gauffre[x] > y && (x != 0 || y != 0));
    }
    
    public void manger(int x, int y){
        if (peut_manger(x, y)) {
            EtatGauffre nouvelEtat = new EtatGauffre(new Point(x, y));
            
            for (int i = x; i < colonne; i++) {
                if (gauffre[i] < ligne - y) {
                    nouvelEtat.ajouterChangement(i, gauffre[i]);
                }
            }
            
            historique.ajoute(nouvelEtat);
            historique.enleve_refait();
            
            for (int i = x; i < colonne; i++) {
                if (gauffre[i] < ligne - y) {
                    gauffre[i] = ligne - y;
                }
            }
        }
    }
    
    public void remange(int x, int y){
        if (peut_manger(x, y)) {
            for (int i = x; i < colonne; i++) {
                if (gauffre[i] < ligne - y) {
                    gauffre[i] = ligne - y;
                }
            }
        }
    }
    
    private void verifierPerdu() {
        Perdu = (!(peut_manger(0,1) || peut_manger(1,0))) ? 1 : 0;
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
}
