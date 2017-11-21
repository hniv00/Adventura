/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy PrikazSeber představují vložení sbírané věci do kapsy.
 *
 * @author hniv00
 * @version ZS 2017/2018
 * 
 */
public class PrikazSeber implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "seber";
    private HerniPlan plan;
    private Kapsa kapsa;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     */
    public PrikazSeber(HerniPlan plan, Kapsa kapsa)
    {
        this.plan = plan;
        this.kapsa = plan.getKapsa();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     *  Provádí příkaz "seber". Nejdřív hledá sbíranou věc v prostoru, pak jí
     *  sebere a vloží do kapsy.
     *  Ale jen pokud je přenositelná a v kapse je místo.
     *
     *@param    parametr obsahuje název sbírané věci
     *@return   zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry){
        if (parametry.length == 0){
            return "Co bys chtěl sebrat? Musíš zadat název věci.";
        }  
        // pokud nezadá sbíranou věc
        String nazevSbiraneho = parametry[0];
        Prostor aktualni = plan.getAktualniProstor();
        Vec sbirana = aktualni.odeberVec(nazevSbiraneho);
        if(sbirana == null){
            return "Taková věc se tu nevyskytuje.";
        } 
        // pokud zadá neexistující věc
        else{
            if(sbirana.jePrenositelnost()){
                // věc lze přenést
                if(kapsa.vlozVec(sbirana)){
                    plan.notifyObservers();
                    return "Sebral/a jsi " + sbirana.getNazev();
                }
                aktualni.vlozVec(sbirana);
                plan.notifyObservers();
                return "Kapsy už máš plné. Nejprve se zbav zbytečností.";
                // pokud se nevejde, musí se vrátit do prostoru
                // pokud ano, dá věc do kapsy
            }  
            else{
                aktualni.vlozVec(sbirana);
                plan.notifyObservers();
                return "Tohle si odtud vzít nemůžeš.";
                // věc není přenositelná
            }  
        }  

    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return    název příkazu
     */
    @Override
    public String getNazev()
    {
        return NAZEV;
        //metoda stejná pro všechny příkazy :-)
    }
}
