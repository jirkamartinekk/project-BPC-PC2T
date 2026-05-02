package default_package;

public class Zamestnanec{
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rok_narozeni;
    private String skupina;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public int getRok_narozeni() {
        return rok_narozeni;
    }

    public void setRok_narozeni(int rok_narozeni) {
        this.rok_narozeni = rok_narozeni;
    }

    public String getSkupina() {
        return skupina;
    }

    public void setSkupina(String skupina) {
        System.out.println("Po vytvoreni zaznamu zamestnance jiz neni mozne jeho skupinu zmenit!");
    }

    public Zamestnanec(int id, String jmeno, String prijmeni, int rok_narozeni, String skupina) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rok_narozeni = rok_narozeni;
        this.skupina = skupina;
    }
}