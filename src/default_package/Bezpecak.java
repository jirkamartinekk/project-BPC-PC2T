package default_package;

import java.util.Map;

public class Bezpecak extends Zamestnanec{
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";

    public Bezpecak(String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(jmeno, prijmeni, rokNarozeni, skupina);
    }

    public Bezpecak(int id, String jmeno, String prijmeni, Short rokNarozeni, String skupina){
        super(id, jmeno, prijmeni, rokNarozeni, skupina);
    }

    public void spustDovednost(){

        float counterSpatna = 0;
        float counterPrumerna = 0;
        float counterDobra = 0;
        float spoluprace = 0;

        System.out.println(ANSI_YELLOW + "Jsem Bezpečák!" + ANSI_RESET);
        for(Map.Entry<Zamestnanec,String> entry : this.pristupKeSpolupracovnikum().entrySet()){

            Zamestnanec spolupracovnik = entry.getKey();
            String uroven = entry.getValue();

            switch (uroven) {
                case "Špatná":
                    counterSpatna++;
                    spoluprace++;
                    break;
                case "Průměrná":
                    counterPrumerna++;
                    spoluprace++;
                    break;
                case "Dobrá":
                    counterDobra++;
                    spoluprace++;
                    break;
                default:
                    System.out.println(ANSI_RED + "CHYBA: Neplatný údaj 'skupina' při statistice spolupráce / (Mohlo by být i absencí spolupráce? Otestujem) " + ANSI_YELLOW + this.ziskejID() + "->" + spolupracovnik.ziskejID() + ANSI_RED + " !" + ANSI_RESET);
                    continue;
            }
        }

        float prumerUrovne = (counterSpatna + counterPrumerna * 2 + counterDobra * 3)/spoluprace;

        if(prumerUrovne >= 2.5){
            System.out.println(ANSI_GREEN + "Spolupráce má nízkou míru rizika" + ANSI_RESET);
        } else if(prumerUrovne < 2.5 && prumerUrovne >= 2){
            System.out.println(ANSI_GREEN + "Spolupráce má " + ANSI_RESET + "standardní" + ANSI_GREEN + " míru rizika" + ANSI_RESET);
        } else if(prumerUrovne < 2 && prumerUrovne >= 1.5){
            System.out.println(ANSI_GREEN + "Spolupráce má " + ANSI_YELLOW + "vyšší" + ANSI_GREEN + " míru rizika" + ANSI_RESET);
        } else if(prumerUrovne < 1.5){
            System.out.println(ANSI_GREEN + "Spolupráce má " + ANSI_RED + "vysokou" + ANSI_GREEN + " míru rizika" + ANSI_RESET);
        } else{
            System.out.println(ANSI_RED + "CHYBA: Průměr úrovně spolupráce nedává smysl T-T" + ANSI_RESET);
        }

    }
}
