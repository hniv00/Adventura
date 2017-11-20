/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.Hra;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 *
 * @author hniv00
 */
public class Mapa extends AnchorPane implements Observer {

    private IHra hra;
    private Circle tecka;

    /*
    * Konstrukce pro třídu Mapa.
    */
    public Mapa(IHra hra) {
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
    }

    /*
    * Metoda init zobrazí mapu a červené kolečko podle toho, kde v prostoru se hráč aktuálně nachází.
    */
    private void init() {

        ImageView obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.jpg"), 400, 200, false, true));

        tecka = new Circle(10, Paint.valueOf("red"));

//        this.setTopAnchor(tecka, 0.0);
//        this.setLeftAnchor(tecka, 0.0);

        this.getChildren().addAll(obrazekImageView, tecka);
        update();
    }
    
    /*
    * Reset mapy při otevření nové hry.
    */
    public void newGame(IHra novaHra){
        hra.getHerniPlan().removeObserver(this);
        hra = novaHra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    /*
    * Metoda aktualizuje tečku na mapě.
    */
    @Override
    public void update() {
        this.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosTop());
        this.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosLeft());
    }

}