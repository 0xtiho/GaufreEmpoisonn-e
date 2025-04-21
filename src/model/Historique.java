package model;

import java.awt.*;
import java.util.Stack;

public class Historique {
    private Stack<EtatGauffre> annule;
    private Stack<EtatGauffre> refait;
    
    public Historique() {
        annule = new Stack<>();
        refait = new Stack<>();
    }
    

    public EtatGauffre annule() {
        if (peut_annule()) {
            EtatGauffre etat = annule.pop();
            refait.push(etat);
            return etat;
        }
        return null;
    }
    

    public EtatGauffre refait() {
        if (peut_refaire()) {
            EtatGauffre etat = refait.pop();
            annule.push(etat);
            return etat;
        }
        return null;
    }
    

    public void ajoute(EtatGauffre etat) {
        annule.push(etat);
    }
    

    public void enleve_refait() {
        if (!refait.isEmpty()) {
            refait.clear();
        }
    }
    

    public boolean peut_annule() {
        return !annule.isEmpty();
    }
    

    public boolean peut_refaire() {
        return !refait.isEmpty();
    }

    public Stack<EtatGauffre> getAnnule() {
        return annule;
    }
}
