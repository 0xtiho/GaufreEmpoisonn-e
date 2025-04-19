import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controlannule implements ActionListener {
    private Vue vue;
    private Gauffre g;
    public controlannule(Vue v,Gauffre g){
        this.vue=v;
        this.g=g;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        g.annule();
        vue.redessine();
    }
}