package default_package;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static boolean jeVstupValidniPridaniZamestnance(String jmeno, String prijmeni, short rokNarozeni, byte skupina){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        if (jmeno.isEmpty() || prijmeni.isEmpty()) {
            System.out.println(ANSI_RED + "CHYBA: Jméno nebo příjmení jsou prázdné!" + ANSI_RESET);
            return false;
        } else if (jmeno.matches(".*\\d.*") || prijmeni.matches(".*\\d.*")) { //zakazuje vložit do stringu int
            System.out.println(ANSI_RED + "CHYBA: Jméno a příjmení nesmí obsahovat čísla!" + ANSI_RESET);
            return false;
        } else if (rokNarozeni < 1930 || rokNarozeni > 2026) {
            System.out.println(ANSI_RED + "CHYBA: Neplatný rok narození!" + ANSI_RESET);
            return false;
        } else if (skupina != 1 && skupina != 2) {
            System.out.println(ANSI_RED + "CHYBA: Skupina musí být ve tvaru 1 nebo 2!" + ANSI_RESET);
            return false;
        }
        return true;
    }

    private static boolean jeVstupValidniID(int pocetPrvkuDatabaze){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        if(pocetPrvkuDatabaze <= 0){
            System.out.println(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!" + ANSI_RESET);
            return false;
        }

        return true;
    }

    private static boolean jeVstupValidniIDPopis(byte popis, int pocetPrvkuDatabaze){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        if(pocetPrvkuDatabaze <= 0){
            System.out.println(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!" + ANSI_RESET);
            return false;
        }
        if (popis <= 0 || popis > 3) {
            System.out.println(ANSI_RED + "CHYBA: Neplatný vstup spolupráce!" + ANSI_RESET);
            return false;
        }
        return true;
    }

    private static boolean jeVstupValidniIDCesta(Integer id, String cesta, int pocetPrvkuDatabaze){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        if(id == null || id >= pocetPrvkuDatabaze){
            System.out.println(ANSI_RED + "CHYBA: Nebylo zadáno ID!" + ANSI_RESET);
            return false;
        }
        if(cesta == null){
            System.out.println(ANSI_RED + "CHYBA: Nebyla zadána cesta!" + ANSI_RESET);
            return false;
        }

        return true;
    }

    private static boolean jeVstupValidniSoubor(String cesta) {
        if (cesta == null || cesta.isBlank()) {
            System.out.println("CHYBA: cesta je prazdná");
            return false;
        }

        Path path = Paths.get(cesta);

        if (!Files.exists(path)) {
            System.out.println("CHYBA: cesta neexistuje");
            return false;
        }

        if (!Files.isRegularFile(path)) {
            System.out.println("CHYBA: není to soubor");
            return false;
        }

        if (!Files.isReadable(path)) {
            System.out.println("CHYBA: soubor nelze číst");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        final String VYPIS_MENU =
                "--- HLAVNÍ MENU --- \n" +
                        "SPRÁVA ZAMĚSTNANCŮ\n" +
                        "\t1 ... [PŘIDAT]    zaměstnance\n" +
                        "\t2 ... [ODEBRAT]   zaměstnance\n" +
                        "\t3 ... [VYHLEDAT]  zaměstnance (dle ID) a vypsat info\n" +
                        "SPOLUPRÁCE A DOVEDNOSTI\n" +
                        "\t4 ... [ZAPSAT]    spolupráci mezi kolegy\n" +
                        "\t5 ... [DOVEDNOST] zaměstnance\n" +
                        "\t6 ... [STATISTIKY] převažující kvalita spolupráce a zaměstnanec s nejvíce vazbami\n" +
                        "VÝPISY A PŘEHLEDY\n" +
                        "\t7 ... [ABECEDNĚ]  výpis zaměstnanců dle skupin\n" +
                        "\t8 ... [POČTY]     zaměstnanců ve skupinách\n" +
                        "SOUBORY (IMPORT/EXPORT)\n" +
                        "\t9 ... [ULOŽIT]    zaměstnance do souboru\n" +
                        "\t10 .. [NAČÍST]    zaměstnance ze souboru\n" +
                        "SYSTÉM\n" +
                        "\t0 ... [UKONČIT]   program a synchronizovat databázi\n";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        boolean behProgramu = true;
        int vybranaMoznost;
        Scanner sc = new Scanner(System.in);
        LokalniDatabaze lokalniDatabaze = new LokalniDatabaze();
        SQLDatabaze sqlDB = new SQLDatabaze(lokalniDatabaze);
        sqlDB.nacistZamestnance();
        sqlDB.nacistSpoluprace();

        //začátek programu
        while (behProgramu) {
            int pocetPrvkuDatabaze = lokalniDatabaze.pocetPrvkuDatabaze();
            System.out.println(VYPIS_MENU);
            System.out.print("Vybraná možnost: ");
            try{
                vybranaMoznost = sc.nextShort();
                sc.nextLine();
                switch (vybranaMoznost) {
                    case 0: {
                        behProgramu = false;
                        sqlDB.smazatSpoluprace();
                        sqlDB.smazatZamestnance();
                        sqlDB.ulozitZamestnance();
                        sqlDB.ulozitSpoluprace();
                        sqlDB.odpojDatabazi();
                        break;
                    }
                    case 1: {
                        String jmeno = "";
                        String prijmeni = "";
                        byte skupina = 0;
                        short rokNarozeni = 0;
                        boolean vstupJeOk = false;

                        while (!vstupJeOk) {
                            try {
                                System.out.print("Zadej jméno: ");
                                jmeno = sc.next().trim();
                                System.out.print("Zadej příjmení: ");
                                prijmeni = sc.next().trim();
                                System.out.print("Zadej rok narození: ");
                                rokNarozeni = sc.nextShort();
                                System.out.print("Zadej pracovní skupinu (číslem) - Datový analytik (1) / Bezpečnostní specialista (2): ");
                                skupina = sc.nextByte();
                                sc.nextLine(); //vyčištění scanneru, ať to pak nemrdá podmínky níž
                            }catch (InputMismatchException e) {
                                System.out.println(ANSI_RED + "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                                sc.nextLine();
                            }
                            vstupJeOk = jeVstupValidniPridaniZamestnance(jmeno, prijmeni, rokNarozeni, skupina);
                        }
                        //noinspection ConstantValue
                        if (vstupJeOk) {
                            lokalniDatabaze.pridejZamestnance(jmeno, prijmeni, rokNarozeni, skupina);
                        }
                        break;
                    }
                    case 2: {
                        Integer ID = null;
                        boolean vstupJeOk;
                        try {
                            System.out.print("Zadejte ID zaměstnance: ");
                            ID = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        if(ID == null){break;}
                        vstupJeOk = jeVstupValidniID(pocetPrvkuDatabaze);
                        if(vstupJeOk){
                            lokalniDatabaze.odstranZamestnance(ID);
                        }
                        break;
                    }
                    case 3: {
                        Integer ID = null;
                        boolean vstupJeOk;
                        try {
                            System.out.print("Zadejte ID zaměstnance: ");
                            ID = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        if(ID == null){break;}
                        vstupJeOk = jeVstupValidniID(pocetPrvkuDatabaze);
                        if(vstupJeOk){
                            lokalniDatabaze.vypisInfoOZamestnanci(ID);
                        }
                        break;
                    }
                    case 4: {
                        int IDa = 0;
                        int IDb = 0;
                        byte popis = 0;
                        boolean vstupJeOk;
                        try {
                            System.out.print("Zadejte ID 1. zaměstnance: ");
                            IDa = sc.nextInt();
                            System.out.print("Zadejte ID 2. zaměstnance: ");
                            IDb = sc.nextInt();
                            System.out.print("Zadejte úroveň spolupráce (číslem) - Špatná (1) / Průměrná (2) / Dobrá (3): ");
                            popis = sc.nextByte();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        vstupJeOk = jeVstupValidniIDPopis(popis, pocetPrvkuDatabaze);
                        if(vstupJeOk){
                            lokalniDatabaze.pridejSpolupraci(IDa, IDb, popis);
                        }
                        break;
                    }
                    case 5: {
                        Integer ID = null;
                        boolean vstupJeOk;
                        try {
                            System.out.print("Zadejte ID zaměstnance: ");
                            ID = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        if(ID == null){break;}
                        vstupJeOk = jeVstupValidniID(pocetPrvkuDatabaze);
                        if(vstupJeOk){
                            lokalniDatabaze.dovednostZamestnance(ID);
                        }
                        break;
                    }
                    case 6: {
                        lokalniDatabaze.vypisStatistiku();
                        break;
                    }
                    case 7: {
                        lokalniDatabaze.vypisSkupinuAbecednePrijmeni();
                        break;
                    }
                    case 8: {
                        lokalniDatabaze.vypisPoctySkupin();
                        break;
                    }
                    case 9: {
                        Integer id = null;
                        String cesta = "data/";

                        System.out.println("Zadejte ID zamestnance: ");
                        id = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Zadejte nazev souboru: ");
                        cesta += sc.nextLine();

                        if (jeVstupValidniIDCesta(id, cesta, lokalniDatabaze.pocetPrvkuDatabaze())) {
                            lokalniDatabaze.ulozZamestnanceDoSouboru(id, cesta);
                        }

                        break;
                    }
                    case 10: {
                        String cesta = "data/";

                        System.out.println("Zadejte nazev souboru: ");
                        cesta += sc.nextLine();

                        if(jeVstupValidniSoubor(cesta)) {
                            lokalniDatabaze.nactiZamestnanceZeSouboru(cesta);
                        }

                        break;
                    }
                    default:
                        System.out.println(ANSI_RED + "CHYBA: Zadaná volba je mimo rozsah výběru!" + ANSI_RESET + "\n\n(Pokud tuto zprávu čtete, bude plný počet? :D)");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                sc.nextLine();
            }
        }
    }
}