package default_package;

public class Analytik extends Zamestnanec{

    public Analytik(String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(jmeno, prijmeni, rokNarozeni, skupina);
    }

    public void spustDovednost(){
        //TODO: napsat funkci pro vraceni spolupracovnika s nejvice spolecnymi spolupracovniky
        System.out.println("Jsem Analytik!");
    }

}
