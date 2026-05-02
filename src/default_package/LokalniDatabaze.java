package default_package;

import java.util.HashMap;
import java.util.Map;

public class LokalniDatabaze {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";

    private Map<Integer, Zamestnanec> prvkyDatabaze;

    public LokalniDatabaze(){
        prvkyDatabaze = new HashMap<>();
    }

    public void pridejZamestnance(Zamestnanec zamestnanec){
        prvkyDatabaze.put(zamestnanec.ziskejID(), zamestnanec);
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

    public void vypisPocty(){
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
            System.out.println("POČTY ZAMĚSTNANCŮ");
            System.out.println("Datoví analytici: " + pocetAnalytiku);
            System.out.println("Bezpečnostní specialisté: " + pocetBezpecaku);
            System.out.println("Celkem: " + (pocetAnalytiku + pocetBezpecaku));
        }else{
            System.out.print(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!\n" + ANSI_RESET);
        }
    }

    public int pocetPrvkuDatabaze(){
        return prvkyDatabaze.size();
    }

    //TODO: vypsat dementy dle abecedy ve skupinách
/*    public void vypisSkupinuAbecednePrijmeni(){
        Set <Integer> klicePrvku = prvkyDatabaze.keySet();
        for(Integer klic : klicePrvku){
            System.out.printf("");
        }
    }*/
}