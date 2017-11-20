package logika;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Veronika Hniličková
 * @version LS 2017/2018
 */
public class Prostor {

    private String nazev;
    private String popis;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private Map<String,Vec> veci; // <název, objekt jako takový>
    private Map<String, Postava> seznamPostav; // seznam postav
    private double posLeft; // pozice pro zakreslení do mapy
    private double posTop; // x a y souřadnice, které jsou v konstruktoru
    

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala" atp.
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     *          víceslovný název bez mezer
     * @param popis Popis prostoru
     */
    public Prostor(String nazev, String popis, double posLeft, double posTop) {
        this.nazev = nazev;
        this.popis = popis;
        this.posLeft = posLeft;
        this.posTop = posTop;
        vychody = new HashSet<>();
        veci = new HashMap<>();
        seznamPostav = new HashMap<>();
    }

    public double getPosLeft() {
        return posLeft;
    }

    public double getPosTop() {
        return posTop;
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

        return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru chodba.
     * Vychody:
     * kuchyň obývák zahrada
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return 
        "\n" +
        "Jsi v prostoru " + popis + ".\n"
        + popisVychodu() + "\n"
        + getSeznamPostav() + "\n"
        + popisVeci(); 
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: chodba".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "Východy: ";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
            .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
            .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    /**
     * Metoda vkládá věc do seznamu věcí (HashMap).
     *
     *@param    vkládaná Vec vec
     */
    public void vlozVec(Vec neco){
        veci.put(neco.getNazev(),neco);
    }

    /**
     * Odebereme věc z mapy a vrátí se název námi odebrané věci.
     *
     *@param    String název odebírané věci
     *@return   odebraná věc
     */
    public Vec odeberVec(String nazev){
        return veci.remove(nazev);
    }

    /**
     * Odebereme věc z mapy a vrátí se název námi odebrané věci.
     * Metoda vrátí text, jaké věci ze nachází v prostoru,
     * pokud se tam žádné věci nenachází - podmínka, aby se text nezobrazoval
     *
     *@return   výsledný String popis 
     */
    private String popisVeci(){
        String vysledek = "";

        for (String nazev: veci.keySet()) {
            vysledek += " " + nazev;
        }

        if (vysledek.length() == 0) {
            vysledek = "V místnosti nejsou žádné věci.";
        }
        else {
            vysledek = "Nachází se zde následující věci: "+vysledek;
        }

        return vysledek;
    }

    /**
     * Pokud věc není v místnosti, prohledávají se věci v místnosti.
     * Pokud nějaká věc obsahuje věc hledanou, vrací se true.
     * Pokud hledaná věc není nikde v prostoru (ani v "truhle"), vrací se false.
     * V ostatní případech se věc s prostoru vyskytuje, metoda vrací true.
     *
     *@param    String jmeno veci
     *@return   true/false 
     */
    public boolean obsahujeVec(String jmeno) {
        if (najdiVecVMistnosti(jmeno) == null) { // vec není v místnosti
            for (Vec vec : veci.values()) {    // prohledávám věci v místnosti
                if (vec.obsahujeVec(jmeno)) { // když nějaká věc obsahuje věc
                    return true;
                }
            }
            return false;  // věc není ani v prozkoumaných věcech (truhlách)
        }
        else {
            return true;
        }

    }

    /**
     * Metoda vybere věc.
     * 
     *@param    String jmeno veci
     *@return   vybrana vec 
     */
    public Vec vyberVec (String jmeno) {
        Vec vybranaVec = najdiVecVMistnosti(jmeno);
        if (vybranaVec != null && vybranaVec.jePrenositelnost()) {
            veci.remove(vybranaVec);
        }
        else {
            for (Vec vec : veci.values()) { // hledám, zda není v nějaké věci
                vybranaVec=vec.vyberVec(jmeno);
                if (vybranaVec != null) {
                    break;
                }
            }
        }
        return vybranaVec;
    }

    /**
     * Metoda, která hledá věc v pouze v místnosti a ne již uvnitř případných "truhel".
     * 
     *@param    String jmeno veci
     *@return   vec
     */
    public Vec najdiVecVMistnosti(String jmeno) {
        Vec vec = null;
        for (Vec neco : veci.values()) {
            if (neco.getNazev().equals(jmeno)) {
                vec = neco;
                break;
            }
        }
        return vec;
    }

    /**
     * Metoda najde postavu.
     * 
     * @return  jmeno postavy
     */

    public Postava najdiPostavu(String jmeno)
    {
        return seznamPostav.get(jmeno);
    }

    /**
     * Metoda vloží postavu do prostoru.
     */

    public void vlozPostavu(Postava postava)
    {
        seznamPostav.put(postava.getJmeno(), postava);
    }

    /**
     * Metoda vrací seznam postav, které sa nacházejí v daném prostoru.
     * 
     * return   popis + seznam postav
     */

    public String getSeznamPostav() {
        String vysledek = "";
        for (String jmenoPostav : seznamPostav.keySet()) {
            vysledek += " " + jmenoPostav;
        }
        if (vysledek.length() == 0) {
            vysledek = "V místnosti se nenachází žádné postavy.";
        }
        else {
            vysledek = "Jsou zde tyto postavy: "+vysledek;
        }
        return vysledek;
    }

}
