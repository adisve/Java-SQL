package utils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlInterface {
    private ResultSet rs;
    private final Connector connector;
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private final HashMap<String, Integer> tableToArrayIndex;
    
    public SqlInterface(Connector connector){
        this.connector = connector;
        this.connection = connector.getConnection();
        this.tableToArrayIndex = new HashMap<String, Integer>() {{
            put("user", 0);
            put("phone", 1);
            put("pillow", 2);
            put("schedule", 3);
        }};
    }

    public void select(String tableName,
        String[] prepStmt, Scanner sc) throws SQLException {
            int tableMapIndex = tableToArrayIndex.get(tableName);
            execute(tableName, sc, prepStmt, tableMapIndex);
        }

    public void insert(String tableName, 
        String[] prepStmt, Scanner sc) throws SQLException{
            int tableMapIndex = tableToArrayIndex.get(tableName);
            execute(tableName, sc, prepStmt, tableMapIndex);
        }

    public void update(String tableName, 
        String[] prepStmt, Scanner sc) throws SQLException{
            int tableMapIndex = tableToArrayIndex.get(tableName);
            execute(tableName, sc, prepStmt, tableMapIndex);
            
        }

    public void delete(String tableName, 
        String[] prepStmt, Scanner sc) throws SQLException{
            int tableMapIndex = tableToArrayIndex.get(tableName);
            execute(tableName, sc, prepStmt, tableMapIndex);
        }
    
    public void execute(String tableName, Scanner sc,
        String[] prepStmt, int tableMapIndex) throws SQLException{
        System.out.printf("Enter %s ID -> ", tableName);
            String QUERYID = sc.nextLine();
            while(checkInput(QUERYID)){
                System.out.println("Input must be integer");
                System.out.printf("Enter %s ID -> ", tableName);
                QUERYID = sc.nextLine();
            }
            preparedStatement = connection.prepareStatement(prepStmt[tableMapIndex]);
            preparedStatement.setString(1, QUERYID);
            rs =  preparedStatement.executeQuery();
            connector.displayQuery(rs); 
    }
    
    private boolean checkInput(String input){
        return input.matches("^[1-9]\\d*$");
    }
}
