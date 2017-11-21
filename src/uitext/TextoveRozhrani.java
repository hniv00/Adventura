package uiText;

import java.util.Scanner;
import logika.IHra;
import java.io.*;
/**
 *  Class TextoveRozhrani
 * 
 *  Toto je uživatelského rozhraní aplikace Adventura
 *  Tato třída vytváří instanci třídy Hra, která představuje logiku aplikace.
 *  Čte vstup zadaný uživatelem a předává tento řetězec logice a vypisuje odpověď logiky na konzoli.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Veronika Hniličková
 * @version ZS 2017/2018
 */

public class TextoveRozhrani {
    private IHra hra;

    /**
     *  Vytváří hru.
     */
    public TextoveRozhrani(IHra hra) {
        this.hra = hra;
    }

    /**
     *  Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     *  příkazu od hráče do konce hry (dokud metoda konecHry() z logiky nevrátí
     *  hodnotu true). Nakonec vypíše text epilogu.
     */
    public void hraj() {
        System.out.println(hra.vratUvitani());

        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.

        while (!hra.konecHry()) {
            String radek = prectiString();
            System.out.println(hra.zpracujPrikaz(radek));
        }

        System.out.println(hra.vratEpilog());
    }

    public void hrajZeSouboru(String nazevSouboru) {
        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry
        try(BufferedReader ctecka = new BufferedReader(new FileReader(nazevSouboru)))
        {
            // FileReader obalíme BufferedReaderem
            // a ukládáme ho do čtečky
            System.out.println(hra.vratUvitani());
            // uvítání do hry
            String radek = ctecka.readLine();
            while (!hra.konecHry() && radek != null) {
                System.out.println("*" + radek + "*");
                // mezi hvězdičkami je to, co se načte ze souboru
                System.out.println(hra.zpracujPrikaz(radek));
                radek = ctecka.readLine();
            }
            System.out.println(hra.vratEpilog());
        }
        // vyzkoušej blok try
        // pokud dojde k výjimce, následuje blok catch
        catch(FileNotFoundException e){
            System.out.println("Soubor nenalezen " + e);
        }
        // výjimkou je FileNotFoundException
        // napíšeme, že se soubor nenašel
        // to v případě, že do Main - Start napíšeme například {"prikazy.txt"}
        // ale soubor {"prikazy.txt"} neexistuje
        catch(IOException e){
            System.out.println("Problém se vstupem " + e);
        }
        // tato výjimka by snad ani neměla nastat
        // ale BlueJ vyžaduje její ošetření
    }

    /**
     *  Metoda přečte příkaz z příkazového řádku
     *
     *@return    Vrací přečtený příkaz jako instanci třídy String
     */
    private String prectiString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

}
