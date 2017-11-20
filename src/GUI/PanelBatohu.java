/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logika.HerniPlan;
import logika.Vec;
import utils.Observer;


/**
 *
 * @author hniv00
 */

public class PanelBatohu implements Observer
{
 private HerniPlan plan;
    ListView<Object> list;
    ObservableList<Object> data;

    public PanelBatohu(HerniPlan plan) {
       this.plan = plan;
       plan.registerObserver(this);
        init();
    }

    private void init() {
        list = new ListView<>();
        data = FXCollections.observableArrayList();
        list.setItems(data);
        list.setPrefWidth(200);
        update();
    }
    
     public ListView<Object> getList() {
        return list;
    }
    @Override 
    public void update() 
    {        
        Map<String, Vec> seznam;
        seznam = plan.getKapsa().vratKapsu();
        data.clear();
        for (String x : seznam.keySet()) 
        {
        Vec pomocna = seznam.get(x);
        ImageView obrazek = new ImageView(new Image(main.Main.class.getResourceAsStream("/zdroje/"+pomocna.getObrazek()), 100, 100, false, false));
        data.add(obrazek);
        }
    }
    
    /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     * @param plan
     */
    public void nastaveniHernihoPlanu (HerniPlan plan){
        this.plan = plan;
        plan.registerObserver(this);
        this.update();
    }



}
