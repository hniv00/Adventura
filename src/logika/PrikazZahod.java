package logika;
/**
 *  Příkaz zahoď vykoná zahození věci z kapsy hráče do aktuálního prostoru.
 *
 * @author hniv00
 * @version ZS 2017/2018
 * 
 */
public class PrikazZahod implements IPrikaz
{
    private static final String NAZEV = "zahoď";
    private HerniPlan plan;
    private Kapsa kapsa;

    /**
     * Konstruktor
     *
     */
    public PrikazZahod(HerniPlan plan, Kapsa kapsa)
    {
        this.plan = plan;
        this.kapsa = plan.getKapsa();
    }

    /**
     * Metoda provádějící příkaz seber.
     * 
     * @param    věc, kterou chceme odhodit
     * @return   zpráva, která se vypíše na konzoli
     */
    @Override
    public String proved(String...parametry) {
        if (parametry.length == 0) {
            // hráč nenapíše, co zahodit
            return "Co bys chtěl/a zahodit?";
        }

        String nazevVeci = parametry[0];
        Prostor aktualniProstor = plan.getAktualniProstor();
        Vec vec = kapsa.getVec(nazevVeci);

        if (vec == null) {
            return "Něco takového v kapse nemáš.";
        }
        // pokud napíše věc, kterou nemá u sebe
        else {
            kapsa.odeberVec(nazevVeci);
            aktualniProstor.vlozVec(vec);
            plan.notifyObservers();
            return "Právě jsi zahodil " + nazevVeci + " do tohoto prostoru.";
        }
        // zadaná věc se vyhodí z kapsy do prostoru
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return    název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}