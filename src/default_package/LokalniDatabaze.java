package default_package;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class LokalniDatabaze {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";

    private Map<Integer, Zamestnanec> prvkyDatabaze;

    public LokalniDatabaze(){
        prvkyDatabaze = new HashMap<>();
    }

    public Collection<Zamestnanec> ziskejZamestnance(){
        return prvkyDatabaze.values();
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

    public void pridejZamestnanceSQL(int id, String jmeno, String prijmeni, Short rokNarozeni, String skupina){

        Zamestnanec zamestnanec = null;

        switch (skupina) {
            case "DATA":
                zamestnanec = new Analytik(id, jmeno, prijmeni, rokNarozeni, "Datový analytik");
                break;
            case "SEC":
                zamestnanec = new Bezpecak(id, jmeno, prijmeni, rokNarozeni, "Bezpečnostní specialista");
                break;
            default:
                return;
        }

        prvkyDatabaze.put(zamestnanec.ziskejID(), zamestnanec);

    }

    public void odstranZamestnance(int ID){
        if(prvkyDatabaze.containsKey(ID)){
            for(Zamestnanec kolega : prvkyDatabaze.values()){
                kolega.pristupKeSpolupracovnikum().remove(prvkyDatabaze.get(ID)); //maže zápis o kolegovi | u kolegů nemaže na základě ID, ale typu Zamestnanec
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
                for(Map.Entry<Zamestnanec, String> popis : hledanyZamestnanec.pristupKeSpolupracovnikum().entrySet()) {
                    String jmeno = popis.getKey().ziskejJmeno();
                    String prijmeni = popis.getKey().ziskejPrijmeni();
                    String hodnoceni = popis.getValue();
                    System.out.println(ANSI_YELLOW + "\t\t*" + jmeno + " " + prijmeni + ": " + hodnoceni + ANSI_RESET);
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

    public void vypisStatistiku(){

        int counterSpatna = 0;
        int counterPrumerna = 0;
        int counterDobra = 0;
        int id;
        int spoluprace;

        Integer idMax = null;
        int spolupraceMax = 0;

        for (Zamestnanec zamestnanec : prvkyDatabaze.values()) {

            id = zamestnanec.ziskejID();
            spoluprace = 0;

            for (Map.Entry<Zamestnanec, String> entry : zamestnanec.pristupKeSpolupracovnikum().entrySet()) {

                Zamestnanec spolupracovnik = entry.getKey();
                String uroven = entry.getValue();

                // převod na DB hodnoty
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
                        System.out.println(ANSI_RED + "CHYBA: Neplatný údaj 'skupina' při statistice spolupráce / (Mohlo by být i absencí spolupráce? Otestujem) " + ANSI_YELLOW + zamestnanec.ziskejID() + "->" + spolupracovnik.ziskejID() + ANSI_RED + " !" + ANSI_RESET);
                        continue;
                }

            }

            if(spoluprace > spolupraceMax){
                spolupraceMax = spoluprace;
                idMax = id;
            }

        }

        if(idMax == null) {
            System.out.println(ANSI_GREEN + "Zaměstnanci nemají žádné vazby. Počet spoluprací je " + ANSI_YELLOW + 0 + ANSI_RESET);
        } else{
            System.out.println(ANSI_GREEN + "Zaměstnanec s největším počtem vazeb má " + ANSI_RESET + "ID: " + ANSI_YELLOW + idMax + ANSI_GREEN + " s počtem spoluprací: " + ANSI_YELLOW + spolupraceMax + ANSI_RESET);
        }

        if(counterSpatna > counterPrumerna && counterSpatna > counterDobra){
            System.out.println(ANSI_GREEN + "Spolupráce s největším výskytem je úrovně 'Špatná' s počtem: " + ANSI_YELLOW + counterSpatna + ANSI_GREEN + "\nZde jsou výsledky:\nŠpatná: " + ANSI_YELLOW + counterSpatna + ANSI_GREEN + "\nPrůměrná: " + ANSI_YELLOW + counterPrumerna + ANSI_GREEN + "\nDobrá: " + ANSI_YELLOW + counterDobra + "\n" + ANSI_RESET);
        } else if(counterPrumerna > counterSpatna && counterPrumerna > counterDobra){
            System.out.println(ANSI_GREEN + "Spolupráce s největším výskytem je úrovně 'Průměrná' s počtem: " + ANSI_YELLOW + counterPrumerna + ANSI_GREEN + "\nZde jsou výsledky:\nŠpatná: " + ANSI_YELLOW + counterSpatna + ANSI_GREEN + "\nPrůměrná: " + ANSI_YELLOW + counterPrumerna + ANSI_GREEN + "\nDobrá: " + ANSI_YELLOW + counterDobra + "\n" + ANSI_RESET);
        } else if(counterDobra > counterSpatna && counterDobra > counterPrumerna){
            System.out.println(ANSI_GREEN + "Spolupráce s největším výskytem je úrovně 'Dobrá' s počtem: " + ANSI_YELLOW + counterDobra + ANSI_GREEN + "\nZde jsou výsledky:\nŠpatná: " + ANSI_YELLOW + counterSpatna + ANSI_GREEN + "\nPrůměrná: " + ANSI_YELLOW + counterPrumerna + ANSI_GREEN + "\nDobrá: " + ANSI_YELLOW + counterDobra + "\n" + ANSI_RESET);
        } else{
            System.out.println(ANSI_GREEN + "Není " + ANSI_RED + "jeden" + ANSI_GREEN + " maximální výskyt úrovně spolupráce\nZde jsou výsledky:\nŠpatná: " + ANSI_YELLOW + counterSpatna + ANSI_GREEN + "\nPrůměrná: " + ANSI_YELLOW + counterPrumerna + ANSI_GREEN + "\nDobrá: " + ANSI_YELLOW + counterDobra + "\n" + ANSI_RESET);

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

    public void pridejSpolupraciSQL(int IDa, int IDb, String popisVstup){
        String popis = "";
        switch (popisVstup){
            case "spatna":
                popis = "Špatná";
                break;
            case "prumerna":
                popis = "Průměrná";
                break;
            case "dobra":
                popis = "Dobrá";
                break;
            default:
                System.out.println(ANSI_RED + "CHYBA: Neplatný údaj 'úroveň' při načtení spolupráce" + ANSI_YELLOW + IDa + "->" + IDb + ANSI_RED + " !" + ANSI_RESET);
                return;
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
        }else{
            System.out.println(ANSI_RED + "CHYBA: Jedno z ID neexistuje!" + ANSI_RESET);
        }
    }

    public void ulozZamestnanceDoSouboru(int id, String cesta) {
        Zamestnanec zamestnanec = prvkyDatabaze.get(id);

        if (zamestnanec == null) {
            System.out.println("CHYBA: Zaměstnanec neexistuje!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(cesta), StandardCharsets.UTF_8))) {

            String radek = zamestnanec.ziskejID() + ";" +
                    zamestnanec.ziskejJmeno() + ";" +
                    zamestnanec.ziskejPrijmeni() + ";" +
                    zamestnanec.ziskejRokNarozeni() + ";" +
                    zamestnanec.ziskejSkupinu();

            writer.write(radek);
            writer.newLine();

            System.out.println(("Zaměstnanec úspěšně uložen do souboru!"));

        } catch (IOException e) {
            System.out.println("CHYBA: Nepodařilo se uložit!");
        }
    }

    public void nactiZamestnanceZeSouboru(String cesta) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(cesta), StandardCharsets.UTF_8))) {

            String radek = reader.readLine();

            if (radek == null || radek.isEmpty()) {
                System.out.println("CHYBA: Soubor je prázdný!");
                return;
            }

            String[] casti = radek.split(";");

            if (casti.length != 5) {
                System.out.println("CHYBA: Špatný formát souboru!");
                return;
            }

            int id = Integer.parseInt(casti[0]);
            String jmeno = casti[1];
            String prijmeni = casti[2];
            short rok = Short.parseShort(casti[3]);
            String skupina = casti[4];

            Zamestnanec zamestnanec;

            if (skupina.equals("Datový analytik")) {
                zamestnanec = new Analytik(id, jmeno, prijmeni, rok, skupina);
            } else if (skupina.equals("Bezpečnostní specialista")) {
                zamestnanec = new Bezpecak(id, jmeno, prijmeni, rok, skupina);
            } else {
                System.out.println("CHYBA: Neznámá skupina!");
                return;
            }

            prvkyDatabaze.put(id, zamestnanec);

            System.out.println("Zaměstnanec byl úspěsně načten!");

        } catch (IOException e) {
            System.out.println("CHYBA: Nepodařilo se načíst soubor!");
        }
    }
}