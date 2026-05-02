package default_package;

public class Zamestnanec{
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rok_narozeni;
    private String skupina;
    private static int counter = 0;

    public Zamestnanec(String jmeno, String prijmeni, int rok_narozeni, String skupina) {
        this.id = counter;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rok_narozeni = rok_narozeni;
        this.skupina = skupina;
        counter++;
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getRok_narozeni() {
        return rok_narozeni;
    }

    public String getSkupina() {
        return skupina;
    }

    public void setSkupina(String skupina) {
        System.out.println("Po vytvoreni zaznamu zamestnance jiz neni mozne jeho skupinu zmenit!");
    }
}