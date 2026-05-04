package default_package;

import java.sql.*;
import java.util.Map;

public class SQLDatabaze {
    private Connection pripojeni = null;

    private LokalniDatabaze lokalniDatabaze = null;

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";

    public SQLDatabaze(LokalniDatabaze lokalniDatabaze){
        this.lokalniDatabaze = lokalniDatabaze;
        pripojDatabazi();

    }

    public void pripojDatabazi(){
        try{
            pripojeni = DriverManager.getConnection("jdbc:sqlite:sql/databaze.db");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean odpojDatabazi(){
        if(pripojeni != null){
            try{pripojeni.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public void ulozitZamestnance(){
        String sql = "INSERT INTO zamestnanci(id, jmeno, prijmeni, rok_narozeni, skupina) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            for(Zamestnanec zamestnanec : lokalniDatabaze.ziskejZamestnance()) {
                ps = pripojeni.prepareStatement(sql);

                String skupina = null;

                switch(zamestnanec.ziskejSkupinu()) {
                    case "Datový analytik":
                        skupina = "DATA";
                        break;
                    case "Bezpečnostní specialista":
                        skupina = "SEC";
                        break;
                    default:
                        System.out.println(ANSI_RED + "CHYBA: Neplatný údaj 'skupina' při uložení zaměstnance " + ANSI_YELLOW + zamestnanec.ziskejID() + ANSI_RED + " !" + ANSI_RESET);
                        continue;
                }

                ps.setInt(1, zamestnanec.ziskejID());
                ps.setString(2, zamestnanec.ziskejJmeno());
                ps.setString(3, zamestnanec.ziskejPrijmeni());
                ps.setInt(4, zamestnanec.ziskejRokNarozeni());
                ps.setString(5, skupina);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println();

    }

    public void smazatZamestnance(){
        String sql = "DELETE FROM zamestnanci";

        PreparedStatement ps = null;
        try {
            ps = pripojeni.prepareStatement(sql);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ulozitSpoluprace() {
        String sql = "INSERT INTO spoluprace(zamestnanec_id, spolupracovnik_id, uroven) VALUES (?, ?, ?)";

        try (PreparedStatement ps = pripojeni.prepareStatement(sql)) {

            for (Zamestnanec zamestnanec : lokalniDatabaze.ziskejZamestnance()) {

                for (Map.Entry<Zamestnanec, String> entry : zamestnanec.pristupKeSpolupracovnikum().entrySet()) {

                    Zamestnanec spolupracovnik = entry.getKey();
                    String uroven = entry.getValue();

                    ps.setInt(1, zamestnanec.ziskejID());
                    ps.setInt(2, spolupracovnik.ziskejID());

                    // převod na DB hodnoty
                    switch (uroven) {
                        case "Špatná":
                            ps.setString(3, "spatna");
                            break;
                        case "Průměrná":
                            ps.setString(3, "prumerna");
                            break;
                        case "Dobrá":
                            ps.setString(3, "dobra");
                            break;
                        default:
                            System.out.println(ANSI_RED + "CHYBA: Neplatný údaj 'skupina' při uložení spolupráce " + ANSI_YELLOW + zamestnanec.ziskejID() + "->" + spolupracovnik.ziskejID() + ANSI_RED + " !" + ANSI_RESET);
                            continue;
                    }

                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ANSI_GREEN + "Data úspěšně uložena do SQL databáze!" + ANSI_RESET);
    }

    public void smazatSpoluprace(){
        String sql = "DELETE FROM spoluprace";

        PreparedStatement ps = null;
        try {
            ps = pripojeni.prepareStatement(sql);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void nacistZamestnance(){
        String sql = "SELECT * FROM zamestnanci";

        PreparedStatement ps = null;
        ResultSet rs = null;

        Zamestnanec novyZamestnanec = null;

        try {
            ps = pripojeni.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                short rok_narozeni = rs.getShort("rok_narozeni");
                String skupina = rs.getString("skupina");

                if(skupina.equals("DATA")){
                    novyZamestnanec = new Analytik(id, jmeno, prijmeni, rok_narozeni, skupina);
                }
                else if(skupina.equals("SEC")){
                    novyZamestnanec = new Bezpecak(id, jmeno, prijmeni, rok_narozeni, skupina);
                }
                else{
                    System.out.println(ANSI_RED + "CHYBA: Neplatný údaj 'skupina' při načtení zaměstnance " + ANSI_YELLOW + id + ANSI_RED + " !" + ANSI_RESET);
                    return;
                }

                lokalniDatabaze.pridejZamestnanceSQL(id, jmeno, prijmeni, rok_narozeni, skupina);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void nacistSpoluprace(){
        String sql = "SELECT * FROM spoluprace";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = pripojeni.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){
                int zamestnanec_id = rs.getInt("zamestnanec_id");
                int spolupracovnik_id = rs.getInt("spolupracovnik_id");
                String uroven = rs.getString("uroven");

                lokalniDatabaze.pridejSpolupraciSQL(zamestnanec_id, spolupracovnik_id, uroven);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(ANSI_GREEN + "Data z SQL databáze úspěšně načtena!" + ANSI_RESET);

    }

}
