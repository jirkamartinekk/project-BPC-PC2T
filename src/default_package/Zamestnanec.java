package default_package;

import java.util.HashMap;
import java.util.Map;

public abstract class Zamestnanec{
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rok_narozeni;
    private String skupina;
    private static int counter = 0;
    private Map<Zamestnanec, String> spolupracovnici = new HashMap<>();

    public Zamestnanec(String jmeno, String prijmeni, int rok_narozeni, String skupina) {
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
}