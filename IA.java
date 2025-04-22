import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class IA {
    private Gauffre gauffre;
    private int niveau; // 1 = aleatoire, 2 = non perdant/gagnant, 3 = minimax
    private Random random;
    private static final int INFINITY = 1000; // Pour Minimax
    private static final int MAX_PROFONDEUR = 6; //ajustiwha selon taille grille ! attention 16 si <= 4X4 snn beug 

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
        gauffre.manger(coupChoisi.x, coupChoisi.y);
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
            gauffreCop.manger(coup.x,coup.y);
            ArrayList<Point> coupsCop=casesJouables(gauffreCop);
            if (coupsCop.size() == 1) {
                //coup gagnant
                gauffre.manger(coup.x, coup.y);
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
            gauffre.manger(coupChoisi.x, coupChoisi.y);
        }
        else{

            //si jamais il ne reste quun coup perdant on le joue quand meme
            gauffre.manger(0, 0);
        }}


    //ici minimax 
    private void jouerMinimax() {
        Point meilleurCoup = bestchoix();
        if (meilleurCoup != null) {
            gauffre.manger(meilleurCoup.x, meilleurCoup.y);
        }} 

    private Point bestchoix() {
        int meilleurScore = -INFINITY; //initialise le score tres bas pour monter au max
        Point meilleurCoup = null; //jai rien ! je stock un coup meilleur 

        for (int j = 0; j < gauffre.get_colonne(); j++) {
            for (int i = 0; i < gauffre.get_ligne(); i++) {
                if (gauffre.peut_manger(j, i)) {
                    int[] etatinit = gauffre.getGauffre().clone();
                    gauffre.manger(j, i);

                    if (estTermine()) {
                        System.arraycopy(etatinit, 0, gauffre.getGauffre(), 0, etatinit.length); // On remet l'état initial
                        return new Point(j, i); // On joue ce coup gagnant
                    }

                    //sinon on evalue le coup avec minimax 
                    int score = minimax(1, false); //c le joueur qui joue
                    System.arraycopy(etatinit, 0, gauffre.getGauffre(), 0, etatinit.length);

                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = new Point(j, i); 
                        //on garde en memoire le meilleur coup et son score
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

        if (estMax) { //ia joue maximise son score 
            int meilleurScore = -INFINITY;
            for (int j = 0; j < gauffre.get_colonne(); j++) {
                for (int i = 0; i < gauffre.get_ligne(); i++) {
                    if (gauffre.peut_manger(j, i)) {
                        int[] etatInitial = gauffre.getGauffre().clone();
                        gauffre.manger(j, i);
                        int score = minimax(profondeur + 1, false);
                        //Appelle recurs minimax pour evaluer la position après le coup simulé
                        //prof +1 passe au niveau suivant
                        //score val de cette branche de l'arbre
                        meilleurScore = Math.max(meilleurScore, score);
                        System.arraycopy(etatInitial, 0, gauffre.getGauffre(), 0, etatInitial.length);
                        //restore l'état initial de la gauffre après le coup simulé
                    }
                }
            }
            return meilleurScore;
        }

        else { //humain minimise score ia 
            int meilleurScore = INFINITY;
            for (int j = 0; j < gauffre.get_colonne(); j++) {
                for (int i = 0; i < gauffre.get_ligne(); i++) {
                    if (gauffre.peut_manger(j, i)) {
                        int[] etatInitial = gauffre.getGauffre().clone();
                        gauffre.manger(j, i);
                        int score = minimax(profondeur + 1, true); 
                        meilleurScore = Math.min(meilleurScore, score); //ici n minimise 
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

    //La fonction evaluer attribue un score à une position dans la grille pour guider l’IA dans ses choix via minimax
    private int evaluer(int profondeur) {
        if (estTermine()) {
            return (profondeur % 2 == 0) ? -1000 + profondeur : 1000 - profondeur;
            //si partie terminé a profondeur pair (ia qui joue) defaite pour lia score negatif 
        }

        int coupsPossibles = 0;
        for (int j = 0; j < gauffre.get_colonne(); j++) {
            for (int i = 0; i < gauffre.get_ligne(); i++) {
                if (gauffre.peut_manger(j, i)) {
                    coupsPossibles++;
                }
            }
        }

        return (profondeur % 2 == 0) ? coupsPossibles : -coupsPossibles; //postif favorable pour ia, negatif defavorable pour ia 
    }


}