package logika;

/**
 * Metoda PrikazUkazKapsy ukáže hráči, co všechno má schované po kapsách.
 * 
 * @author  Veronika Hniličková 
 * @version LS 2015/2016, květen
 */
public class PrikazUkazKapsy implements IPrikaz {
    private static final String NAZEV = "ukaž_kapsy";
    private static final String POPIS = "ukáže obsah kapes";
    private Kapsa kapsa;

    /**
     * Konstruktor třídy
     */
    public PrikazUkazKapsy(Kapsa kapsa)
    {
        this.kapsa = kapsa;
    }

    /**
     * Metoda vraci seznam věcí (názvy), které jsou v kapse 
     * 
     * @param parametr se pro tento příkaz žádný nepoužívá
     * 
     * @return obsah kapes
     */
    public String proved(String... parametry) {
        return kapsa.obsahKapes();    
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return     nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }

    /**
     *  Metoda vrací popis příkazu
     *  
     *  @return     popis
     */
    public String getPopis() {
        return POPIS;
    }
}
