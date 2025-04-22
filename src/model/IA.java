package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class IA {
    private Gauffre gauffre;
    private int niveau; // 1 = aleatoire, 2 = non perdant/gagnant, 3 = minimax
    private Random random;
    private static final int INFINITY = 1000; // Pour Minimax
    private static final int MAX_PROFONDEUR = 16; //ajustiwha selon taille grille ! attention 16 si <= 4X4 snn beug

    public IA(Gauffre g, int niveau) {
        this.gauffre = g;
        this.niveau = niveau;
        this.random = new Random();
    }

    public void jouerCoup() {
        if (niveau == 1) {
            jouerAleatoire();
        }
        if(niveau==2){
            jouerFacile();
        }else if (niveau == 3) {
            jouerMinimax();
        }
    }

    private ArrayList<Point> casesJouables(Gauffre g){
        //Recup toutes les cases jouables (non mangees sauf poison)
        ArrayList<Point> coupsPossibles = new ArrayList<>();
        for (int j = 0; j < g.get_colonne(); j++) {
            for (int i = 0; i < g.get_ligne(); i++) {
                if (g.peut_manger(j, i)) {
                    coupsPossibles.add(new Point(j, i));
                }
            }
        }
        return coupsPossibles;
    }
    // niv1 : Jouer un coup aléatoire
    private void jouerAleatoire() {
        ArrayList<Point> coupsPossibles=casesJouables(gauffre);
        // coup choisi random
        int indexAleatoire = random.nextInt(coupsPossibles.size());
        Point coupChoisi = coupsPossibles.get(indexAleatoire);

        //play
        gauffre.manger(coupChoisi.x, coupChoisi.y,true);
    }

    private Gauffre copieGauffre() {
        Gauffre copie = new Gauffre(gauffre.get_ligne(), gauffre.get_colonne());
        int[] etat = gauffre.getGauffre();
        for (int i = 0; i < etat.length; i++) {
            copie.setGauffre(i, etat[i]);
        }
        return copie;
    }


    //niv2
    private void jouerFacile(){
        ArrayList<Point> coupsPossibles=casesJouables(gauffre);
        //essayer de trouver un coup gagant (adv va directement faire 0,0 apres)
        for(Point coup : coupsPossibles){
            Gauffre gauffreCop=copieGauffre();
            gauffreCop.manger(coup.x,coup.y,false);
            ArrayList<Point> coupsCop=casesJouables(gauffreCop);
            if (coupsCop.size() == 1) {
                //coup gagnant
                gauffre.manger(coup.x, coup.y,true);
                return;
            }
        }
        int indexAleatoire = random.nextInt(coupsPossibles.size());
        Point coupChoisi = coupsPossibles.get(indexAleatoire);
        if(coupsPossibles.size()!=1){
            while(coupChoisi.x==0 && coupChoisi.y==0){
                indexAleatoire = random.nextInt(coupsPossibles.size());
                coupChoisi = coupsPossibles.get(indexAleatoire);
            }
            gauffre.manger(coupChoisi.x, coupChoisi.y,true);
        }
        else{

            //si jamais il ne reste quun coup perdant on le joue quand meme
            gauffre.manger(0, 0,true);
        }}


    //ici minimax
    private void jouerMinimax() {
        Point meilleurCoup = bestchoix();
        if (meilleurCoup != null) {
            gauffre.manger(meilleurCoup.x, meilleurCoup.y,true);
        }}

    private Point bestchoix() {
        int meilleurScore = -INFINITY;
        Point meilleurCoup = null;

        for (int j = 0; j < gauffre.get_colonne(); j++) {
            for (int i = 0; i < gauffre.get_ligne(); i++) {
                if (gauffre.peut_manger(j, i)) {
                    int[] etatinit = gauffre.getGauffre().clone();
                    gauffre.manger(j, i,false);

                    if (estTermine()) {
                        System.arraycopy(etatinit, 0, gauffre.getGauffre(), 0, etatinit.length);
                        return new Point(j, i); // On joue ce coup gagnant
                    }

                    int score = minimax(1, false);
                    System.arraycopy(etatinit, 0, gauffre.getGauffre(), 0, etatinit.length);

                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = new Point(j, i);
                    }
                }
            }
        }
        return meilleurCoup;
    }


    private int minimax(int profondeur, boolean estMax) {
        if (profondeur >= MAX_PROFONDEUR || estTermine()) {
            return evaluer(profondeur);
        }

        if (estMax) {
            int meilleurScore = -INFINITY;
            for (int j = 0; j < gauffre.get_colonne(); j++) {
                for (int i = 0; i < gauffre.get_ligne(); i++) {
                    if (gauffre.peut_manger(j, i)) {
                        int[] etatInitial = gauffre.getGauffre().clone();
                        gauffre.manger(j, i,false);
                        int score = minimax(profondeur + 1, false);
                        meilleurScore = Math.max(meilleurScore, score);
                        System.arraycopy(etatInitial, 0, gauffre.getGauffre(), 0, etatInitial.length);
                    }
                }
            }
            return meilleurScore;
        }

        else {
            int meilleurScore = INFINITY;
            for (int j = 0; j < gauffre.get_colonne(); j++) {
                for (int i = 0; i < gauffre.get_ligne(); i++) {
                    if (gauffre.peut_manger(j, i)) {
                        int[] etatInitial = gauffre.getGauffre().clone();
                        gauffre.manger(j, i,false);
                        int score = minimax(profondeur + 1, true);
                        meilleurScore = Math.min(meilleurScore, score);
                        System.arraycopy(etatInitial, 0, gauffre.getGauffre(), 0, etatInitial.length);
                    }
                }
            }
            return meilleurScore;
        }
    }

    private boolean estTermine() {
        for (int j = 0; j < gauffre.get_colonne(); j++) {
            for (int i = 0; i < gauffre.get_ligne(); i++) {
                if (gauffre.peut_manger(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int evaluer(int profondeur) {
        if (estTermine()) {
            return (profondeur % 2 == 0) ? -1000 + profondeur : 1000 - profondeur;
        }

        int coupsPossibles = 0;
        for (int j = 0; j < gauffre.get_colonne(); j++) {
            for (int i = 0; i < gauffre.get_ligne(); i++) {
                if (gauffre.peut_manger(j, i)) {
                    coupsPossibles++;
                }
            }
        }

        return (profondeur % 2 == 0) ? coupsPossibles : -coupsPossibles;
    }


}