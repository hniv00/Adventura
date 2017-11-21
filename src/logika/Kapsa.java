package logika;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import utils.Observer;

/*******************************************************************************
 * Třída Kapsa představuje inventář s omezenou kapacitou
 * Umožňuje manipulaci s věcmi.
 *
 * @author    Veronika Hniličková
 * @version   ZS 2017/2018
 * 
 */
public class Kapsa
{
    //== Datové atributy (statické i instancí)=====================================
    private Map <String, Vec> veci;   // seznam věcí v kapse
    private static final int KAPACITA = 4 ;   // maximální počet věcí v kapse
    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor. Vytváří kapsu věcí jako seznam.
     */
    public Kapsa()
    {
        veci = new HashMap<String,Vec>();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     *  Metoda, která najde věc.
     *  Parametrem je věc, kterou chceme získat.
     *  
     *  @return získaná věc
     */     
    public Vec getVec(String nazev) {
        return veci.get(nazev);
    }
 
     /*
     *  Vrací seznam věcí z kapsy.
     */
    public Map <String, Vec> vratKapsu()
    {
        return this.veci;
    }

    /**
     *  Metoda zjistí, zda se věc vejde do kapsy.
     *  
     *  @return vrátí true, pokud ano
     */
    public boolean jeMistoVKapse()
    {
        return (veci.size() < KAPACITA);
    }

    /**
     * Metoda přidá věc do kapsy, pokud v ní je místo.
     * 
     * @param   věc, která se má přidat do kapsy
     * @return  true, pokud se věc podaří přidat do kapsy
     */

    public boolean vlozVec(Vec vec)
    {
        if(jeMistoVKapse() && (vec.jePrenositelnost())) {
            veci.put(vec.getNazev(), vec);

            return true;
        }
        return false;
    }

    /**
     * Metoda odebere věc z kapsy, pokud se v ní nacházela.
     * 
     * @param   věc, která se má odebrat
     * @return  true, pokud se věc podaří odebrat
     */
    public boolean odeberVec(String nazev) {
        boolean odebrana = false;
        if(veci.containsKey(nazev) && veci.get(nazev).jePrenositelnost()) {
            veci.remove(nazev);
            odebrana = true;
        }
        return odebrana;
    }

    /**
     * Metoda vyhodí věc z kapsy.
     * 
     * @param  parametrem je věc, která sa má odebrat
     * @return jméno vyhozené věci, pokud se ji podařilo vyhodit
     */   
    public Vec vyhodVec(String nazev){
        Vec vyhozenaVec = null;
        if (veci.containsKey(nazev)) {
            vyhozenaVec = veci.get(nazev);
            veci.remove(nazev);
        }
        return vyhozenaVec;  
    } 

    /**
     *  Metoda zjišťuje, zda se daná věc vyskytuje v kapse.
     *  
     *  @param   je věc, na kterou se ptáme
     *  @return  true/false
     */   
    public boolean obsahujeVec(String nazev) {
        if (this.veci.containsKey(nazev)) {
            return true;
        }
        return false;
    }    

    /**
     *  Metoda zjistí a vypíše obsah batohu, jednotlivé věci jsou odděleny mezerou.
     *  
     *  @return obsah
     */   
    public String obsahKapes() {
        String obsah = "";
        for (String nazev : veci.keySet()) {
            obsah += " " + nazev;
        }
        if (obsah.length() == 0) {
            obsah = "Až na pár drobků máš kapsy úplně prázdné.";
        }
        else {
            obsah = "Po kapsách nosíš: "+obsah;
        }
        return obsah;
    }
    
    
}
