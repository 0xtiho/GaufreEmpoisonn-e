import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Historique {
    private Stack<Point> annule;
    private Queue<Point> refait;
    public Historique() {
        annule = new Stack<>();
        refait = new LinkedList<>();
    }
    public boolean annule() {
        if(peut_annule()){
            Point p =annule.pop();
            refait.add(p);
            return true;
        }
        return false;

    }
    public Point refait() {
        Point p=null ;

        if (peut_refaire()) {
            p=refait.poll();
            annule.push(p);
        }
        return p;
    }

    public Stack<Point> getAnnule() {
        return annule;
    }

    public void enleve_refait(){
        if(peut_refaire()){
            refait.clear();
        }

    }
    private boolean peut_annule(){
        if(annule.isEmpty()){
            return false;
        }
        return true;
    }
    private boolean peut_refaire(){
        if(refait.isEmpty()){
            return false;
        }
        return true;
    }
    public void ajoute(Point p){
        annule.push(p);
    }


}
