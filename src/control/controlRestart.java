package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Gauffre;
import view.Vue;

public class controlRestart implements ActionListener {
    private Vue vue;
    private Gauffre g;
    public controlRestart(Vue v,Gauffre g){
        this.vue=v;
        this.g=g;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        g.resetgame();
        vue.redessine();
    }
}
