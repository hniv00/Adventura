/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.Mapa;
import GUI.MenuLista;
import GUI.PanelBatohu;
import GUI.PanelVeci;
import GUI.PanelVychodu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class Main extends Application{

    private TextArea centralText;
    private IHra hra;
    private TextField zadejPrikazTextArea;
    private Mapa mapa;
    private MenuLista menuLista;
    private PanelBatohu panelBatohu;
    private PanelVychodu panelVychodu;
    private PanelVeci panelVeci;  
    private Stage stage;
    
    /*
    * Metoda pro setnutí hry.
    */
    public void setHra(IHra hra) {
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
        // Hlavní borderPane + spodní odsazení
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(0, 0, 10, 0));
        // Borderpane pro východy a věci v místnosti + odsazení + nadpis
        BorderPane borderPane2 = new BorderPane();
        borderPane2.setPadding(new Insets(10, 10, 10, 10));
        Label header2 = new Label("Věci nacházející se v prostoru a seznam východů: ");
        borderPane2.setTop(header2);
        // Borderpane pro věci v kapse a mapu + nadpis
        BorderPane borderPane3 = new BorderPane();
        borderPane3.setPadding(new Insets(10, 10, 10, 10));
        Label header3 = new Label("Věci v kapsách: ");
        borderPane3.setLeft(header3);
        
        // Text s průbehem hry
        centralText = new TextArea();
        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);
        borderPane.setCenter(centralText);
        
        // Label s textem zadej prikaz
        Label zadejPrikazLabel = new Label("Zadej příkaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Text area do ktere piseme prikazy
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
        
        // dolni lista s elementy
        FlowPane dolniLista = new FlowPane();
        dolniLista.setAlignment(Pos.CENTER);
        dolniLista.getChildren().addAll(zadejPrikazLabel,zadejPrikazTextArea);
        
        borderPane.setBottom(dolniLista);
        borderPane.setTop(menuLista);
        
        /*
        * Panel věcí v prostoru je napravo od centrálního textu nahoře.
        * Panel východů je vpravo nahoře.
        * Panel věcí v kapse je vlevo pod mapou.
        * Mapa je vlevo nahoře.
        */
        
        // panel věcí    
        panelVeci = new PanelVeci(hra.getHerniPlan(),centralText);
        borderPane2.setLeft(panelVeci.getList());
        // panel východů
        panelVychodu = new PanelVychodu(hra.getHerniPlan(),centralText,zadejPrikazTextArea);       
        borderPane2.setRight(panelVychodu.getList());
        borderPane.setRight(borderPane2);
        // panel kapsy
        panelBatohu = new PanelBatohu(hra.getHerniPlan(),centralText);
        borderPane3.setCenter(panelBatohu.getList());
        // panel s mapou
        borderPane3.setTop(mapa);
        borderPane.setLeft(borderPane3);
       
        
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