package default_package;

import java.util.Set;

public class Analytik extends Zamestnanec{
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";

    public Analytik(String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(jmeno, prijmeni, rokNarozeni, skupina);
    }

    public Analytik(int id, String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(id, jmeno, prijmeni, rokNarozeni, skupina);
    }

    @Override
    public void spustDovednost(){
        Set<Zamestnanec> spolupracovnikZamestnance = this.pristupKeSpolupracovnikum().keySet();

        Zamestnanec maxSpolupracovnik = null;
        int maxSpolecnych = -1;

        for (Zamestnanec spolupracovnik : spolupracovnikZamestnance) {

            Set<Zamestnanec> spolupracovnikSpolupracovnika = spolupracovnik.pristupKeSpolupracovnikum().keySet();

            int pocetSpolecnych = 0;

            for (Zamestnanec kandidat : spolupracovnikSpolupracovnika) {

                if (spolupracovnikZamestnance.contains(kandidat) && kandidat != this) {
                    pocetSpolecnych++;
                }
            }

            if (pocetSpolecnych > maxSpolecnych) {
                maxSpolecnych = pocetSpolecnych;
                maxSpolupracovnik = spolupracovnik;
            }
        }

        if (maxSpolupracovnik != null) {
            System.out.println(ANSI_GREEN + "Nejvíc společných spolupracovníku má: " + ANSI_RESET +"ID: " + ANSI_YELLOW + maxSpolupracovnik.ziskejID() + " " + ANSI_RESET + maxSpolupracovnik.ziskejJmeno() + " " +  maxSpolupracovnik.ziskejPrijmeni() + ANSI_GREEN + " (" + ANSI_YELLOW + maxSpolecnych + ANSI_GREEN + ")" + ANSI_RESET);
        } else {
            System.out.println("Žádní vhodní spolupracovníci!");
        }
    }

}
