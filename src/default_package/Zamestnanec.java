package default_package;

import java.util.HashMap;
import java.util.Map;

public abstract class Zamestnanec implements Comparable<Zamestnanec>{
    private int id;
    private String jmeno;
    private String prijmeni;
    private short rok_narozeni;
    private String skupina;
    private static int counter = 0;
    //TODO: vymyslet spojení na kolegy (níže je varianta - vylepšit)
    //private Map<Zamestnanec, String> spolupracovnici = new HashMap<>();

    public Zamestnanec(String jmeno, String prijmeni, short rok_narozeni, String skupina) {
        this.id = counter;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rok_narozeni = rok_narozeni;
        this.skupina = skupina;
        counter++;
    }

    public Zamestnanec(int id, String jmeno, String prijmeni, short rok_narozeni, String skupina) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rok_narozeni = rok_narozeni;
        this.skupina = skupina;
        counter++;
    }

    public int ziskejID() {
        return id;
    }

    public String ziskejJmeno() {
        return jmeno;
    }

    public String ziskejPrijmeni() {
        return prijmeni;
    }

    public int ziskejRokNarozeni() {
        return rok_narozeni;
    }

    public String ziskejSkupinu() {
        return skupina;
    }

    public void nastavSkupinu(String skupina) {
        System.out.println("Po vytvoreni zaznamu zamestnance jiz neni mozne jeho skupinu zmenit!");
    }

    public abstract void spustDovednost();

    @Override
    public String toString(){
        return String.format("*ID:%d\t\tJMÉNO:%s\t\tPŘÍJMENÍ:%s\t\tROK NAROZENÍ:%d\t\tSKUPINA:%s", id, jmeno, prijmeni, rok_narozeni, skupina);
    }

    @Override
    public boolean equals(Object kolega){
        //jedná se o stejné objekty?
        if (this == kolega) {
            return true;
        }

        //je kolega typu Zaměstnanec?
        if (!(kolega instanceof Zamestnanec)) {
            return false;
        }

        Zamestnanec on = (Zamestnanec) kolega;

        //jsou si rovni, pokud mají stejné ID
        return this.id == on.id;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(id); //musí se zvolit pole, které se nemění | je použit Integer wrapper
    }

    @Override
    public int compareTo(Zamestnanec kolega){
        int compare = this.prijmeni.compareToIgnoreCase(kolega.prijmeni);
        if (compare == 0){
            return this.jmeno.compareToIgnoreCase(kolega.jmeno); //pro případ, že ty 2 volové mají stejný příjmení
        }
        return compare;
    }
}