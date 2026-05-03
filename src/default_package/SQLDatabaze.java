package default_package;

import java.sql.*;

public class SQLDatabaze {
    private Connection pripojeni = null;

    public SQLDatabaze(){
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

    public void pridatZamestnance(Zamestnanec pracovnik){
        String sql = "INSERT INTO zamestnanci(id, jmeno, prijmeni, rok_narozeni, skupina) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = pripojeni.prepareStatement(sql);

            ps.setInt(1, pracovnik.ziskejID());
            ps.setString(2, pracovnik.ziskejJmeno());
            ps.setString(3, pracovnik.ziskejPrijmeni());
            ps.setInt(4, pracovnik.ziskejRokNarozeni());
            ps.setString(5, pracovnik.ziskejSkupinu());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    public void pridatSpolupraci(Zamestnanec pracovnik, Zamestnanec spolupracovnik, String uroven){
        String sql = "INSERT INTO spoluprace(zamestnanec_id, spolupracovnik_id, uroven) VALUES (?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = pripojeni.prepareStatement(sql);

            ps.setInt(1, pracovnik.ziskejID());
            ps.setInt(2, spolupracovnik.ziskejID());
            ps.setString(3, uroven);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
                    Analytik novyZamestnanec = new Analytik(id, jmeno, prijmeni, rok_narozeni, skupina);
                }
                else if(skupina.equals("SEC")){
                    Bezpecak novyZamestnanec = new Bezpecak(id, jmeno, prijmeni, rok_narozeni, skupina);
                }

                //TODO: Pridat hajzla do lokální databáze - Jirko pomoc!
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

                //TODO: Pridat spolupraci do lokální databáze - Jirko pomoc!
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
