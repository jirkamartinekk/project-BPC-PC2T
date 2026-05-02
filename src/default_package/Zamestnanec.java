package default_package;

import java.util.HashMap;
import java.util.Map;

public abstract class Zamestnanec{
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
        return String.format("*ID:%d\tJMÉNO:%s\tPŘÍJMENÍ:%s\tROK NAROZENÍ:%d\t SKUPINA:%s", id, jmeno, prijmeni, rok_narozeni, skupina);
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
}