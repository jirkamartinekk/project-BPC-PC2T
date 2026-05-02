package default_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDatabaze {
    private Connection pripojeni = null;

    public boolean pripojDatabazi(){
        try{
            pripojeni = DriverManager.getConnection("jdbc:sqlite:databaze.db");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
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
            ps.setByte(5, pracovnik.ziskejSkupinu());

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

}
