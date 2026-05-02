package default_package;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
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
                        "\t0 ... [UKONČIT]   program a synchronizovat s databází\n";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";

        boolean behProgramu = true;
        int vybranaMoznost;
        Scanner sc = new Scanner(System.in);
        LokalniDatabaze lokalniDatabaze = new LokalniDatabaze();

        //začátek programu
        while (behProgramu) {
            System.out.println(VYPIS_MENU);
            System.out.print("Vybraná možnost: ");
            vybranaMoznost = sc.nextShort();
            switch (vybranaMoznost) {
                case 0: {
                    behProgramu = false;
                    //TODO: kód pro uložení dat do databáze přes SQL
                    //TODO: ohlídat si try-catch*/
                    break;
                }
                case 1: {
                    String jmeno = "";
                    String prijmeni = "";
                    Short rokNarozeni = 0;
                    Byte skupina = 0;
                    boolean vstupJeOk = false;

                    while (!vstupJeOk) {
                        try {
                            System.out.print("Zadej jméno: ");
                            jmeno = sc.next().trim();
                            System.out.print("Zadej příjmení: ");
                            prijmeni = sc.next().trim();
                            System.out.print("Zadej rok narození: ");
                            rokNarozeni = sc.nextShort();
                            System.out.print("Zadej pracovní skupinu - Datový analytik (1) / Bezpečnostní specialista (2): ");
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
                    if (vstupJeOk) {
                        Zamestnanec novyZamestnanec = null;
                        switch (skupina) {
                            case 1:
                                novyZamestnanec = new Analytik(jmeno, prijmeni, rokNarozeni, skupina);
                                break;
                            case 2:
                                novyZamestnanec = new Bezpecak(jmeno, prijmeni, rokNarozeni, skupina);
                                break;
                        }
                        lokalniDatabaze.pridejZamestnance(novyZamestnanec);
                        System.out.println(ANSI_GREEN + "Zaměstnanec byl úspěšně přidán!\n" + ANSI_RESET);
                        vstupJeOk = false;
                    }
                    break;
                }
                case 2: {
                    int ID = 0;
                    int pocetPrvkuDatabaze = lokalniDatabaze.pocetPrvkuDatabaze();
                    if(pocetPrvkuDatabaze > 0){
                        try {
                            System.out.println("Rozsah prvků databáze: " + pocetPrvkuDatabaze);
                            System.out.print("Zadejte ID zaměstnance: ");
                            ID = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED +  "CHYBA: Nedodržuješ syntaxi!" + ANSI_RESET);
                            sc.nextLine();
                        }
                        boolean odstranen = lokalniDatabaze.odstranZamestnance(ID);
                        if(odstranen){
                            System.out.println(ANSI_GREEN + "Zaměstnanec byl úspěšně odebrán!\n" + ANSI_RESET);
                        }else{
                            System.out.println(ANSI_RED + "CHYBA: Zaměstnance se nepodařilo odebrat!\n" + ANSI_RESET);
                        }
                    }else{
                        System.out.print(ANSI_RED + "CHYBA: Databáze neobsahuje žádné prvky!\n" + ANSI_RESET);
                    }
                    //TODO: odebrání zaměstnance ze všech vazeb
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    break;
                }
                case 5: {
                    break;
                }
                case 6: {
                    break;
                }
                case 7: {
                    break;
                }
                case 8: {
                    break;
                }
                case 9: {
                    break;
                }
                case 10: {
                    break;
                }
            }
        }
    }
}