package utils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection{
    private ResultSet rs;
    private final Connector connector;
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private final HashMap<String, Integer> tableToArrayIndex;
    
    public DatabaseConnection(Connector connector){
        this.connector = connector;
        this.connection = connector.getConnection();
        this.tableToArrayIndex = new HashMap<String, Integer>() {{
            put("User", 0);
            put("Phone", 1);
            put("Pillow", 2);
            put("Schedule", 3);
        }};
    }

    public void select(String tableName,
        String[] prepStmt, String QUERYID) throws SQLException {
            System.out.println(tableName);
            int tableMapIndex = tableToArrayIndex.get(tableName);
            executeQuery(tableName, QUERYID, prepStmt, tableMapIndex);
        }

    public void update(String tableName, 
        String[] prepStmt, Scanner sc, String QUERYID) throws SQLException{
            int tableMapIndex = tableToArrayIndex.get(tableName);
            String[] parameters;
            switch (tableMapIndex) {
                case 0:
                    parameters = updateUser(sc);
                    executeUpdate(tableName, tableMapIndex,
                        prepStmt, parameters, QUERYID);
                    break;
                case 1:
                    parameters = updatePhone(sc);
                    executeUpdate(tableName, tableMapIndex,
                    prepStmt, parameters, QUERYID);
                    break;
                case 2:
                    parameters = updatePillow(sc);
                        executeUpdate(tableName, tableMapIndex,
                    prepStmt, parameters, QUERYID);
                    break;
                case 3:
                    parameters = updateSchedule(sc);
                    executeUpdate(tableName, tableMapIndex,
                        prepStmt, parameters, QUERYID);
                    break;
                default:
                    break;
            }
        }

    public void delete(String tableName, 
    String[] prepStmt, String QUERYID) throws SQLException{
        System.out.println(tableName);
        int tableMapIndex = tableToArrayIndex.get(tableName);
        System.out.println(tableMapIndex);
        executeDelete(tableName, prepStmt, tableMapIndex, QUERYID);
    }
    
    
    private String[] updateUser(Scanner sc) {
        System.out.println("New password: ");
        String password = sc.next();
        return new String[] {password};
    }

    private String[] updatePhone(Scanner sc) {
        System.out.println("New IMEI: ");
        String imei = sc.next();
        System.out.println("New UUID: ");
        String uuid = sc.next();
        System.out.println("New MAC: ");
        String mac = sc.next();
        System.out.println("New Brand: ");
        String brand = sc.next();
        System.out.println("New Model: ");
        String model = sc.next();
        System.out.println("New Manufacturer: ");
        String man = sc.next();
        return new String[] {imei, uuid, mac, brand, model, man};
    }

    private String[] updatePillow(Scanner sc) {
        System.out.println("New Model: ");
        String model = sc.next();
        System.out.println("New Version number: ");
        String versionNum = sc.next();
        return new String[] {model, versionNum};
    }

    private String[] updateSchedule(Scanner sc) {
        System.out.println("New alarm date: ");
        String alarmDate = sc.next();
        System.out.println("New schedule name: ");
        String scheduleName = sc.next();
        return new String[] {alarmDate, scheduleName};
    }
    

    

    private void executeUpdate(String tableName, int tableMapIndex,
        String[] prepStmt, String[] params, String QUERYID) throws SQLException {
            int res;
            preparedStatement = connection.prepareStatement(prepStmt[tableMapIndex]);
            switch (tableMapIndex) {
                case 0:
                    preparedStatement.setString(1, params[0]);
                    preparedStatement.setString(2, QUERYID);
                    res = preparedStatement.executeUpdate();
                    System.out.printf("RECORDS UPDATED -> %d\n", res);
                    break;
                case 1:
                    preparedStatement.setString(1, params[0]);
                    preparedStatement.setString(2, params[1]);
                    preparedStatement.setString(3, params[2]);
                    preparedStatement.setString(4, params[3]);
                    preparedStatement.setString(5, params[4]);
                    preparedStatement.setString(6, params[5]);
                    preparedStatement.setString(7, QUERYID);
                    res = preparedStatement.executeUpdate();
                    System.out.printf("RECORDS UPDATED -> %d\n", res);
                case 2:
                    preparedStatement.setString(1, params[0]);
                    preparedStatement.setString(2, params[1]);
                    preparedStatement.setString(3, QUERYID);
                    res = preparedStatement.executeUpdate();
                    System.out.printf("RECORDS UPDATED -> %d\n", res);
                    break;
                case 3:
                    preparedStatement.setString(1, params[0]);
                    preparedStatement.setString(2, params[1]);
                    preparedStatement.setString(3, QUERYID);
                    res = preparedStatement.executeUpdate();
                    System.out.printf("RECORDS UPDATED -> %d\n", res);
                    break;
                default:
                    break;
            }
    }

    private void executeDelete(String tableName, String[] prepStmt,
        int tableMapIndex, String QUERYID) throws SQLException {
            preparedStatement = connection.prepareStatement(prepStmt[tableMapIndex]);
            preparedStatement.setString(1, QUERYID);
            int res = preparedStatement.executeUpdate();
            System.out.printf("\nRECORDS UPDATED -> %d\n", res);
    }

    public void executeQuery(String tableName, String QUERYID,
        String[] prepStmt, int tableMapIndex) throws SQLException{
        preparedStatement = connection.prepareStatement(prepStmt[tableMapIndex]);
        preparedStatement.setString(1, QUERYID);
        rs = preparedStatement.executeQuery();
        connector.displayQuery(rs); 
    }
    
    
}
