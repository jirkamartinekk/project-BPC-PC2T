import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        boolean behProgramu = true;
        int vybranaMoznost;
        Scanner sc = new Scanner(System.in);
        final String VYPIS_MENU =
                "--- HLAVNÍ MENU --- \n" +
                "SPRÁVA ZAMĚSTNANCŮ\n" +
                "\t1 ... [PŘIDAT]    zaměstnance\n" +
                "\t2 ... [ODEBRAT]   zaměstnance (včetně všech vazeb)\n" +
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

        //samotný program
        while(behProgramu){
            System.out.println(VYPIS_MENU);
            System.out.print("Vybraná možnost: ");
            vybranaMoznost = sc.nextShort();
            switch (vybranaMoznost){
                case 0:
                    behProgramu = false;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
            }
        }
    }
}
