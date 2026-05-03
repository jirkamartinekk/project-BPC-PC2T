package default_package;

import java.util.*;

public class LokalniDatabaze {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";

    private Map<Integer, Zamestnanec> prvkyDatabaze;

    public LokalniDatabaze(){
        prvkyDatabaze = new HashMap<>();
    }

    public void pridejZamestnance(String jmeno, String prijmeni, Short rokNarozeni, Byte skupina){

        Zamestnanec zamestnanec = null;

        switch (skupina) {
            case 1:
                zamestnanec = new Analytik(jmeno, prijmeni, rokNarozeni, "Datový analytik");
                break;
            case 2:
                zamestnanec = new Bezpecak(jmeno, prijmeni, rokNarozeni, "Bezpečnostní specialista");
                break;
            default:
                return;
        }

        prvkyDatabaze.put(zamestnanec.ziskejID(), zamestnanec);
        System.out.println(ANSI_GREEN + "Zaměstnanec byl přidán!" + ANSI_RESET);

    }

    public void odstranZamestnance(int ID){
        Zamestnanec mazanyZamestnanec = prvkyDatabaze.get(ID);
        if(prvkyDatabaze.containsKey(ID)){
            for(Zamestnanec kolega : mazanyZamestnanec.pristupKeSpolupracovnikum().keySet()){
                kolega.pristupKeSpolupracovnikum().remove(mazanyZamestnanec); //u kolegů nemaže na základě ID, ale typu Zamestnanec
            }
            prvkyDatabaze.remove(ID);
            System.out.println(ANSI_GREEN + "Zaměstnanec byl úspěšně odebrán!" + ANSI_RESET);
        }else{
            System.out.println(ANSI_RED + "CHYBA: Zaměstnance se nepodařilo odebrat!" + ANSI_RESET);
        }
    }

    public void vypisInfoOZamestnanci(int ID){
        Zamestnanec hledanyZamestnanec = prvkyDatabaze.get(ID);
        if(hledanyZamestnanec != null){
            System.out.printf(ANSI_YELLOW + "*ID: %d\t\tJMÉNO: %s\t\tPŘÍJMENÍ: %s\t\tROK NAROZENÍ: %d\t\tSKUPINA: %s" + ANSI_RESET, hledanyZamestnanec.ziskejID(), hledanyZamestnanec.ziskejJmeno(), hledanyZamestnanec.ziskejPrijmeni(), hledanyZamestnanec.ziskejRokNarozeni(), hledanyZamestnanec.ziskejSkupinu());
            System.out.println(ANSI_YELLOW + "\n\tÚROVEŇ SPOLUPRÁCE" + ANSI_RESET);
            if(hledanyZamestnanec.pristupKeSpolupracovnikum().isEmpty()){
                System.out.println(ANSI_RED + "\t\tNení evidována žádná spolupráce!" + ANSI_RESET);
            }else{
                for(Zamestnanec kolega : hledanyZamestnanec.pristupKeSpolupracovnikum().keySet()){
                    System.out.println(ANSI_YELLOW + "\t\t*" + kolega.ziskejJmeno() + " " + kolega.ziskejPrijmeni() + " " + kolega.pristupKeSpolupracovnikum().values() + ANSI_RESET);
                }
            }
        }else{
            System.out.println(ANSI_RED + "CHYBA: Hledaný zaměstnanec je typu null!" + ANSI_RESET);
        }
    }

    public void dovednostZamestnance(int ID){
        Zamestnanec hledanyZamestnanec = prvkyDatabaze.get(ID);
        if(hledanyZamestnanec != null){
            hledanyZamestnanec.spustDovednost();
        }else{
            System.out.println(ANSI_RED + "CHYBA: Hledaný zaměstnanec je typu null!" + ANSI_RESET);
        }
    }

    public void vypisPoctySkupin(){
        int pocetAnalytiku = 0;
        int pocetBezpecaku = 0;
        if(pocetPrvkuDatabaze() > 0){
            for(Zamestnanec zamestnanec: prvkyDatabaze.values()){
                if(zamestnanec instanceof Analytik){
                    pocetAnalytiku++;
                }else if(zamestnanec instanceof Bezpecak){
                    pocetBezpecaku++;
                }
            }
            System.out.println(ANSI_YELLOW + "Datoví analytici: " + pocetAnalytiku + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Bezpečnostní specialisté: "+ pocetBezpecaku + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Celkem: " + (pocetAnalytiku + pocetBezpecaku) + ANSI_RESET);
        }else{
            System.out.print(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!\n" + ANSI_RESET);
        }
    }

    public int pocetPrvkuDatabaze(){
        return prvkyDatabaze.size();
    }

    public void vypisSkupinuAbecednePrijmeni(){
        List<Zamestnanec> seznamZamestnancu = new ArrayList<>(prvkyDatabaze.values());
        Collections.sort(seznamZamestnancu);
        System.out.println("Seznam pracovníků");
        System.out.println("\tDatoví analytici");
        for(Zamestnanec zamestnanec : seznamZamestnancu){
            if(zamestnanec instanceof Analytik){
                System.out.println(ANSI_YELLOW + "\t\t" + zamestnanec + ANSI_RESET);
            }
        }
        System.out.println("\tBezpečnostní specialisté");
        for(Zamestnanec zamestnanec : seznamZamestnancu){
            if(zamestnanec instanceof Bezpecak){
                System.out.println(ANSI_YELLOW + "\t\t" + zamestnanec + ANSI_RESET);
            }
        }
    }

    public void pridejSpolupraci(int IDa, int IDb, byte popisVstup){
        String popis = "";
        switch (popisVstup){
            case 1:
                popis = "Špatná";
                break;
            case 2:
                popis = "Průměrná";
                break;
            case 3:
                popis = "Dobrá";
                break;
        }
        Zamestnanec zamestnanecA = null;
        Zamestnanec zamestnanecB = null;
        if(IDa == IDb){
            System.out.println(ANSI_RED + "CHYBA: Nelze propojit identické zaměstnance!" + ANSI_RESET);
            return;
        }
        if(prvkyDatabaze.containsKey(IDa) && prvkyDatabaze.containsKey(IDb)){
            zamestnanecA = prvkyDatabaze.get(IDa);
            zamestnanecB = prvkyDatabaze.get(IDb);
        }else{
            System.out.println(ANSI_RED + "CHYBA: Databáze neobsahuje jedno z ID!" + ANSI_RESET);
            return;
        }
        if((zamestnanecA != null) && (zamestnanecB != null)){
            zamestnanecA.pristupKeSpolupracovnikum().put(zamestnanecB, popis);
            System.out.println(ANSI_GREEN + "Spolupráce byla úspěšně přidána!" + ANSI_RESET);
        }else{
            System.out.println(ANSI_RED + "CHYBA: Jedno z ID neexistuje!" + ANSI_RESET);
        }
    }
}