package default_package;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TODO: načtení dat z SQL databáze - Mám funkci na načtení a vytvoření objektů, potřebuju pomoct jak to propojit s lokalni db viz SQLDatabaze - nacistZamestnance()
        //TODO: dopsat podmínky pro správné načtení int, str atd.

        SQLDatabaze sqlDB = new SQLDatabaze();

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

        //začátek programu
        while (behProgramu) {
            int pocetPrvkuDatabaze = lokalniDatabaze.pocetPrvkuDatabaze();
            System.out.println(VYPIS_MENU);
            System.out.print("Vybraná možnost: ");
            vybranaMoznost = sc.nextShort();
            switch (vybranaMoznost) {
                case 0: {
                    behProgramu = false;
                    //TODO: kód pro uložení dat do databáze přes SQL - MIKEŠ
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
                            if (jmeno.isEmpty() || prijmeni.isEmpty()) {
                                System.out.println(ANSI_RED + "CHYBA: Jméno nebo příjmení jsou prázdné!" + ANSI_RESET);
                            } else if (jmeno.matches(".*\\d.*") || prijmeni.matches(".*\\d.*")) { //zakazuje vložit do stringu int
                                System.out.println(ANSI_RED + "CHYBA: Jméno a příjmení nesmí obsahovat čísla!" + ANSI_RESET);
                            } else if (rokNarozeni < 1930 || rokNarozeni > 2026) {
                                System.out.println(ANSI_RED + "CHYBA: Neplatný rok narození!" + ANSI_RESET);
                            } else if (skupina != 1 && skupina != 2) {
                                System.out.println(ANSI_RED + "CHYBA: Skupina musí být ve tvaru 1 nebo 2!" + ANSI_RESET);
                            } else {
                                vstupJeOk = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED + "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                    }
                    //noinspection ConstantValue
                    if (vstupJeOk) {
                        Zamestnanec novyZamestnanec = null;
                        switch (skupina) {
                            case 1:
                                novyZamestnanec = new Analytik(jmeno, prijmeni, rokNarozeni, "Datový analytik");
                                break;
                            case 2:
                                novyZamestnanec = new Bezpecak(jmeno, prijmeni, rokNarozeni, "Bezpečnostní specialista");
                                break;
                        }
                        lokalniDatabaze.pridejZamestnance(novyZamestnanec);
                        //noinspection UnusedAssignment
                        vstupJeOk = false;
                    }
                    break;
                }
                case 2: {
                    int ID = 0;
                    if(pocetPrvkuDatabaze > 0){
                        try {
                            //System.out.println("Rozsah prvků databáze: " + pocetPrvkuDatabaze);
                            System.out.print("Zadejte ID zaměstnance: ");
                            ID = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        lokalniDatabaze.odstranZamestnance(ID);
                    }else{
                        System.out.println(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!" + ANSI_RESET);
                    }
                    //TODO: odebrání zaměstnance ze všech vazeb
                    break;
                }
                case 3: {
                    int ID = 0;
                    if(pocetPrvkuDatabaze > 0){
                        try {
                            System.out.print("Zadejte ID zaměstnance: ");
                            ID = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        lokalniDatabaze.vypisInfoOZamestnanci(ID);
                    }else{
                        System.out.println(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!" + ANSI_RESET);
                    }
                    //TODO: dopsat statistiky spolupráce (nejspíš v Zamestnanec.java)
                    break;
                }
                case 4: {
                    //TODO: spolupráce mezi kolegy
                    break;
                }
                case 5: {
                    int ID = 0;
                    if(pocetPrvkuDatabaze > 0){
                        try {
                            System.out.print("Zadejte ID zaměstnance: ");
                            ID = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        lokalniDatabaze.dovednostZamestnance(ID);
                    }else{
                        System.out.println(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!" + ANSI_RESET);
                    }
                    break;
                }
                case 6: {
                    //TODO: statistiky - MIKEŠ
                    break;
                }
                case 7: {
                    //TODO: abecední výpis lidí ve skupinách
                    break;
                }
                case 8: {
                    lokalniDatabaze.vypisPocty();
                    break;
                }
                case 9: {
                    //TODO: uložit zaměstnance do souboru - MIKEŠ
                    break;
                }
                case 10: {
                    //TODO: načíst zaměstnance ze souboru - MIKEŠ
                    break;
                }
            }
        }
    }
}