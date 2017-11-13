/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import logika.HerniPlan;
import logika.IHra;
import logika.Kapsa;
import utils.Observer;

/**
 *
 * @author lenovo
 */
public class SeznamObrazku implements Observer {
    
    private IHra hra;
    // private ImageView obsahKapes;
    // private TilePane panelSObrazkem;
    private TilePane seznamObrazku;
    private Kapsa kapsa;
    private HerniPlan herniPlan;
    
    public SeznamObrazku(IHra hra) {
        this.hra = hra;
        this.kapsa = kapsa;
        this.herniPlan = herniPlan;
        hra.getHerniPlan().registerObserver(this);
        herniPlan.getKapsa().registerObserver(this);
        init();
    }
    
    private void init(){
        
        TilePane seznamObrazku = new TilePane();
        seznamObrazku.setHgap(8);
        seznamObrazku.setPrefColumns(4);
        for (int i = 0; i < 20; i++) {
            seznamObrazku.getChildren().add(new ImageView(...obsahKapes));
        }
        
    }
    
    public void novaGame(IHra novaHra){
        hra.getHerniPlan().removeObserver(this);
        hra = novaHra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    @Override
    public void update() {
        
    }

}
