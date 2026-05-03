package default_package;

public class Bezpecak extends Zamestnanec{
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_YELLOW = "\u001B[33m";

    public Bezpecak(String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(jmeno, prijmeni, rokNarozeni, skupina);
    }

    public Bezpecak(int id, String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(id, jmeno, prijmeni, rokNarozeni, skupina);
    }

    public void spustDovednost(){
        //TODO: napsat funkci pro vypocet prumeru urovne spoluprace - MIKEŠ
        System.out.println(ANSI_YELLOW + "Jsem Bezpečák!" + ANSI_RESET);
    }
}
