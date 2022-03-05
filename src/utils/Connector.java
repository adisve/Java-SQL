package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class Connector implements ConnectionUtils{
    private String conn_hostname;
    private String conn_database;
    private String conn_user;
    private String conn_pass;
    private Connection connection;
    private Statement statement;

    public Connector(String hostname, String database, User user) {
        this.conn_hostname = hostname;
        this.conn_database = database;
        this.conn_pass = user.getPassword();
        this.conn_user = user.getName();
        try {
            this.connection = init();
            this.statement = connection.createStatement();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Connection init() throws SQLException {
        return DriverManager.getConnection(String.format("jdbc:mysql://%s:3306/%s", conn_hostname, conn_database),
                String.format("%s", conn_user), String.format("%s", conn_pass));
    }

    @Override
    public ResultSet query(PreparedStatement sqlString) {
        try {
            return sqlString.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void displayQuery(ResultSet result) {
        try {
            ResultSetMetaData rsmd = result.getMetaData();
            System.out.println("\n--------------------\n");
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rsmd.getColumnLabel(i) + "\t");
            }
            System.out.println("\n");
            while(result.next()){
                System.out.println("\n--------------------\n");
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(result.getString(i) + "\t");
                }
            }
            System.out.println("\n--------------------\n");
        } catch (SQLException e) {
            System.out.println("Something went wrong.");
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean isConnected() {
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
        return this.conn_hostname;
    }

    @Override
    public int update(String sqlString) {
        try {
            return statement.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
