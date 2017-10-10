package logika;

/**
 *  Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *@author     Jarmila Pavlickova, Luboš Pavlíček, Veronika Hniličková
 *@version    LS 2015/2016, květen
 *  
 */
class PrikazNapoveda implements IPrikaz {
    private static final String NAZEV = "nápověda";
    private SeznamPrikazu platnePrikazy;

    /**
     *  Konstruktor třídy
     *  
     *  @param   seznam příkazů, které je možné ve hře použít,
     *           aby je nápověda mohla zobrazit uživateli
     */    
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }

    /**
     *  Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     *  vcelku primitivní zpráva a seznam dostupných příkazů.
     *  
     *  @return napoveda ke hre
     */
    @Override
    public String proved(String... parametry) {
        return "\n"
        + "Užij si dobrodružství v katakombách starého hradu.\n"
        + "Tvým úkolem je dostat se pryč z temného podzemí.\n"
        + "Zkus to zde prozkoumat a snad najdeš indicie, které ti pomohou.\n"
        + "\n"
        + "Můžeš zadat tyto příkazy:\n"
        + platnePrikazy.vratNazvyPrikazu();
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
