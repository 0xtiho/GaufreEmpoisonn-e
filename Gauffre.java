import java.util.ArrayList;

public class Gauffre {

    private ArrayList<ArrayList<Integer>> gauffre;
    private int ligne;
    private int colonne ;
    private final int POISON = 2;
    private final int VIDE = 1;
    private final int CHOCOLAT = 0;




    public Gauffre(int l, int c) {
        this.colonne = c;
        this.ligne = l;
        gauffre = new ArrayList<>();


        for (int i = 0; i < ligne; i++) {
            ArrayList<Integer> ligneGauffre = new ArrayList<>();
            for (int j = 0; j < colonne; j++) {
                ligneGauffre.add(CHOCOLAT);
            }
            gauffre.add(ligneGauffre); // Ajout de la ligne à la gauffre
        }
        gauffre.get(0).set(0, POISON); // Case empoisonnée en (0,0)
    }
    public int  get_ligne(){
        return ligne;
    }
    public int get_colonne(){
        return colonne;
    }

    private boolean peut_manger(int l ,int c){
        return (gauffre.get(l).get(c)== CHOCOLAT || gauffre.get(l).get(c)==POISON);
    }
    public void manger(int l , int c){
        if(peut_manger(l,c)){
            for (int i=l;i<ligne;i++){
                for (int j=c;j<colonne;j++){
                    if(gauffre.get(i).get(j)!=VIDE){
                        gauffre.get(i).set(j,VIDE);
                    }
                    else {
                        break;
                    }
                }
            }
        }
    }

    public void afficher(){
        for (ArrayList<Integer> arr : gauffre){
            for (int i : arr){
                if (i==VIDE){
                    System.out.print(" ");
                }
                else {
                    System.out.print(i +" ");
                }

            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Gauffre gauffre1 = new Gauffre(6,7);
        gauffre1.afficher();
        System.out.println();
        gauffre1.manger(2 ,2);
        gauffre1.afficher();

    }











}
