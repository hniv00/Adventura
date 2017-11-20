/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import main.Main;

/**
 *
 * @author xzenj02
 */
public class MenuLista extends MenuBar{
    
    private IHra hra;
    private Main main;
    private Stage stage;
    
    public MenuLista(IHra hra, Main main){
        this.hra = hra;
        this.main = main;
        this.stage = main.getStage();
        init();
    }
    
    private void init(){
        
        Menu novySoubor = new Menu("Adventura");
        Menu napoveda = new Menu("Help");
        
        MenuItem novaHra = new MenuItem("Nová hra");
        //, new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/ikona.png")))
        
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        MenuItem konecHry = new MenuItem("Konec hry");
        
        novySoubor.getItems().addAll(novaHra, konecHry);
        
        MenuItem oProgramu = new MenuItem("O programu");
        MenuItem napovedaItem = new MenuItem("Nápověda");
        
        napoveda.getItems().addAll(oProgramu, napovedaItem);
        
        this.getMenus().addAll(novySoubor, napoveda);
        
        konecHry.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        novaHra.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                main.start(stage);
            }
        });

        oProgramu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                Alert oProgramuAlert = new Alert(Alert.AlertType.INFORMATION);
                
                oProgramuAlert.setTitle("O programu");
                oProgramuAlert.setHeaderText("Ztracen v katakombách strého hradu");
                oProgramuAlert.setContentText("Úvod do hry: \n" +
                                              "Adventure hra začíná úvodem do děje.\n" + 
                                              "Probudili jste se v podzemí starého hradu.\n" + 
                                              "Nacházíte se v téměř prázdné místnosti. Jste zmatení.\n" + 
                                              "Vaším úkolem je porozhlédnout se po okolí,\n" + 
                                              "zda nenajdete cestu ven.\n" +
                                              "\n" + 
                                              "Cíl hry: \n" +
                                              "Hlavním cílem této hry je dostat se pryč z tajemného\n" + 
                                              "podzemí hradu. Na způsob, jak toho dosáhnout,\n" + 
                                              "musí hráč přijít sám.\n" +
                                              "Máte možnost vcházet do různých prostorů, zkoumat předměty,\n" + 
                                              "odnést si je s sebou apod.\n" +
                                              "\n" + 
                                              "Způsob ovládání hry:\n" +
                                              "Hra disponuje textovým i grafickým uživatelským rozhraním.\n" +  
                                              "K ovládání slouží příkazy zapisované ve spodní části.\n" + 
                                              "Pro zpracování příkazu je nutno stisknout enter.");
                oProgramuAlert.initOwner(main.getStage());
                
                oProgramuAlert.showAndWait();

            }
        }); 
        
        napovedaItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                Stage stage = new Stage();
                stage.setTitle("Nápověda");
                
                WebView webView = new WebView();
                
                webView.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());
                
                stage.setScene(new Scene(webView, 1200, 650));
                stage.show();

            }
        });
        
    }
    
}