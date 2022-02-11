package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector implements ConnectionUtils{
    private String hostname;
    private String database;
    private String conn_user;
    private String conn_pass;
    private Connection connection;
    private Statement statement;

    public Connector(String hostname, String database, User user){
        this.hostname = hostname;
        this.database = database;
        this.conn_pass = user.getPassword();
        this.conn_user = user.getName();
        try{
            this.connection = init();
            this.statement = connection.createStatement();
            
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
    
    private Connection init() throws SQLException{
        return DriverManager.getConnection(String.format("jdbc:mysql://%s:3306/%s", hostname, database), 
            String.format("%s", conn_user), String.format("%s", conn_pass));
    }

    @Override
    public ResultSet query(String sqlString) {
        try {
            return statement.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String displayQuery(ResultSet result) {
        return null;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        };
        
    }

    @Override
    public Boolean isConnected(){
        try {
            return connection.isValid(3);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public String getHost() {
        return this.hostname;
    }

}
