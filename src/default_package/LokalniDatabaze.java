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

    public void pridejZamestnance(String jmeno, String prijmeni, short rokNarozeni, Byte skupina){
        try{
            Zamestnanec zamestnanec = null;
            switch (skupina) {
                case 1:
                    zamestnanec = new Analytik(jmeno, prijmeni, rokNarozeni, "Datový analytik");
                    break;
                case 2:
                    zamestnanec = new Bezpecak(jmeno, prijmeni, rokNarozeni, "Bezpečnostní specialista");
                    break;
            }
            prvkyDatabaze.put(zamestnanec.ziskejID(), zamestnanec);
            System.out.println(ANSI_GREEN + "Zaměstnanec byl přidán!" + ANSI_RESET);
        }catch (Exception e){
            System.out.println(ANSI_RED + "CHYBA: Zaměstnance se nepodařilo přidat!" + ANSI_RESET);
        }
    }

    public void odstranZamestnance(int ID){
        if(prvkyDatabaze.containsKey(ID)){
            prvkyDatabaze.remove(ID);
            System.out.println(ANSI_GREEN + "Zaměstnanec byl úspěšně odebrán!" + ANSI_RESET);
        }else{
            System.out.println(ANSI_RED + "CHYBA: Zaměstnance se nepodařilo odebrat!" + ANSI_RESET);
        }
    }

    public void vypisInfoOZamestnanci(int ID){
        Zamestnanec hledanyZamestnanec = prvkyDatabaze.get(ID);
        if(hledanyZamestnanec != null){
            System.out.println(ANSI_YELLOW + hledanyZamestnanec + ANSI_RESET);
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
            System.out.println("Datoví analytici: " + ANSI_YELLOW + pocetAnalytiku + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Bezpečnostní specialisté: " + ANSI_YELLOW + pocetBezpecaku + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Celkem: " + ANSI_YELLOW + (pocetAnalytiku + pocetBezpecaku) + ANSI_RESET);
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
}