import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Main {

    public static void main(String[] args) {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "guest", "q18jpg!5&rT");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Country LIMIT 5");
            while(rs.next()) System.out.println(rs.getString("Code"));
        }catch (SQLException e) {
            e.printStackTrace();
        } {

        }
    }
}
