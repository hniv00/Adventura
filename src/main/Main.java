/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.Mapa;
import GUI.MenuLista;
import GUI.PanelBatohu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logika.*;
import uiText.TextoveRozhrani;

/**
 *
 * @author hniv00
 * @version LS 2017/2018
 * 
 */
public class Main extends Application {

    private TextArea centralText;
    private IHra hra;
    private TextField zadejPrikazTextArea;
    private Mapa mapa;
    private MenuLista menuLista;
    private PanelBatohu panelBatohu;
    
    private Stage stage;
    
    /*
    * Metoda pro setnutí hry.
    */
    public void setHra(IHra hra) 
    {
        this.hra = hra;
    }
    
    /*
    * Metoda pro spuštění stage a všech grafických komponent.
    */
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        
        hra = new Hra();
        
        mapa = new Mapa(hra);
        menuLista = new MenuLista(hra, this);
        
        BorderPane borderPane = new BorderPane();
        
        // Text s prubehem hry
        centralText = new TextArea();
        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);
        borderPane.setCenter(centralText);
        
        //label s textem zadej prikaz
        Label zadejPrikazLabel = new Label("Zadej příkaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // text area do ktere piseme prikazy
        zadejPrikazTextArea = new TextField("...");
        zadejPrikazTextArea.setOnAction(new EventHandler<ActionEvent>() {

            /*
            * Zpracování příkazů, odpověď hry, vrácení centrálního textu.
            */
            @Override
            public void handle(ActionEvent event) {

                String vstupniPrikaz = zadejPrikazTextArea.getText();
                String odpovedHry = hra.zpracujPrikaz(vstupniPrikaz);
                
                centralText.appendText("\n" + vstupniPrikaz + "\n");
                centralText.appendText("\n" + odpovedHry + "\n");
                
                zadejPrikazTextArea.setText("");
                
                if (hra.konecHry()) {
                    zadejPrikazTextArea.setEditable(false);
                    centralText.appendText(hra.vratEpilog());
                }
            }
        });
        
        //dolni lista s elementy
        FlowPane dolniLista = new FlowPane();
        dolniLista.setAlignment(Pos.CENTER);
        dolniLista.getChildren().addAll(zadejPrikazLabel,zadejPrikazTextArea);
        
        borderPane.setLeft(mapa);
        borderPane.setBottom(dolniLista);
        borderPane.setTop(menuLista);
        
        
        // panel batohu     
        panelBatohu = new PanelBatohu(hra.getHerniPlan());
        borderPane.setRight(panelBatohu.getList());
       
        
        Scene scene = new Scene(borderPane, 1200, 600);
        primaryStage.setTitle("Adventura");

        primaryStage.setScene(scene);
        primaryStage.show();
        zadejPrikazTextArea.requestFocus();
    }

    /*
    * Vrácení centrálního textu.
    */
    public TextArea getCentralText() {
        return centralText;
    }

    /*
    * Vrácení mapy.
    */
    public Mapa getMapa() {
        return mapa;
    }  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        }
        else{
            if (args[0].equals("-txt")) {
                IHra hra = new Hra();
                TextoveRozhrani textHra = new TextoveRozhrani(hra);
                textHra.hraj();
            }
            else{
                System.out.println("Neplatný parametr");
                System.exit(1);
            }
        }
    }

    /**
     * 
     * Metoda vrací stage.
     * 
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

}