import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        vue.repaint();
    }
}
