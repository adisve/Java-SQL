package utils;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menus {
    private enum SQLFUNCTIONS {
        SELECT,
        INSERT,
        UPDATE,
        DELETE
    }

    private final String[] selectStatements = {"SELECT * FROM user where user_id = ?", "SELECT * FROM phone WHERE phone.user_id = ?", 
        "SELECT * FROM pillow WHERE pillow.user_id = ?", "SELECT * FROM schedule WHERE schedule.schedule_id = ?"};

    private final String[] deleteStatements = 
    {"DELETE user, phone, schedule, theme FROM user INNER JOIN phone ON phone.user_id = user.user_id " +
        "INNER JOIN schedule ON schedule.user_id = user.user_id INNER JOIN theme ON " +
            "theme.schedule_id = schedule.schedule_id WHERE user.name = ?", "DELETE FROM schedule WHERE name = ?", 
        "DELETE FROM pillow WHERE pillow_id = ?", "DELETE FROM bluetooth_module WHERE module_id = ?", "DELETE FROM theme WHERE theme_id = ?"};

    private final String[] insertStatements = {"INSERT INTO user(email, password, name) VALUES(?, ?, ?)", 
        "INSERT INTO schedule(alarm_date, user_id, schedule_name) VALUES(?, ?, ?)", "INSERT INTO pillow(model, user_id, version_number)",
        "INSERT INTO bluetooth_module(mac, uuid, pillow_id) VALUES(?, ?, ?)", "INSERT INTO theme(color, pattern, movement, speed, schedule_id) VALUES(?, ?, ?, ?, ?)"};

    private final String[] updateStatements = {};
    
    private final Map<Integer, String[]> statements = new HashMap<Integer, String[]>() {{
        put(1, selectStatements);
        put(2, deleteStatements);
        put(3, insertStatements);
        put(4, updateStatements);
    }};

    private final Map<Integer, String> tableNames = new HashMap<Integer, String>(){{
        put(1, "User");
        put(2, "Phone");
        put(3, "Pillow");
        put(4, "Schedule");
    }};


    public void generalMenu(Scanner sc, int option, Connector connector) throws SQLException{
        Menus menus = new Menus();
        String[] prepStmt = getPrepStmt(option);
        SQLFUNCTIONS funcType = SQLFUNCTIONS.values()[option];
        SqlInterface sqlInterface = new SqlInterface(connector);
        
        System.out.printf("\nWhat table do you want to %s?\n", funcType.toString());
        var input = sc.nextLine();
        while (menus.checkInput(input)) {
            System.out.println("\nInput has to be a number\n");
            System.out.println("1. User\t2. Phone");
            System.out.println("2. Pillow\t3. Schedule");
            input = sc.nextLine();
        }
        var tableName = getTableName(Integer.parseInt(input));

        switch (funcType) {
            case SELECT:
                sqlInterface.select(tableName, prepStmt, sc);
                break;
            case INSERT:
                sqlInterface.insert(tableName, prepStmt, sc);
                break;
            case UPDATE:
                sqlInterface.update(tableName, prepStmt, sc);
                break;
            case DELETE:
                sqlInterface.delete(tableName, prepStmt, sc);
                break;
            default:
                break;
        }
    }

    private String[] getPrepStmt(int option) {
        return statements.get(option);
    }
    private boolean checkInput(String input){
        return input.matches("^[0-9]{1,3}");
    }
    private String getTableName(int option){
        return tableNames.get(option);
    }
}