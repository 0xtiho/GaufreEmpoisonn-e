import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class EtatGauffre {
    private Point mouvement;

    // stocke seulement les changements par rapport a l'etat precedent
    // la clé est l'index de colonne et la valeur est la nouvelle valeur pour cette colonne
    private Map<Integer, Integer> changementsColonnes;

    public EtatGauffre(Point mouvement) {
        this.mouvement = mouvement;
        this.changementsColonnes = new HashMap<>();
    }

    public Point getMouvement() {
        return mouvement;
    }

    public void ajouterChangement(int indexColonne, int nouvelleValeur) {
        changementsColonnes.put(indexColonne, nouvelleValeur);
    }

    // recupere tous les changs
    public Map<Integer, Integer> getChangements() {
        return changementsColonnes;
    }

    @Override
    public String toString() {
        return "Mouvement: (" + mouvement.x + "," + mouvement.y + "), Changements: " + changementsColonnes;
    }
}