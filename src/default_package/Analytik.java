package default_package;

public class Analytik extends Zamestnanec{
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_YELLOW = "\u001B[33m";

    public Analytik(String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(jmeno, prijmeni, rokNarozeni, skupina);
    }

    public Analytik(int id, String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(id, jmeno, prijmeni, rokNarozeni, skupina);
    }

    public void spustDovednost(){
        //TODO: napsat funkci pro vraceni spolupracovnika s nejvice spolecnymi spolupracovniky - MIKEŠ
        System.out.println(ANSI_YELLOW + "Jsem Analytik!" + ANSI_RESET);
    }

}
