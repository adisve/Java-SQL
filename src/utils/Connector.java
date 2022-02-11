package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Connector {
    private String hostname;
    private String database;
    private String conn_user;
    private String conn_pass;
    private Connection connection;

    public Connector(String hostname, String database, User user){
        this.hostname = hostname;
        this.database = database;
        this.conn_pass = user.getPassword();
        this.conn_user = user.getName();
        try{
            this.connection = init();
            
        }catch(Exception exception){
            exception.printStackTrace();
        }
    
    }
    
    private Connection init() throws SQLException{
        return DriverManager.getConnection(String.format("jdbc:mysql://%s:3306/%s", hostname, database), 
            String.format("%s", conn_user), String.format("%s", conn_pass));
    }

}
