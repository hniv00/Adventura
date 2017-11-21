package logika;

import java.util.*;
/**
 * Instance třídy postava představují postavy, které se vyskytují ve hře.
 * 
 * @author hniv00
 * @version ZS 2017/2018
 * 
 */

public class Postava
{
    private String jmeno;
    private String proslov1;
    private String proslov2;
    private String proslov3;
    private boolean ochocena;
    private Map<String, Postava> seznamPostav;

    /***************************************************************************
     *  Konstruktor pro postavu.
     */
    public Postava(String jmeno, String proslov1, String proslov2, String proslov3, boolean ochocena)
    {
        this.jmeno = jmeno;
        this.proslov1 = proslov1;
        this.proslov2 = proslov2;
        this.proslov3 = proslov3;
        this.ochocena = ochocena;
        seznamPostav = new HashMap<String, Postava>();
    }

    /**
     * Metoda vrací jméno postavy.
     * 
     * @return   String jméno postavy.
     */
    public String getJmeno(){
        return jmeno;
    }

    /**
     * Metoda poskytuje proslov postavy.
     * 
     * @return  String první proslov postavy.
     */
    public String getProslov1(){
        return proslov1;
    }

    /**
     * Metoda poskytuje druhý proslov postavy.
     * 
     * @return  String druhý proslov postavy.
     */
    public String getProslov2() {
        return proslov2;
    }

    /**
     * Metoda poskytuje třetí proslov postavy.
     * 
     * @return  String třetí proslov postavy.
     */
    public String getProslov3() {
        return proslov3;
    }

    /**
     *  Metoda zjišťuje, zda je postava ochočená.
     *
     *@return    vrací hodnotu ochocena
     */
    public boolean getOchocena(){
        return ochocena;
    } 

    /**
     *  Metoda nastavuje atribut ochocena.
     *
     *@param    boolean hodnota ochocena
     */
    public void setOchocena(boolean ochocena){
        this.ochocena = ochocena;
    }

    /***************************************************************************
     * Metoda vrací seznam postav typu Map (Hash).
     * 
     * @return  seznam postav
     */    
    public Map getSeznamPostav()
    {
        return seznamPostav;
    }
}
