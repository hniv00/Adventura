package logika;

/**
 * Příkaz mluv umožňuje hráči rozmlouvat s postavami.
 * 
 * @author hniv00
 * @version ZS 2017/2018
 * 
 */
public class PrikazMluv implements IPrikaz
{
    private static final String NAZEV = "mluv";
    private HerniPlan plan;
    private Hra hra;
    private Prostor prostor;
    private Kapsa kapsa;

    /**
     * Konstruktor.
     *
     * @param plan
     * @param hra
     * @param kapsa
     */
    public PrikazMluv(HerniPlan plan, Hra hra, Kapsa kapsa)
    {
        this.plan = plan;
        this.hra = hra;
        this.prostor = prostor;
        this.kapsa = kapsa;
    }

    /**
     * Provadi prikaz "mluv". Mluvi se zadanou postavou.
     * Proslovy se mění dle situace.
     *
     * @param       jmeno postavy, se kterou chceme mluvit
     * @return      zprava, kterou vypise hra hraci
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (osoba), tak ....
            return "Zadej jméno postavy, se kterou chceš hovořit.";
        }

        String jmenoPostavy = parametry[0];

        Prostor aktualni = plan.getAktualniProstor();
        Postava postava = aktualni.najdiPostavu(jmenoPostavy);

        if (postava == null) {
            return jmenoPostavy + " se v tomto prostoru nenachází.";
        }       

        if(postava.getJmeno().equals("šašek") && kapsa.obsahujeVec("obojek")
        && kapsa.obsahujeVec("provaz") && kapsa.obsahujeVec("flétna")&& !plan.jeOchoceny()){
            return postava.getProslov2();
        }

        if(postava.getJmeno().equals("šašek") && plan.jeOchoceny()){
            aktualni.vlozVec(new Vec("šaškova_čepice", true, "cepice.png"));
            plan.notifyObservers();
            return postava.getProslov3();
        }

        return postava.getProslov1();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
