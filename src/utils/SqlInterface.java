package utils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlInterface{
    private ResultSet rs;
    private final Connector connector;
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private final HashMap<String, Integer> tableToArrayIndex;
    
    public SqlInterface(Connector connector){
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
        String[] prepStmt, Scanner sc) throws SQLException {
            System.out.println(tableName);
            int tableMapIndex = tableToArrayIndex.get(tableName);
            executeQuery(tableName, sc, prepStmt, tableMapIndex);
        }

    public void update(String tableName, 
        String[] prepStmt, Scanner sc) throws SQLException{
            int tableMapIndex = tableToArrayIndex.get(tableName);
            String[] parameters;
            switch (tableMapIndex) {
                case 0:
                    parameters = updateUser(sc);
                    executeUpdate(tableName, sc, 
                        prepStmt, tableMapIndex, parameters);
                    break;
                case 1:
                    parameters = updatePhone(sc);
                    executeUpdate(tableName, sc, 
                        prepStmt, tableMapIndex, parameters);
                    break;
                case 2:
                    parameters = updatePillow(sc);
                    executeUpdate(tableName, sc, 
                        prepStmt, tableMapIndex, parameters);
                    break;
                case 3:
                    parameters = updateSchedule(sc);
                    executeUpdate(tableName, sc, 
                        prepStmt, tableMapIndex, parameters);
                    break;
                default:
                    break;
            }
        }

    public void delete(String tableName, 
        String[] prepStmt, Scanner sc) throws SQLException{
            int tableMapIndex = tableToArrayIndex.get(tableName);
            executeDelete(tableName, sc, prepStmt, tableMapIndex);
        }
    
    public void executeQuery(String tableName, Scanner sc,
        String[] prepStmt, int tableMapIndex) throws SQLException{
        System.out.printf("Enter %s ID -> ", tableName);
            String QUERYID = sc.nextLine();
            while(!checkInput(QUERYID)){
                System.out.println("Input must be integer");
                System.out.printf("Enter %s ID -> ", tableName);
                QUERYID = sc.nextLine();
            }
            preparedStatement = connection.prepareStatement(prepStmt[tableMapIndex]);
            preparedStatement.setString(1, QUERYID);
            rs = preparedStatement.executeQuery();
            connector.displayQuery(rs); 
    }
    
    private String[] updateUser(Scanner sc) {
        System.out.println("New password: ");
        String password = sc.nextLine();
        return new String[] {password};
    }

    private String[] updatePhone(Scanner sc) {
        System.out.println("New IMEI: ");
        String imei = sc.nextLine();
        System.out.println("New UUID: ");
        String uuid = sc.nextLine();
        System.out.println("New MAC: ");
        String mac = sc.nextLine();
        System.out.println("New Brand: ");
        String brand = sc.nextLine();
        System.out.println("New Model: ");
        String model = sc.nextLine();
        System.out.println("New Manufacturer: ");
        String man = sc.nextLine();
        return new String[] {imei, uuid, mac, brand, model, man};
    }

    private String[] updatePillow(Scanner sc) {
        System.out.println("New Model: ");
        String model = sc.nextLine();
        System.out.println("New Version number: ");
        String versionNum = sc.nextLine();
        return new String[] {model, versionNum};
    }

    private String[] updateSchedule(Scanner sc) {
        System.out.println("New alarm date: ");
        String alarmDate = sc.nextLine();
        System.out.println("New schedule name: ");
        String scheduleName = sc.nextLine();
        return new String[] {alarmDate, scheduleName};
    }

    private void executeDelete(String tableName, Scanner sc,
        String[] prepStmt, int tableMapIndex) throws SQLException {
            System.out.printf("Enter %s ID -> ", tableName);
            String QUERYID = sc.nextLine();
            while(!checkInput(QUERYID)){
                System.out.println("Input must be integer");
                System.out.printf("Enter %s ID -> ", tableName);
                QUERYID = sc.nextLine();
            }
            preparedStatement = connection.prepareStatement(prepStmt[tableMapIndex]);
            preparedStatement.setString(1, QUERYID);
            int res = preparedStatement.executeUpdate();
            System.out.printf("\n%d RECORDS UPDATED\n", res);
    }

    private void executeUpdate(String tableName, Scanner sc,
        String[] prepStmt, int tableMapIndex, String[] params) throws SQLException {
            int res;
            System.out.printf("Enter %s ID -> ", tableName);
            preparedStatement = connection.prepareStatement(prepStmt[tableMapIndex]);
            String QUERYID = sc.nextLine();
            while(!checkInput(QUERYID)){
                System.out.println("Input must be integer");
                System.out.printf("Enter %s ID -> ", tableName);
                QUERYID = sc.nextLine();
            }

            switch (tableMapIndex) {
                case 0:
                    preparedStatement.setString(1, params[0]);
                    preparedStatement.setString(2, QUERYID);
                    res = preparedStatement.executeUpdate();
                    System.out.printf("%d RECORDS UPDATED\n", res);
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
                    System.out.printf("%d RECORDS UPDATED\n", res);
                case 2:
                    preparedStatement.setString(1, params[0]);
                    preparedStatement.setString(2, params[1]);
                    preparedStatement.setString(3, QUERYID);
                    res = preparedStatement.executeUpdate();
                    System.out.printf("%d RECORDS UPDATED\n", res);
                    break;
                case 3:
                    preparedStatement.setString(1, params[0]);
                    preparedStatement.setString(2, params[1]);
                    preparedStatement.setString(3, QUERYID);
                    res = preparedStatement.executeUpdate();
                    System.out.printf("%d RECORDS UPDATED\n", res);
                    break;
                default:
                    break;
            }
    }
    
    private boolean checkInput(String input){
        return input.matches("^[1-9]\\d*$");
    }
}
