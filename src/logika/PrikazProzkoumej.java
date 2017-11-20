package logika;
import java.util.*;

/**
 * Příkaz prozkoumej má za úkol prohledat zadané věci.
 * 
 * @author  Veronika Hniličková
 * @version LS 2015/2016, květen
 */
public class PrikazProzkoumej implements IPrikaz
{
    private static final String NAZEV = "prozkoumej";
    private HerniPlan plan;
    private Map<String, Vec> veci;
    private Vec vec;

    /**
     *  Konstruktor třídy
     *  
     *  @param plan herní plán, ve kterém se bude zkoumat
     */    
    public PrikazProzkoumej(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     * Metoda vrací text, podle podmínek.
     * Pokud chybí zadaný název věci, nic se neprozkoumá.
     * Pokud hráč napíše věc, která se v prostoru nevyskytuje, taktéž.
     * Pokud chce znovu prozkoumat již prozkoumanou věc, taktéž.
     * Pokud se podívá do skříně, vloží se do prostoru všechny věci zevnitř skříně.
     * Pokud zadá věc, kterou lze prozkoumat, pak se prozkoumáno změní na true
     * a vypíší se věci uvnitř.
     * 
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (co chce prozkoumat)
            return "Co bys chtěl/a prozkoumat?";
        }
        String nazevZkoumaneho = parametry[0];
        Prostor aktualni = plan.getAktualniProstor();
        Vec zkoumana = aktualni.najdiVecVMistnosti(nazevZkoumaneho);
        if(zkoumana == null){
            return "Taková věc se tu nevyskytuje.";
        } 
        // pokud zadá neexistující věc
        if(zkoumana.jeProzkoumana() == true){
            return "Tato věc už byla prozkoumaná.";
        }
        // pokud zadá věc, kterou už prozkoumal
        else{
            if(zkoumana.getNazev().equals("skříň"))
            {
                veci = zkoumana.getSeznamVeci();
                for (Vec vec : veci.values())
                {
                    aktualni.vlozVec(vec);
                }
            }
            plan.notifyObservers();
            zkoumana.prozkoumano(true);
            return zkoumana.popisProzkoumej();
        }  
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
