package logika;

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author     Jarmila Pavlickova, Luboš Pavlíček, Veronika Hniličková
 * @version ZS 2017/2018
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    //NAZEV je konstanta k příkazu
    private HerniPlan plan;
    private Kapsa kapsa;
    private Hra hra;

    /**
     *  Konstruktor třídy
     *  
     *  @param         herní plán, ve kterém se bude ve hře "chodit" 
     *                 kapsa, která se využívá pro "nošení" věcí s sebou
     */    
    public PrikazJdi(HerniPlan plan, Kapsa kapsa, Hra hra) {
        this.plan = plan;
        this.kapsa = plan.getKapsa();
        this.hra = hra;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam chceš jít? Musíš zadat jméno východu.";
        }
        String smer = parametry[0];
        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odtud jít nedá!";
        }
        // pokud zadá neexistující prostor
        if (sousedniProstor.getNazev().equals("psí_doupě")){
            // pokud chce vejít do psího doupěte      
            if(kapsa.obsahujeVec("obojek") == true && kapsa.obsahujeVec("provaz") == true
            && kapsa.obsahujeVec("flétna") == true){
                plan.ochocitPsa();
                plan.setAktualniProstor(sousedniProstor);
                String popisek = sousedniProstor.dlouhyPopis() + "\n"
                    + "Výborně! Díky hře na flétnu jsi ochočil/a krvelačného psa."
                    + "\n" + "Šašek si s ním může hrát a nebude potřebovat tvou společnost.";
                return popisek;
            }
            // pokud vejde do psího doupěte a má u sebe 3 potřebné věci
            hra.setKonecHry(true);
            return "Bez patřičného vybavení to nepůjde! Prohrál/a jsi! Sežral tě hrozný pes!";
            // konec hry se nastaví na true
            // pokud vejde bez vybavení
        }

        if (sousedniProstor.getNazev().equals("poklop")){
            plan.setAktualniProstor(sousedniProstor);
            return "\n" + "Dostal/a jsi se k poklopu!" + "\n"
            + "Znamená to, že jsi nalezl/a tajný východ!" + "\n";
        }
        else {
            plan.setAktualniProstor(sousedniProstor);
            return sousedniProstor.dlouhyPopis();
        }
        // pokud chce jít do jiného možného prostoru
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
