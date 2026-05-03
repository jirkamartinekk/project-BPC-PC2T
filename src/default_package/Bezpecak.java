package default_package;

public class Bezpecak extends Zamestnanec{

    public Bezpecak(String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(jmeno, prijmeni, rokNarozeni, skupina);
    }

    public Bezpecak(int id, String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(id, jmeno, prijmeni, rokNarozeni, skupina);
    }

    public void spustDovednost(){
        //TODO: napsat funkci pro vypocet prumeru urovne spoluprace
        System.out.println("Jsem Bezpečák!");
    }
}
