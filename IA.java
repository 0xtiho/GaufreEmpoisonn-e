import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class IA {
    private Gauffre gauffre;
    private int niveau; // 1 = aleatoire, 2 = non perdant/gagnant, 3 = minimax
    private Random random;

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
        }

        //pour le reste on va rajouter plus tard : niv 2 et 3
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
        }


    }
}