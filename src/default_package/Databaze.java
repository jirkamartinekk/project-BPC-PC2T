package default_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Databaze {
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
}
