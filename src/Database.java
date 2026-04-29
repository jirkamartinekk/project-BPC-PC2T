import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Database() throws SQLException {
    }

    public static void main(String[] args){
        System.out.println("Test");

        Connection conn;

        {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:databaze.db");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print("Připojeno");
    }
}
