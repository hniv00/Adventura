/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import java.util.*;
/*******************************************************************************
 * @author    Veronika Hniličková
 * @version   LS 2015/2016, květen
 */
public class Vec
{
    //== Datové atributy (statické i instancí)======================================
    private String nazev;
    private boolean prenositelnost;
    private boolean prozkoumana = false;
    private Map<String, Vec> veci;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor pro věc, která má vlastnosti:
     *  název a přenositelnost
     *  pokud je přenositelnost true, lze vložit do kapsy
     */
    public Vec(String nazev, boolean prenositelnost)
    {
        this.nazev = nazev;
        this.prenositelnost = prenositelnost;
        veci = new HashMap<String,Vec>();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací jméno věci.
     * 
     * @return String název věci
     */
    public String getNazev(){
        return nazev;
    }

    /**
     * Metoda zjišťuje, zda je věc přenositelná.
     * 
     * @return vrací true, pokud se věc dá přenést
     */
    public boolean jePrenositelnost(){
        return prenositelnost;
    }
    
     /**
     * Metoda nastavuje přenositelnost.
     * 
     * @return vrací přenositelnost
     */
    public void setPrenositelnost(boolean prenositelnost){
        this.prenositelnost = prenositelnost;
    }

    /**
     * Metoda zjišťuje, zda je věc prozkoumaná.
     * 
     * @return vrací true, pokud je věc prozkoumaná
     */
    public boolean jeProzkoumana(){
        return prozkoumana;
    }

    /**
     * Metoda zjišťuje, zda bylo prozkoumáno.
     */
    public void prozkoumano(boolean prozkoumana){
        this.prozkoumana = prozkoumana;    
    }

    public void vlozVec(Vec neco){
        veci.put(neco.getNazev(), neco);
    }

    /***************************************************************************
     * Metoda vrátí true, pokud byla věc prozkoumaná
     * a obsahuje námi hledanou věc
     */
    public boolean obsahujeVec(String jmeno) {
        return prozkoumana && veci.containsKey(jmeno);
    }

    /***************************************************************************
     * Metoda vybere věc, pokud je přenositelná.
     * 
     * @return      vec
     */
    public Vec vyberVec(String jmeno) {
        Vec vec = null;
        if (prozkoumana && veci.containsKey(jmeno)) {
            vec = veci.get(jmeno);
            if (vec.jePrenositelnost()) {
                veci.remove(jmeno);
            }
        }
        return vec;
    }

    /***************************************************************************
     * Pokud je věc obyčejná, hráč nic nevykoumá.
     * Pokud je k prozkoumání, vypíšou se mu věci uvnitř.
     * Věci uvnitř prozkoumané věci se vloží do prostoru.
     * 
     * @return popis, zda bylo vloženo
     */
    public String popisProzkoumej() {
        if (veci.isEmpty()) {
            return "Pozorně sis prohlédl/a "+nazev+", ale nic z toho nevykoukáš.";
        }
        String popis = "Prouzkoumal/a jsi "+nazev+" a uvnitř jsi objevil/a: ";
        for (String jmeno : veci.keySet()) {
            popis += jmeno + " ";
        }

        return popis;
    }

    /***************************************************************************
     * @return  String název věci
     */
    public String toString() {
        return nazev;
    }

    /***************************************************************************
     * Metoda vrací seznam věcí typu HashMap.
     * 
     * @return  seznam věcí
     */    
    public Map getSeznamVeci()
    {
        return veci;
    }
    
     /**
     * Metoda vrací seznam jako řetězec.
     *
     * @return seznam věcí
     */
    public String getSeznamVeci2() {
        String seznam = "";
        for (String nazev : veci.keySet()) {
            seznam += nazev + " ";
        }
        return seznam;
    }
}
