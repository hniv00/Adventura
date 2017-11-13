/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import logika.IHra;
import utils.Observer;

/**
 *
 * @author lenovo
 */
public class SeznamObrazku implements Observer {
    
    private IHra hra;
    private JPanel obsahKapes;
    private JPanel panelSObrazkem;
    
    public SeznamObrazku(IHra hra) {
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
    }
    
    private void init(){
        
        obsahKapes = new JPanel(new GridLayout(2,1));
        panelSObrazkem.add(obsahKapes);
        obsahKapes.setBorder(BorderFactory.createTitledBorder("Obsah kapes"));
        
    }
    
    public void novaGame(IHra novaHra){
        hra.getHerniPlan().removeObserver(this);
        hra = novaHra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
