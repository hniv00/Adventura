package logika;

import java.util.*;
import utils.Observer;
import utils.Subject;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Veronika Hniličková
 * @version ZS 2017/2018
 * 
 */

public class HerniPlan implements Subject{
    private static final String VITEZNY = "poklop";
    //konstanta pro vítězný prostor
    private Prostor aktualniProstor;
    private Kapsa kapsa;
    private Postava postava;
    private Map<String,Postava> seznamPostav;
    private Postava pes;
    private Prostor poklop;
    private Prostor vezeni;
    private Hra hra;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();

    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví temnou místnost.
     */
    public HerniPlan( Hra hra) {
        zalozProstoryHry();
        kapsa = new Kapsa();
        this.hra = hra;
    }

    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví temnou místnost.
     *  Přiřazují se průchody mezi prostory.
     *  Zakládají se věci, které lze následně vložit do prostoru či do jiné věci.
     *  Inicializují se postavy.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory:
        Prostor temnaMistnost = new Prostor("temná_místnost","temná, vlhká místnost", 40, 30);
        poklop = new Prostor(VITEZNY, "poklop, ze kterého vede cesta ven", 400, 40);
        Prostor chodba = new Prostor("chodba","úzká, dlouhá chodba", 110, 100);
        vezeni = new Prostor("vězení","vězení plné krys", 175, 30);
        Prostor kumbal = new Prostor("kumbál","malý kumbál, jež skrývá poklady", 40, 140);
        Prostor zlataMistnost = new Prostor("zlatá_místnost","místnost vyložená zlatem, kde potkáš kouzelnou osobu", 175, 160);
        Prostor psiDoupe = new Prostor("psí_doupě","doupě, ve kterém přebývá rozzuřený krvelačný pes", 300, 160);

        // přiřazují se průchody mezi prostory (sousedící prostory):
        temnaMistnost.setVychod(chodba);
        chodba.setVychod(temnaMistnost);
        chodba.setVychod(kumbal);
        chodba.setVychod(vezeni);
        chodba.setVychod(zlataMistnost);
        kumbal.setVychod(chodba);
        vezeni.setVychod(chodba);
        zlataMistnost.setVychod(chodba);
        zlataMistnost.setVychod(psiDoupe);
        psiDoupe.setVychod(zlataMistnost);

        // založení věcí:
        Vec sirky = new Vec("sirky", true, "sirky.png");
        Vec pochoden = new Vec("pochodeň", false, "pochoden.png");
        Vec horiciPochoden = new Vec("hořící_pochodeň", false, "horici.png");
        Vec skrin = new Vec("skříň", false, "skrin.png");
        Vec obojek = new Vec("obojek", true, "obojek.png");
        Vec provaz = new Vec("provaz", true, "provaz.png");
        Vec skrpal = new Vec("škrpál", true, "skrpal.png");
        Vec drahokamy = new Vec("drahokamy", true, "drahokamy.png");
        Vec jehla = new Vec("jehla", true, "jehla.png");
        Vec fletna = new Vec("flétna", true, "fletna.png");

        // vkládání věcí do skříně:
        skrin.vlozVec(obojek);
        skrin.vlozVec(provaz);
        skrin.vlozVec(skrpal);
        skrin.vlozVec(drahokamy);
        skrin.vlozVec(jehla);
        skrin.vlozVec(fletna);

        // vložení věcí do prostorů:
        temnaMistnost.vlozVec(sirky);
        temnaMistnost.vlozVec(pochoden);
        zlataMistnost.vlozVec(horiciPochoden);
        kumbal.vlozVec(skrin);

        // inicializace postav a přiřazení do seznamu postav
        pes = new Postava("krvelačný_pes","Haf! Haf!","","", false);
        Postava sasek = new Postava("šašek",
                "\n" + "Vítej, cizinče. Jsem královský šašek." + "\n"
                + "Před stovkami let tento hrad podlehl přírodní katastrofě," + "\n"
                + "zřítil se a neporušené zbylo jen hradní podzemí." + "\n"
                + "Jsem stále na živu, jelikož království bylo začarované" + "\n"
                + "a lidé v něm žili bez nemocí a nikdy nezestárli." + "\n"
                + "Mohu odtud odejít. Znám cestu ven, ale nevyužiji ji," + "\n"
                + "dokud nenajdu krále s královnou." + "\n"
                + "Cítím se ovšem osamělý. Když mi seženeš společnost," + "\n"
                + "ukážu ti cestu. Pokud ne, zůstaneš tu se mnou." + "\n"
                + "Při prohledávání podzemí se měj na pozoru před zlými stvořeními!",

                "Koukám, že máš dobré nápady a také velkou odvahu." + "\n",

                "Opravdu se ti podařilo toho hrozného psa zkrotit!" + "\n"
                + "Jsem ti vděčný. Věnuji ti svou čepici s rolničkami." + "\n"
                + "Díky ní uvidíš i to, co ti doposud bylo skryto." + "\n",
                false);

        psiDoupe.vlozPostavu(pes);
        zlataMistnost.vlozPostavu(sasek);
        
        // hra začíná v temné místnosti
        aktualniProstor = temnaMistnost;       
    }

    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
        notifyObservers();
    }

    /**
     * Metoda vrátí text se jmény postav v místnosti.
     * Tato metoda je zde asi zbytečná, jelikož se podobná nachází ve třídě Prostor.
     */
    public String popisPostavVMistnosti() {
        String vysledek = "";
        for (Postava postava : seznamPostav.values()) {
            if (getAktualniProstor() == aktualniProstor) {
                vysledek += " " + postava.getJmeno();
            }
        }
        if (vysledek.length() == 0) {
            vysledek = "V místnosti není žádná postava.";
        }
        else {
            vysledek = "Tuto místnost obývá"+vysledek;
        }
        return vysledek;
    }

     /**
     *  Metoda vrací hodnotu kapsa.
     *
     *@return    kapsa
     */
    public Kapsa getKapsa() {
        return kapsa;
    }

     /**
     *  Metoda vrací hodnotu postava.
     *
     *@return    postava
     */
    public Postava getPostava(){
        return postava;
    }
    
     /**
     *  Metoda rozhoduje, zda hráč zvítězil na základě podmínek.
     *
     *@return   true, pokud se hráč nachází ve vítězném prostoru
     *          false, pokud nikoliv
     */
    public boolean vyhra(){
        return aktualniProstor.getNazev().equals(VITEZNY);
    } 
    
     /**
     *  Metoda nastavuje hodnotu ochocena na true.
     */
    public void ochocitPsa(){
        pes.setOchocena(true);
    }
    
     /**
     *  Metoda zjišťuje, zda je hodnota ochocena = true nebo false.
     *
     *@return    boolean hodnota ochocena
     */
    public boolean jeOchoceny(){
        return pes.getOchocena();
    }
    
     /**
     *  Metoda "ověří", zda je možné dostat se do vítězného prostoru.
     *  Pokud hráč u sebe nese čepici, pak se průchod ověří.
     */
    public void overitPruchod(){
        if(kapsa.obsahujeVec("šaškova_čepice")){
            vezeni.setVychod(poklop);
        }
    }
    
        
    public Hra getHra(){
        return this.hra;
    }

    
     /*
     *  Override pro registraci, odstranění a notifikaci observeru.
     */    
    @Override
    public void registerObserver(utils.Observer observer) {
        listObserveru.add(observer);
    }

    @Override
    public void removeObserver(utils.Observer observer) {
        listObserveru.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }
    }
}
