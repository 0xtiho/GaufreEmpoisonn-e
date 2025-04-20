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
        
        //pour le reste on va rajouter plus tard : niv 2 et 3
    }

    // niv1 : Jouer un coup aléatoire
    private void jouerAleatoire() {
        //Recup toutes les cases jouables (non mangees sauf poison)
        ArrayList<Point> coupsPossibles = new ArrayList<>();
        for (int j = 0; j < gauffre.get_colonne(); j++) {
            for (int i = 0; i < gauffre.get_ligne(); i++) {
                if (gauffre.peut_manger(j, i)) {
                    coupsPossibles.add(new Point(j, i));
                }
            }
        }

        // verification de si il y a des coups possibles
        if (coupsPossibles.isEmpty()) {
            return; 
        }

        // coup choisi random
        int indexAleatoire = random.nextInt(coupsPossibles.size());
        Point coupChoisi = coupsPossibles.get(indexAleatoire);

        //play
        gauffre.manger(coupChoisi.x, coupChoisi.y);
    }
}