package utils;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menus {
    private enum SQLFUNCTIONS {
        SELECT,
        UPDATE,
        DELETE
    }

    private final String[] selectStatements = {
        "SELECT * FROM user where user_id = ?",
            "SELECT * FROM phone WHERE phone.user_id = ?", 
                "SELECT * FROM pillow WHERE pillow.user_id = ?", 
                    "SELECT * FROM schedule WHERE schedule.schedule_id = ?"};

    private final String[] updateStatements = {
        "UPDATE user SET password = ? WHERE user.user_id = ?",
            "UPDATE phone SET imei = ?, uuid = ?, mac = ?, brand = ?, model = ?, manufacturer = ? WHERE phone.user_id = ?",
                "UPDATE pillow SET model = ?, version_number = ? WHERE pillow.user_id = ?",
                    "UPDATE schedule SET alarm_date = ?, schedule_name = ? WHERE schedule.schedule_id = ?"
    };

    private final String[] deleteStatements = {
        "DELETE user, phone, schedule, theme FROM user INNER JOIN phone ON phone.user_id = user.user_id " +
        "INNER JOIN schedule ON schedule.user_id = user.user_id INNER JOIN theme ON " +
            "theme.schedule_id = schedule.schedule_id WHERE user.user_id = ?", 
            "DELETE phone, schedule, pillow FROM phone INNER JOIN schedule ON schedule.user_id = phone.user_id " +
                "INNER JOIN pillow ON pillow.user_id = phone.user_id INNER JOIN pillow ON pillow.user_id = phone.user_id WHERE phone.user_id = ?", 
                "DELETE FROM pillow WHERE pillow_id = ?"};

    
    private final Map<Integer, String[]> statements = new HashMap<Integer, String[]>() {{
        put(1, selectStatements);
        put(2, updateStatements);
        put(3, deleteStatements);
    }};

    private final Map<Integer, String> tableNames = new HashMap<Integer, String>(){{
        put(1, "User");
        put(2, "Phone");
        put(3, "Pillow");
        put(4, "Schedule");
    }};


    public void generalMenu(Scanner sc, int option, Connector connector) throws SQLException{
        sc.reset();
        Menus menus = new Menus();
        String[] prepStmt = getPrepStmt(option);
        SQLFUNCTIONS funcType = SQLFUNCTIONS.values()[option-1];
        DatabaseConnection databaseConnection = new DatabaseConnection(connector);
        
        System.out.printf("\nWhat table do you want to %s?\n", funcType.toString());
        System.out.println("1. User\t2. Phone");
        System.out.println("3. Pillow\t4. Schedule");
        var input = sc.next();
        while (!menus.checkInput(input)) {
            System.out.println("\nInput has to be a number in the range 1 - 4\n");
            System.out.println("1. User\t2. Phone");
            System.out.println("3. Pillow\t4. Schedule");
            input = sc.next();
        }
        var tableName = getTableName(Integer.parseInt(input));
        sc.reset();
        System.out.printf("Enter %s PK -> ", tableName);
        String QUERYID = sc.next();
        while(!checkInputId(QUERYID)){
            System.out.println("Input must be integer");
            System.out.printf("Enter %s PK -> ", tableName);
            QUERYID = sc.next();
        }
        switch (funcType) {
            case SELECT:
                databaseConnection.select(tableName, prepStmt, QUERYID);
                break;
            case DELETE:
                databaseConnection.delete(tableName,
                    prepStmt, QUERYID);
                break;
            case UPDATE:
                databaseConnection.update(tableName, prepStmt,
                    sc, QUERYID);
                break;
            default:
                break;
        }
    }

    private String[] getPrepStmt(int option) {
        return statements.get(option);
    }
    private boolean checkInput(String input){
        return input.matches("[1-4]?");
    }
    private boolean checkInputId(String input){
        return input.matches("^[1-9]\\d*$");
    }
    private String getTableName(int option){
        return tableNames.get(option);
    }
}