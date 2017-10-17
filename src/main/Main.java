/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logika.*;
import uiText.TextoveRozhrani;

/**
 *
 * @author xzenj02
 */
public class Main extends Application {

    private TextArea centralText;
    private IHra hra;
    private TextField zadejPrikazTextField;
    
    @Override
    public void start(Stage primaryStage) {
        hra = new Hra();
        BorderPane borderPane = new BorderPane();
        
        /* Centrální text s průběhem hry je ve formátu text are a není editovatelný - nejde mazat. */
        centralText = new TextArea();
        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);
        borderPane.setCenter(centralText);
        
        /* Label s textem Zadej příkaz */
        Label zadejPrikazLabel = new Label("Zadej prikaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Zadání příkazu
        zadejPrikazTextField = new TextField("...");
        zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>(){
           
            @Override
            public void handle(ActionEvent event){
        
            String vstupniPrikaz = zadejPrikazTextField.getText();
            String odpovedHry = hra.zpracujPrikaz(vstupniPrikaz);
            
            centralText.appendText("\n" + vstupniPrikaz);
            centralText.appendText("\n" + odpovedHry + "\n");
            
            if (hra.konecHry()){
                zadejPrikazTextField.setEditable(false);
                centralText.appendText(hra.vratEpilog());
            }
            }
        });
        
        // Obrázek s mapou
        FlowPane obrazekFlowPane = new FlowPane();
        obrazekFlowPane.setPrefSize(200, 100);
        ImageView obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.jpg"), 200, 100, false, true));
        obrazekFlowPane.setAlignment(Pos.CENTER);
        obrazekFlowPane.getChildren().add(obrazekImageView);
        
        
        /* Co všechno se nachází v dolní liště. */
        FlowPane dolniLista = new FlowPane();
        dolniLista.setAlignment(Pos.CENTER);
        dolniLista.getChildren().addAll(zadejPrikazLabel, zadejPrikazTextField);
        
        borderPane.setLeft(obrazekFlowPane);
        borderPane.setBottom(dolniLista);
        
        Scene scene = new Scene(borderPane, 750, 450);

        primaryStage.setTitle("Adventura");

        primaryStage.setScene(scene);
        primaryStage.show();
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
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
    }

}
