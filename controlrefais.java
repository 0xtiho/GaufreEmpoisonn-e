import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class controlrefais implements ActionListener {
    private Vue vue;
    private Gauffre g;

    public controlrefais(Vue v, Gauffre g){
        this.vue=v;
        this.g=g;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        g.refait();
        vue.redessine();
    }
}